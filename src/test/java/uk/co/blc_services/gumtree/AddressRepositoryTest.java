/*
 * Copyright BLC IT Services Ltd 2015
 */
package uk.co.blc_services.gumtree;

import static org.junit.Assert.*;

import java.util.List;
import java.time.LocalDate;

import org.junit.Test;

import uk.co.blc_services.gumtree.domain.*;

/**
 * Depends on the Test data supplied by Gumtree.
 * Test will need to be made more rigorous (or other cases created)
 * to test other scenarios.
 * 
 * @author dave.clarke@blc-services.co.uk
 *
 */
public class AddressRepositoryTest {
	
	private AddressRepository addressRepo;
	

	/**
	 * Test method for {@link uk.co.blc_services.gumtree.AddressRepository#getPeople()}.
	 */
	@Test
	public void testGetPeople() {
		List<Person> people = this.addressRepo.getPeople();
		assertNotNull("Shouldn't get a null list", people);
		assertEquals("Expected number of people from test data not returned.",5,people.size());
		for (Person person : people) {
			assertNotNull("One of the people was null", person);
		}
		assertEquals("Bill should be 1st in the list", "Bill McKnight", people.get(0).getName());
		assertEquals("Wes should be last in the list", "Wes Jackson", people.get(4).getName());
	}

	/**
	 * Test method for {@link uk.co.blc_services.gumtree.AddressRepository#findPeopleByName(java.lang.String)}.
	 */
	@Test
	public void testGetPeopleByName() {
		List<Person> people = this.addressRepo.findPeopleByName("Paul Robinson");
		assertEquals(1, people.size());
		assertEquals(new Person("Paul Robinson", Gender.MALE, LocalDate.parse("1985-01-15")), people.get(0));
	}
	
	/**
	 * No people found scenario.
	 * Test method for {@link uk.co.blc_services.gumtree.AddressRepository#findPeopleByName(java.lang.String)}.
	 */
	@Test
	public void testGetPeopleByNameNoneFound() {
		List<Person> people = this.addressRepo.findPeopleByName("Nobody Real");
		assertNotNull("Should return empty list not null",people);
		assertEquals(0, people.size());
	}

	/**
	 * Test method for {@link uk.co.blc_services.gumtree.AddressRepository#findPeopleByGender(uk.co.blc_services.gumtree.domain.Gender)}.
	 */
	@Test
	public void testGetPeopleOfGender() {
		List<Person> people = this.addressRepo.findPeopleByGender(Gender.MALE);
		assertNotNull("People shouldn't be null",people);
		assertEquals(3, people.size());
	}

	/**
	 * Test method for {@link uk.co.blc_services.gumtree.AddressRepository#findOldest()}.
	 */
	@Test
	public void testGetOldest() {
		List<Person> people = this.addressRepo.findOldest();
		assertNotNull("Should return non null result",people);
		assertEquals(1, people.size());
		assertEquals(new Person("Wes Jackson", Gender.MALE, LocalDate.parse("1974-08-14")), people.get(1));
	}

	/**
	 * Test method for {@link uk.co.blc_services.gumtree.AddressRepository#getAgeDifferenceInDays(uk.co.blc_services.gumtree.domain.Person, uk.co.blc_services.gumtree.domain.Person)}.
	 */
	@Test
	public void testAgeDifferenceInDays() {
		fail("Not yet implemented");
	}

}
