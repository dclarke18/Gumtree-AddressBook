/*
 * Copyright BLC IT Services Ltd 2015
 */
package uk.co.blc_services.gumtree;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.*;
import uk.co.blc_services.gumtree.domain.Gender;
import uk.co.blc_services.gumtree.domain.Person;
import uk.co.blc_services.gumtree.parsing.CommonsCSVAddressBookParser;

/**
 * Note this uses TestNG rather than JUnit thanks to it's
 * easier setup for multithreaded testing using the \@Test(threadpoolsize...) annotation
 * @author dave.clarke@blc-services.co.uk
 *
 */
public class AddressBookParserConcurrencyTest {
	
	public static final int NO_OF_ENTRIES = 500;
	public static final String NAME_PREFIX = "Parser Test Entry : ";
	public static final String FIELD_SEPERATOR = ", ";
	public static final LocalDate DOB_START_POINT = LocalDate.ofEpochDay(0);// 1st Jan 1970

	public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yy");
	
	private static final String testFileContents;
	
	static{
		//length of each entry = 2 commas, 2 spaces, crlf, name prefix, entry count length(5), male/female (length of 5 on avg.),date
		int entryLength = 2+2+2+NAME_PREFIX.length()+5+5+8;
		StringBuilder sb = new StringBuilder(NO_OF_ENTRIES * entryLength);
		for(int i = 0; i<NO_OF_ENTRIES; i++){
			String entryId = String.format("%1$5s", i);
			String gender = (i % 2 == 0 ? Gender.MALE.toString() : Gender.FEMALE.toString()); 
			sb.append(NAME_PREFIX).append(entryId).append(FIELD_SEPERATOR)
			.append(gender).append(FIELD_SEPERATOR)
			.append(LocalDate.ofEpochDay(i).format(DATE_FORMAT)).append("\n");
			//System.out.println("Added entry "+i);
		}
		testFileContents = sb.toString();
	}
	
	private static CommonsCSVAddressBookParser parser;
	
	private InputStream is;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpClass() throws Exception {
		parser = new CommonsCSVAddressBookParser();
		//TODO Suppress log4j or it could get quite noisy!
	}
	
	@BeforeTest
	public void setUp(){
		is = new ByteArrayInputStream(new String(testFileContents).getBytes());
		//System.out.println("Test File = "+testFileContents);
	}

	@Test(threadPoolSize = 2, invocationCount = 2, timeOut = 10_000)
	public void testParse() {
		List<Person> parsed = parser.parse(is);
		assertNotNull(parsed);
		assertEquals(NO_OF_ENTRIES, parsed.size());
		//TODO should we block until all threads complete before start to check the List?
		int i = 0;
		for (Person p : parsed) {
			assertEquals(NAME_PREFIX+String.format(String.format("%1$5s", i)), p.getName());
			assertEquals(i % 2 ==0 ? Gender.MALE:Gender.FEMALE, p.getGender());
			assertEquals(LocalDate.ofEpochDay(i), p.getDob());
			i++;
		}
		
	}

}
