/*
 * Copyright BLC IT Services Ltd 2015
 */
package uk.co.blc_services.gumtree.parsing;


/**
 * @see AddressBookParserConcurrencyTest
 * @author dave.clarke@blc-services.co.uk
 *
 */
public class CommonsCSVAddressBookParserConcurrencyTest extends
		AddressBookParserConcurrencyTest {
	
	private static AddressBookParser parser = new CommonsCSVAddressBookParser();
	
	/* (non-Javadoc)
	 * @see uk.co.blc_services.gumtree.parsing.AddressBookParserConcurrencyTest#getParser()
	 */
	@Override
	protected AddressBookParser getParser() {
		return parser;
	}
	
}
