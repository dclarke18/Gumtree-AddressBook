package uk.co.blc_services.gumtree.domain;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Test for person domain object.
 * Getters and setters not tested.
 * 
 * TODO Add equals/HashCode contract tests as safety net against future modification
 * @author dave.clarke@blc-services.co.uk
 *
 */
public class PersonTest {

	
	/**
	 * Very basic test checking that the 2012 leap year has been taken into account
	 * and some of the common things that might go wrong.
	 * Test method for {@link uk.co.blc_services.gumtree.AddressRepository#getAgeDifferenceInDays(uk.co.blc_services.gumtree.domain.Person, uk.co.blc_services.gumtree.domain.Person)}.
	 */
	@Test
	public void testAgeDifferenceInDays() {
		Person fred = new Person("fred", Gender.MALE, LocalDate.parse("2013-01-15"));
		Person jeff = new Person("jeff", Gender.MALE, LocalDate.parse("2012-01-15"));
			
		assertEquals(366,fred.getAgeDifferenceInDays(jeff).longValue());
		//Check it's reciperocal
		assertEquals(366,jeff.getAgeDifferenceInDays(fred).longValue());
		//self comparison
		assertEquals(0,jeff.getAgeDifferenceInDays(jeff).longValue());
		//nulls
		assertNull(jeff.getAgeDifferenceInDays(null));
		assertNull(jeff.getAgeDifferenceInDays(new Person("fred", Gender.MALE, null)));
		assertNull(new Person("fred", Gender.MALE, null).getAgeDifferenceInDays(jeff));
		assertNull(new Person("fred", Gender.MALE, null).getAgeDifferenceInDays(new Person("jeff", Gender.MALE, null )));
	}
	
	@Test
	@Ignore
	public void testValidator(){
		//TODO Implement this test
	}

}
