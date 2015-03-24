/*
 * Copyright BLC IT Services Ltd 2015
 */
package uk.co.blc_services.gumtree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import uk.co.blc_services.gumtree.domain.Person;

/**
 * Test harness for parser.
 * TODO create a set of tests for invalid data
 * Nulls, unparseable dates, character encoding issues etc.
 * 
 * @author dave.clarke@blc-services.co.uk
 *
 */
public class AddressBookParserTest {
	
	public static final String TEST_FILE_NAME = "AddressBookTest.csv";
	
	private InputStream is;
	private AddressBookParser parser;
	
	@Before
	public void setup(){
		this.is = ClassLoader.getSystemResourceAsStream(TEST_FILE_NAME);
		this.parser = new AddressBookParser();
	}

	@Test
	public void testParseInputSource() {
		List<Person> parsed = this.parser.parse(is);
		assertTrue("Parsed data doesn't match expectation \nExpected:\n"+AddressRepositoryTest.getTestData()+" but got :\n"+parsed,
				parsed.containsAll(AddressRepositoryTest.getTestData()));
		assertEquals("Wrong number of entries", AddressRepositoryTest.getTestData().size(), parsed.size());
		
	}

}
