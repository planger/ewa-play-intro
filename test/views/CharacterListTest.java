package views;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import play.api.templates.Html;
import views.html.characterlist;

public class CharacterListTest {

	@Test
	public void testEmptyList() {
		Html html = characterlist.render("empty", new ArrayList<Character>());
		assertEquals("text/html", html.contentType());
		assertFalse(html.body().contains("<ul>"));
	}
	
	@Test
	public void testNonEmptyList() {
		ArrayList<Character> list = new ArrayList<Character>();
		list.add('t');
		Html html = characterlist.render("nonempty", list);
		assertEquals("text/html", html.contentType());
		assertTrue(html.body().contains("<ul>"));
		assertTrue(html.body().contains("<li>t</li>"));
	}

}
