package controllers;

import static org.fest.assertions.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withText;
import static org.junit.Assert.assertEquals;
import static play.test.Helpers.HTMLUNIT;
import static play.test.Helpers.callAction;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.running;
import static play.test.Helpers.status;
import static play.test.Helpers.testServer;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import play.libs.F.Callback;
import play.libs.Json;
import play.mvc.Result;
import play.test.FakeRequest;
import play.test.TestBrowser;
import play.test.WithApplication;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;

public class PetsTest extends WithApplication {

	@Before
	public void setUp() throws Exception {
		start();
	}

	@Test
	public void testGettingNonExistingPet() {
		Result result = callAction(routes.ref.Pets.show(99999), fakeRequest());
		assertEquals(HttpStatus.SC_NOT_FOUND, status(result));
	}

	@Test
	public void testCreatePetResponse() {
		Result result = callAction(routes.ref.Pets.list(), fakeRequest());
		JsonNode node = Json.parse(contentAsString(result));
		assertEquals(1l, node.get(0).get("id").asLong());
		assertEquals("Lassie", node.get(0).get("name").asText());

		ImmutableMap<String, String> data = ImmutableMap.of("petId", "1",
				"petResponse", "hello");
		FakeRequest request = fakeRequest().withFormUrlEncodedBody(data);
		result = callAction(routes.ref.Pets.createPetResponse(), request);
		assertEquals(HttpStatus.SC_OK, status(result));
		assertEquals("Lassie says hello", contentAsString(result));
	}

	@Test
	public void testCreatePetResponseViaForm() {
		running(testServer(3333), HTMLUNIT, new Callback<TestBrowser>() {
			public void invoke(TestBrowser browser) {
				browser.goTo("http://localhost:3333/pets");
				browser.goTo("http://localhost:3333/pets/response");
				browser.click("option", withText("Snoopy"));
				browser.fill("#petResponse").with("hello");
				browser.$("#petResponse").submit();
				assertThat(browser.pageSource()).isEqualTo("Snoopy says hello");
			}
		});
	}

}
