/*
 * Copyright BLC IT Services Ltd 2015
 */
package uk.co.blc_services.gumtree.parsing;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.blc_services.gumtree.domain.Gender;
import uk.co.blc_services.gumtree.domain.Person;

/**
 * Parser of address books.
 * 
 * @author dave.clarke@blc-services.co.uk
 *
 */
public interface AddressBookParser {
	
	//TODO feels wrong having a logger in an interface somehow... refactor out or just get used to it?
	static final Logger LOG = LoggerFactory.getLogger(CommonsCSVAddressBookParser.class);
	
	/* These are threadsafe unlike SimpleDateFormat used to be */
	static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yy");
	
	
	
	/**
	 * Returns a list of people in the order found in the stream.
	 * Duplicates maintained.  Lenient on processing so if an entry is invalid
	 * processing should continue with the next entry.
	 * @param is
	 * @return
	 */
	public List<Person> parse(InputStream is);
	
	/**
	 * Attempts to create a Person instance from the args.
	 * 
	 * @param nameStr
	 * @param genderStr
	 * @param dobStr
	 * @return null if invalid or as full a person as can be created
	 */
	default Person fromStrings(String nameStr, String genderStr, String dobStr){
		LocalDate dob = null;
	    Gender gender = null;
	    try {
			dob = LocalDate.parse(dobStr.trim(), DATE_FORMATTER);
			//Java8 doesn't support the joda time pivot year concept (yet?).
			//check that this date isn't in the future if it is deduct 100 years
			//TODO - Get the users to accept an unambigous yyyy date format!!
			if(LocalDate.now().isBefore(dob) ){
				dob = dob.minusYears(100);
			}
			LOG.debug("Converted {} into {}", dobStr, dob);
		} catch (Exception e) {
			LOG.warn("Failed to parse "+dobStr+" using "+DATE_FORMATTER, e);
		}
	    
	    try {
			gender = Gender.valueOf(Gender.class, genderStr.trim().toUpperCase());
		} catch (Exception e) {
			LOG.warn("Failed to parse "+genderStr+" into a gender, gender for "+nameStr+ " will be set to null",e);
		}
		try {
			Person p = new Person(nameStr, gender , dob);
		    LOG.debug("Converted args in to instance : {}", p);
		    return p;
		} catch (IllegalArgumentException e) {
			//Can't create a person from this invalid input return null
			//and leave it upto the client to decide if to throw an exception or not
		}
		return null;
	}
	

}