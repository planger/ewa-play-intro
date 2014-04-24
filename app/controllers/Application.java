package controllers;

import java.util.ArrayList;
import java.util.List;

import play.*;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {

	public static Result index() {
		return ok(index.render("Your new application is ready."));
	}

	public static Result sayHello() {
		return ok("Hello " + request().remoteAddress());
	}

	public static Result sayHelloTo(String name) {
		if (name.toLowerCase().matches("[a-z]+")) {
			String theName = name.toUpperCase();
			return ok("Hello " + theName);
		} else {
			return badRequest("Provide a proper name!");
		}
	}

	public static Result listCharacters(String text) {
		List<Character> characters = new ArrayList<Character>();
		for (char c : text.toLowerCase().toCharArray()) {
			if (!characters.contains(c)) {
				characters.add(c);
			}
		}
		return ok(characterlist.render(text, characters));
	}

}
