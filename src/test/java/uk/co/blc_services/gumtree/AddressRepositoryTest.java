/*
 * Copyright BLC IT Services Ltd 2015
 */
package uk.co.blc_services.gumtree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import uk.co.blc_services.gumtree.domain.Gender;
import uk.co.blc_services.gumtree.domain.Person;

/**
 * Test will need to be made more rigorous (or other cases created)
 * to test other scenarios.
 * 
 * TODO - Create other files of test data including:
 * Null values for each/all fields
 * Duplicate items in address book (is this okay?? Or should they be filtered out?)
 * Multiple entries with the same name (but other details differ)
 * Multiple people with the same oldest DOB
 * 
 * 
 * @author dave.clarke@blc-services.co.uk
 *
 */
public abstract class AddressRepositoryTest {
	
	private static final Person PAUL = new Person("Paul Robinson", Gender.MALE, LocalDate.parse("1985-01-15"));
	private static final Person WES = new Person("Wes Jackson", Gender.MALE, LocalDate.parse("1974-08-14"));
	private static final Person BILL = new Person("Bill McKnight", Gender.MALE, LocalDate.parse("1977-03-16"));
	private static final Person YOUNG_JOHN = new Person("Young John Smith", Gender.MALE, LocalDate.parse("1945-09-20"));
	private static final Person OLD_FRED = new Person("Old Fred Smith", Gender.MALE, LocalDate.parse("1945-09-20"));
	
	
	/**
	 * @return fully initialised implementation to test
	 */
	abstract protected AddressRepository getRepo();
	

	/**
	 * Test method for {@link uk.co.blc_services.gumtree.AddressRepository#getPeople()}.
	 */
	@Test
	public void testGetPeople() {
		List<Person> people = this.getRepo().getPeople();
		assertNotNull("Shouldn't get a null list", people);
		assertEquals("Expected number of people from test data not returned.",7,people.size());
		for (Person person : people) {
			assertNotNull("One of the people was null", person);
		}
		assertEquals("Bill should be 1st in the list", "Bill McKnight", people.get(0).getName());
		assertEquals("John should be last in the list", YOUNG_JOHN.getName(), people.get(people.size()-1).getName());
	}

	/**
	 * Test method for {@link uk.co.blc_services.gumtree.AddressRepository#findPeopleByName(java.lang.String)}.
	 */
	@Test
	public void testFindPeopleByName() {
		List<Person> people = this.getRepo().findPeopleByName("Paul Robinson");
		assertNotNull("Should return non null list",people);
		assertEquals(1, people.size());
		assertEquals(PAUL, people.get(0));
	}
	
	/**
	 * No people found scenario.
	 * Test method for {@link uk.co.blc_services.gumtree.AddressRepository#findPeopleByName(java.lang.String)}.
	 */
	@Test
	public void testFindPeopleByNameNoneFound() {
		List<Person> people = this.getRepo().findPeopleByName("Nobody Real");
		assertNotNull("Should return empty list not null",people);
		assertEquals(0, people.size());
	}

	/**
	 * Test method for {@link uk.co.blc_services.gumtree.AddressRepository#findPeopleByGender(uk.co.blc_services.gumtree.domain.Gender)}.
	 */
	@Test
	public void testFindPeopleOfGender() {
		List<Person> people = this.getRepo().findPeopleByGender(Gender.MALE);
		assertNotNull("People shouldn't be null",people);
		assertEquals(5, people.size());
	}

	/**
	 * Test method for {@link uk.co.blc_services.gumtree.AddressRepository#findOldest()}.
	 */
	@Test
	public void testFindOldest() {
		List<Person> people = this.getRepo().findOldest();
		assertNotNull("Should return non null result",people);
		assertEquals(2, people.size());
		assertTrue(people.containsAll(Arrays.asList(OLD_FRED, YOUNG_JOHN)));
	}
	
	/**
	 * Dummy test data. Same as provided by Gumtree but order changed to ensure the repo is
	 * sorting the data.
	 * TODO Now we have a parser refactor this into a test file.
	 * @return
	 */
	public static Collection<Person> getTestData(){
		return Arrays.asList(
			PAUL,
			WES,WES,
			BILL,
			YOUNG_JOHN,
					//John and 'Old Fred' are twins born 10 mins apart
			new Person("Gemma Lane", Gender.FEMALE, LocalDate.parse("1991-11-20")),
			new Person("Gemma Lane", Gender.FEMALE, LocalDate.parse("1991-11-20")),//different identity but equal
			new Person("Sarah Stone", Gender.FEMALE, LocalDate.parse("1980-09-20")),
			OLD_FRED);
	}
}
