/*
 * Copyright BLC IT Services Ltd 2015
 */
package uk.co.blc_services.gumtree;

import java.io.InputStream;

import uk.co.blc_services.gumtree.domain.*;

/**
 * Basic Java Implementation
 * TODO Refactor to use Spring IOC
 * TODO take file to parse as an arg
 * @author dave.clarke@blc-services.co.uk
 *
 */
public class AddressBookApplication {
	
	private static final String DEFAULT_ADDRESS_FILE = "AddressBook";
	private static final String BILL_NAME = "Bill McKnight";
	private static final String PAUL_NAME = "Paul Robinson";
	
	private static final String DEFAULT_VERSION = "Unreleased";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//create parser
		InputStream is = ClassLoader.getSystemResourceAsStream(DEFAULT_ADDRESS_FILE);
		
		AddressBookParser parser = new AddressBookParser();
		AddressRepository repo = new AddressRepositoryImpl(parser.parse(is));
		
		System.out.println("Welcome to the Gumtree Address book - "+getVersion());
		System.out.println("Entries parsed sucessfully:\n"+repo.getPeople());
		
		System.out.println("Q1 - How many males in the book = "+repo.findPeopleByGender(Gender.MALE).size());
		System.out.println("Q2 - The oldest people in the book = "+repo.findOldest());
		//TODO null safety
		Person bill = repo.findPeopleByName(BILL_NAME).get(0);
		Person paul = repo.findPeopleByName(PAUL_NAME).get(0);
		System.out.println("Q3 - Difference in age in days between Bill and Paul = "+repo.getAgeDifferenceInDays(bill, paul));

	}
	
	private static String getVersion(){
		String version = AddressBookApplication.class.getPackage().getImplementationVersion();
		if(version == null){
			version = DEFAULT_VERSION;
		}
		return version;
	}

}
