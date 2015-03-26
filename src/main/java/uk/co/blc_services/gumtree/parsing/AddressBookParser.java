/*
 * Copyright BLC IT Services Ltd 2015
 */
package uk.co.blc_services.gumtree.parsing;

import java.io.InputStream;
import java.util.List;

import uk.co.blc_services.gumtree.domain.Person;

/**
 * Parser of address books.
 * 
 * @author dave.clarke@blc-services.co.uk
 *
 */
public interface AddressBookParser {
	
	/**
	 * Returns a list of people in the order found in the stream.
	 * Duplicates maintained.  Lenient on processing so if an entry is invalid
	 * processing should continue with the next entry.
	 * @param is
	 * @return
	 */
	public List<Person> parse(InputStream is);
	

}