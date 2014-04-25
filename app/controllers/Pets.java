package controllers;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import models.Pet;
import models.Pet.Gender;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.petform;
import views.html.petresponseform;

public class Pets extends Controller {

	@Transactional
	public static Result list() {
		Collection<Pet> pets = getAllPets();
		if (pets.isEmpty()) addPets(); pets = getAllPets(); // just for testing
		return ok(Json.toJson(pets));
	}
	
	private static Collection<Pet> getAllPets() {
		EntityManager em = JPA.em();
		String queryString = "SELECT p FROM Pet p";
		TypedQuery<Pet> query = em.createQuery(queryString, Pet.class);
		return (Collection<Pet>) query.getResultList();
	}

	private static void addPets() {
		EntityManager em = JPA.em();
		Pet pet1 = new Pet();
		pet1.setGender(Gender.female);
		pet1.setName("Lassie");
		em.persist(pet1);
		Pet pet2 = new Pet();
		pet2.setGender(Gender.male);
		pet2.setName("Snoopy");
		em.persist(pet2);
	}
	
	@Transactional
	public static Result show(long id) {
		Pet pet = getPetById(id);
		if (pet != null)
			return ok(Json.toJson(pet));
		else
			return notFound("No pet with id " + id);
	}

	private static Pet getPetById(long id) {
		EntityManager em = JPA.em();
		return em.find(Pet.class, id);
	}
	
	@Transactional
	public static Result newPetResponse() {
		return ok(petresponseform.render(getAllPets()));
	}
	
	@Transactional
	public static Result createPetResponse() {
		DynamicForm form = Form.form().bindFromRequest();
		String petId = form.data().get("petId");
		String petResponse = form.data().get("petResponse");
		Pet pet = getPetById(Long.valueOf(petId));
		return ok(Messages.get("pet.response", pet.getName(), petResponse));
	}
	
	public static Result newPet() {
		return ok(petform.render(Form.form(Pet.class)));
	}
	
	@Transactional
	public static Result createPet() {
		Form<Pet> form = Form.form(Pet.class).bindFromRequest();
		if (form.hasErrors()) {
			return badRequest(petform.render(form));
		} else {
			Pet pet = form.get();
			JPA.em().persist(pet);
			return redirect(routes.Pets.list());
		}
	}

}
