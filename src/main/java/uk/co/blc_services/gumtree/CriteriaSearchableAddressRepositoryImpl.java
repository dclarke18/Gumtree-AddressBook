/*
 * Copyright BLC IT Services Ltd 2015
 */
package uk.co.blc_services.gumtree;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import uk.co.blc_services.gumtree.domain.Person;

/**
 * @author dave.clarke@blc-services.co.uk
 *
 */
public class CriteriaSearchableAddressRepositoryImpl extends
		AddressRepositoryImpl implements CriteriaSearchableAddressRepository {
	

	public CriteriaSearchableAddressRepositoryImpl(Collection<Person> entries) {
		super(entries);
	}

	/* (non-Javadoc)
	 * @see uk.co.blc_services.gumtree.CriteriaSearchableAddressRepository#findMatching(uk.co.blc_services.gumtree.CriteriaSearchableAddressRepository.PersonCriteria)
	 */
	@Override
	public List<Person> findMatching(PersonCriteria criteria) {
		LinkedList<Person> matches = new LinkedList<>();
		for (Person p : getPeople()) {
			if(criteria.matches(p)){
				matches.add(p);
			}
		}
		return matches;
	}

}
