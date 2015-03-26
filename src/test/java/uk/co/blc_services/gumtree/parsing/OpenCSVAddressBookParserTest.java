/*
 * Copyright BLC IT Services Ltd 2015
 */
package uk.co.blc_services.gumtree.parsing;

import org.junit.BeforeClass;

/**
 * Test for the OpenCSV implementation (again using template pattern)
 * @author dave.clarke@blc-services.co.uk
 *
 */
public class OpenCSVAddressBookParserTest extends AddressBookParserTest {
	
	private static AddressBookParser parser;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		 parser = new OpenCSVAddressBookParser();
	}

	@Override
	protected AddressBookParser getParser() {
		return parser;
	}
	
	


}
