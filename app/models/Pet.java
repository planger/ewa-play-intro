package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import play.data.validation.Constraints;
import play.data.validation.ValidationError;

@Entity
public class Pet {

	public enum Gender {
		male, female
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Constraints.Required
	@Constraints.MinLength(4)
	@Constraints.MaxLength(8)
	private String name;

	public List<ValidationError> validate() {
		List<ValidationError> errors = null;
		if (!Character.isUpperCase(name.charAt(0))) {
			errors = new ArrayList<ValidationError>();
			errors.add(new ValidationError("name",
					"Must start with upper case letter"));
		}
		return errors;
	}

	private Gender gender;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Long getId() {
		return id;
	}

}
