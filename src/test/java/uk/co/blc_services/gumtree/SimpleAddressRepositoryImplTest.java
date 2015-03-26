/*
 * Copyright BLC IT Services Ltd 2015
 */
package uk.co.blc_services.gumtree;

import org.junit.BeforeClass;

/**
 * Test for the SimpleAddressRepositoryImpl
 * All tests defined in the {@link AddressRepositoryTest} class (Template pattern)
 * 
 * @author dave.clarke@blc-services.co.uk
 *
 */
public class SimpleAddressRepositoryImplTest extends AddressRepositoryTest{
	
	private static AddressRepository repo;
	
	@BeforeClass
	public static void setup(){
		//initialise test data
		repo = new SimpleAddressRepositoryImpl(getTestData());
	}

	@Override
	protected AddressRepository getRepo() {
		return repo;
	}
	
	

}
