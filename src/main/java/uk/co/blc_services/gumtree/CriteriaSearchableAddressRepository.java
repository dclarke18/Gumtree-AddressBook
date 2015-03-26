/*
 * Copyright BLC IT Services Ltd 2015
 */
package uk.co.blc_services.gumtree;

import java.util.List;

import uk.co.blc_services.gumtree.domain.Person;

/**
 * 
 * Adds the ability to find people which match critera as defined by the calling client.
 * @author dave.clarke@blc-services.co.uk
 *
 */
public interface CriteriaSearchableAddressRepository extends AddressRepository {
	
	public List<Person> findMatching(PersonCriteria criteria);
	
	static interface PersonCriteria{
		boolean matches(Person p);
	}

}
