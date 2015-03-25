/*
 * Copyright BLC IT Services Ltd 2015
 */
package uk.co.blc_services.gumtree;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.co.blc_services.gumtree.domain.Gender;
import uk.co.blc_services.gumtree.domain.Person;

/**
 * Depends on the Test data supplied by Gumtree.
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
public class AddressRepositoryTest {
	
	private static AddressRepository addressRepo;
	
	private static final Person PAUL = new Person("Paul Robinson", Gender.MALE, LocalDate.parse("1985-01-15"));
	private static final Person WES = new Person("Wes Jackson", Gender.MALE, LocalDate.parse("1974-08-14"));
	private static final Person BILL = new Person("Bill McKnight", Gender.MALE, LocalDate.parse("1977-03-16"));
	@BeforeClass
	public static void setup(){
		//initialise test data
		addressRepo = new AddressRepositoryImpl(getTestData());
	}
	

	/**
	 * Test method for {@link uk.co.blc_services.gumtree.AddressRepository#getPeople()}.
	 */
	@Test
	public void testGetPeople() {
		List<Person> people = addressRepo.getPeople();
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
		List<Person> people = addressRepo.findPeopleByName("Paul Robinson");
		assertNotNull("Should return non null list",people);
		assertEquals(1, people.size());
		assertEquals(PAUL, people.get(0));
	}
	
	/**
	 * No people found scenario.
	 * Test method for {@link uk.co.blc_services.gumtree.AddressRepository#findPeopleByName(java.lang.String)}.
	 */
	@Test
	public void testGetPeopleByNameNoneFound() {
		List<Person> people = addressRepo.findPeopleByName("Nobody Real");
		assertNotNull("Should return empty list not null",people);
		assertEquals(0, people.size());
	}

	/**
	 * Test method for {@link uk.co.blc_services.gumtree.AddressRepository#findPeopleByGender(uk.co.blc_services.gumtree.domain.Gender)}.
	 */
	@Test
	public void testGetPeopleOfGender() {
		List<Person> people = addressRepo.findPeopleByGender(Gender.MALE);
		assertNotNull("People shouldn't be null",people);
		assertEquals(3, people.size());
	}

	/**
	 * Test method for {@link uk.co.blc_services.gumtree.AddressRepository#findOldest()}.
	 */
	@Test
	public void testfindOldest() {
		List<Person> people = addressRepo.findOldest();
		assertNotNull("Should return non null result",people);
		assertEquals(1, people.size());
		assertEquals(WES, people.get(0));
	}

	/**
	 * Very basic test checking that the 2012 leap year has been taken into account.
	 * This could do with a more complete data driven test but this test will catch
	 * a common mistake at least.
	 * Test method for {@link uk.co.blc_services.gumtree.AddressRepository#getAgeDifferenceInDays(uk.co.blc_services.gumtree.domain.Person, uk.co.blc_services.gumtree.domain.Person)}.
	 */
	@Test
	public void testAgeDifferenceInDays() {
		
		long days = addressRepo.getAgeDifferenceInDays(new Person("jeff", Gender.MALE, LocalDate.parse("2012-01-15")),
						new Person("fred", Gender.MALE, LocalDate.parse("2013-01-15")));
		assertEquals(366,days);
		
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
			WES,
			BILL,
			new Person("Gemma Lane", Gender.FEMALE, LocalDate.parse("1991-11-20")),
			new Person("Sarah Stone", Gender.FEMALE, LocalDate.parse("1980-09-20")));
	}
}
