package controllers;

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

}
