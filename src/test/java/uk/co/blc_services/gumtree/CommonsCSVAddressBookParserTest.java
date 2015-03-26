/*
 * Copyright BLC IT Services Ltd 2015
 */
package uk.co.blc_services.gumtree;

import org.junit.BeforeClass;

import uk.co.blc_services.gumtree.parsing.AddressBookParser;
import uk.co.blc_services.gumtree.parsing.AddressBookParserTest;
import uk.co.blc_services.gumtree.parsing.CommonsCSVAddressBookParser;

/**
 * @author dave.clarke@blc-services.co.uk
 *
 */
public class CommonsCSVAddressBookParserTest extends AddressBookParserTest {
	
	private static AddressBookParser parser;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		parser = new CommonsCSVAddressBookParser();
	}

	@Override
	protected AddressBookParser getParser() {
		return parser;
	}
	
	
}
