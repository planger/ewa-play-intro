package models;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import play.data.validation.ValidationError;


public class PetTest {

	@Test
	public void testInvalidName() {
		Pet pet = new Pet();
		pet.setName("lower");
		List<ValidationError> errors = pet.validate();
		
		assertEquals(1, errors.size());
		assertEquals("name", errors.get(0).key());
	}
	
	@Test
	public void testValidName() {
		Pet pet = new Pet();
		pet.setName("Upper");
		List<ValidationError> errors = pet.validate();
		
		assertNull(errors);
	}

}
