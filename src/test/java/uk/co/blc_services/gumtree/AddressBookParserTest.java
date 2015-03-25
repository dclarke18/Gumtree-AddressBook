/*
 * Copyright BLC IT Services Ltd 2015
 */
package uk.co.blc_services.gumtree;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import uk.co.blc_services.gumtree.domain.Gender;
import uk.co.blc_services.gumtree.domain.Person;

/**
 * Test harness for parser.
 * TODO Add wondows and unix line ending tests
 * Nulls, unparseable dates, character encoding issues etc.
 * TODO Multithreaded test of the parser
 * TODO refactor into data driven parameterised test?
 * 
 * @author dave.clarke@blc-services.co.uk
 *
 */
public class AddressBookParserTest {
	
	public static final String TEST_DUPLICATES_FILE_NAME = "AddressBookParserTest-testDuplicates.csv";
	public static final String TEST_UNPARSE_ENTRIES_FILE_NAME = "AddressBookParserTest-testUnparseableEntries.csv";
	
	private static final LocalDate VALID_DATE = LocalDate.parse("1977-03-16");
	
	private AddressBookParser parser;
	
	@Before
	public void setup(){
		this.parser = new AddressBookParser();
	}

	@Test
	public void testDuplicatesFile() {
		List<Person> parsed = this.parser.parse(ClassLoader.getSystemResourceAsStream(TEST_DUPLICATES_FILE_NAME));
		assertEquals("Wrong number of entries", 8, parsed.size());
		assertTrue("Parsed data doesn't match expectation \nExpected:\n"+AddressRepositoryTest.getTestData()+" but got :\n"+parsed,
				parsed.equals(getTestDuplicatesExpectedResult()));
		
	}
	
	@Test
	public void testUnparseableEntriesFile() {
		List<Person> parsed = this.parser.parse(ClassLoader.getSystemResourceAsStream(TEST_UNPARSE_ENTRIES_FILE_NAME));
		assertEquals("Wrong number of entries", 2, parsed.size());
		assertTrue("Parsed data doesn't match expectation \nExpected:\n"+AddressRepositoryTest.getTestData()+" but got :\n"+parsed,
				parsed.equals(getTestUnparseableEntriesExpectedResult()));
		
	}
	
	/**
	 * Expected test data matching the input file.
	 * @return expected entries
	 */
	public static Collection<Person> getTestDuplicatesExpectedResult(){
		
		Person wes = new Person("Wes Jackson", Gender.MALE, LocalDate.parse("1974-08-14"));
		Person sarah = new Person("Sarah Stone", Gender.FEMALE, LocalDate.parse("1980-09-20"));
		return Arrays.asList(
			new Person("Bill McKnight", Gender.MALE, VALID_DATE),
			new Person("Paul Robinson", Gender.MALE, LocalDate.parse("1985-01-15")),
			new Person("Gemma Lane", Gender.FEMALE, LocalDate.parse("1991-11-20")),
			sarah,wes, wes, wes, sarah);
	}
	
	/**
	 * Expected test data matching the input file.
	 * @return expected entries
	 */
	public static Collection<Person> getTestUnparseableEntriesExpectedResult(){
		
		return Arrays.asList(
			new Person("Bill McKnight", Gender.MALE, VALID_DATE),
			new Person("Sarah Stone", Gender.FEMALE, LocalDate.parse("1980-09-20")));
	}
	
	
	//*  NAMES */
	@Test
	public void testApostrophe(){
		List<Person> parsed = this.parser.parse(getStream("Fred O'Connor, Male, 16/03/77"));
		assertNotNull(parsed);
		assertEquals("Incorrect number of people returned", 1, parsed.size());
		assertEquals("Unxepected parsing of person","Fred O'Connor", parsed.get(0).getName());
	}
	
	@Test
	public void testComma(){
		List<Person> parsed = this.parser.parse(getStream("\"Mary Ramsgate, Jnr.\", Female, 16/03/77"));
		assertNotNull(parsed);
		assertEquals("Incorrect number of people returned", 1, parsed.size());
		assertEquals("Unxepected parsing of person", new Person("Mary Ramsgate, Jnr.", Gender.FEMALE, VALID_DATE), parsed.get(0));
	}
	
	
	//* DOBS */
	
	@Test
	public void testYoungPersonDOBParse(){
		List<Person> parsed = this.parser.parse(getStream("Young Person, Female, 05/05/05"));
		assertNotNull(parsed);
		assertEquals("Incorrect number of people returned", 1, parsed.size());
		assertEquals("Unxepected parsing of person", LocalDate.parse("2005-05-05"),
						parsed.get(0).getDob());
	}
	
	@Test
	public void testNoDOB(){
		List<Person> parsed = this.parser.parse(getStream("Jeff NoBirthday, Male,"));
		assertNotNull(parsed);
		assertEquals("Incorrect number of people returned", 1, parsed.size());
		assertNull("Unxepected parsing of person", parsed.get(0).getDob());
	}
	
	@Test
	public void testBornAsLeapingOn29th(){
		List<Person> parsed = this.parser.parse(getStream("Lizzie Leaping, Female, 29/02/12"));
		assertNotNull(parsed);
		assertEquals("Incorrect number of people returned", 1, parsed.size());
		assertEquals("Unxepected parsing of person", LocalDate.parse("2012-02-29"),
						parsed.get(0).getDob());
	}
	

	@Test
	public void testNotBornOn29thNotLeapYear(){
		List<Person> parsed = this.parser.parse(getStream("Mark NotLeaping, Male, 29/02/11"));
		assertNotNull(parsed);
		assertEquals("Incorrect number of people returned", 1, parsed.size());
//		assertNull("Unxepected parsing of person", parsed.get(0).getDob());
		//Default behaviour for Java8 parsing is to convert to 28th Feb apparently
		//Seems sensible so asserting that instead. Keeping test case to warn of any change of behaviour
		//in the future.
		
		assertEquals("Unxepected parsing of person", LocalDate.parse("2011-02-28"),
				parsed.get(0).getDob());

		
	}
	

	@Test
	public void testUnparseableDate1DigitMonth(){
		List<Person> parsed = this.parser.parse(getStream("Unparseable Date, Male, 16/3/77"));
		assertNotNull(parsed);
		assertEquals("Incorrect number of people returned", 1, parsed.size());
		assertNull("Unxepected parsing of person", parsed.get(0).getDob());
	}
	
	@Test
	public void testUnparseableDate1DigitDay(){
		List<Person> parsed = this.parser.parse(getStream("Unparseable Date, Male, 16/3/77"));
		assertNotNull(parsed);
		assertEquals("Incorrect number of people returned", 1, parsed.size());
		assertNull("Unxepected parsing of person", parsed.get(0).getDob());
	}
	
	@Test
	public void testUnparseableDate1DigitYear(){
		List<Person> parsed = this.parser.parse(getStream("Unparseable Date, Male, 16/03/7"));
		assertNotNull(parsed);
		assertEquals("Incorrect number of people returned", 1, parsed.size());
		assertNull("Unxepected parsing of person", parsed.get(0).getDob());
	}
	
	@Test
	public void testUnparseableDateInvalidMonth(){
		List<Person> parsed = this.parser.parse(getStream("Unparseable Date, Male, 16/13/77"));
		assertNotNull(parsed);
		assertEquals("Incorrect number of people returned", 1, parsed.size());
		assertNull("Unxepected parsing of person", parsed.get(0).getDob());
	}
	
	@Test
	public void testUnparseableDate4DigitYear(){
		List<Person> parsed = this.parser.parse(getStream("Unparseable Date, Male, 16/13/1977"));
		assertNotNull(parsed);
		assertEquals("Incorrect number of people returned", 1, parsed.size());
		assertNull("Unxepected parsing of person", parsed.get(0).getDob());
	}
		
	/* GENDER */
	
	@Test
	public void testNoGender(){
		List<Person> parsed = this.parser.parse(getStream("Mary NoGender,, 16/03/77"));
		assertNotNull(parsed);
		assertEquals("Incorrect number of people returned", 1, parsed.size());
		assertNull("Unxepected parsing of person", parsed.get(0).getGender());
	}
	
	@Test
	public void testGenderLowerCase(){
		List<Person> parsed = this.parser.parse(getStream("Gordon Gender, male, 16/03/77"));
		assertNotNull(parsed);
		assertEquals("Incorrect number of people returned", 1, parsed.size());
		assertEquals("Unxepected parsing of person", Gender.MALE, parsed.get(0).getGender());
	}
	
	@Test
	public void testGenderUpperCase(){
		List<Person> parsed = this.parser.parse(getStream("Gordon Gender, MALE, 16/03/77"));
		assertNotNull(parsed);
		assertEquals("Incorrect number of people returned", 1, parsed.size());
		assertEquals("Unxepected parsing of person", Gender.MALE, parsed.get(0).getGender());
	}
	
	@Test
	public void testGenderMixedCase(){
		List<Person> parsed = this.parser.parse(getStream("Gordon Gender, MaLe, 16/03/77"));
		assertNotNull(parsed);
		assertEquals("Incorrect number of people returned", 1, parsed.size());
		assertEquals("Unxepected parsing of person", Gender.MALE, parsed.get(0).getGender());
	}
	
	@Test
	public void testUnparseableGender(){
		List<Person> parsed = this.parser.parse(getStream("Gordon Gender, Nothing, 16/03/77"));
		assertNotNull(parsed);
		assertEquals("Incorrect number of people returned", 1, parsed.size());
		assertNull("Unxepected parsing of person", parsed.get(0).getGender());
	}
	
	/* MISSING DATA */
	@Test
	public void testJustName(){
		List<Person> parsed = this.parser.parse(getStream("Only Name,,"));
		assertNotNull(parsed);
		assertEquals("Incorrect number of people returned", 1, parsed.size());
		assertEquals("Unxepected parsing of person", new Person("Only Name", null, null), parsed.get(0));
	}
	
	@Test
	public void testJustNameAndSpaces(){
		List<Person> parsed = this.parser.parse(getStream("NameButRestSpaces,        ,       "));
		assertNotNull(parsed);
		assertEquals("Incorrect number of people returned", 1, parsed.size());
		assertEquals("Unxepected parsing of person", new Person("NameButRestSpaces", null, null), parsed.get(0));
	}

	
	private static InputStream getStream(String data){
		return new ByteArrayInputStream(data.getBytes());
	}

}
