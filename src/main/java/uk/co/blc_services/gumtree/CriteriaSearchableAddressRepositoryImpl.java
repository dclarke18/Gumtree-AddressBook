/*
 * Copyright BLC IT Services Ltd 2015
 */
package uk.co.blc_services.gumtree;

import java.util.LinkedList;
import java.util.List;

import uk.co.blc_services.gumtree.domain.Gender;
import uk.co.blc_services.gumtree.domain.Person;

/**
 * Adds criteria search functionality by decorating an {@link AddressRepository} implementation.
 * @author dave.clarke@blc-services.co.uk
 *
 */
public class CriteriaSearchableAddressRepositoryImpl implements CriteriaSearchableAddressRepository {
	
	
	private AddressRepository delegate;
	
	public CriteriaSearchableAddressRepositoryImpl(AddressRepository toDecorate) {
		super();
		this.delegate = toDecorate;
	}

	/* (non-Javadoc)
	 * @see uk.co.blc_services.gumtree.CriteriaSearchableAddressRepository#findMatching(uk.co.blc_services.gumtree.CriteriaSearchableAddressRepository.PersonCriteria)
	 */
	@Override
	public List<Person> findMatching(PersonCriteria criteria) {
		LinkedList<Person> matches = new LinkedList<>();
		for (Person p : delegate.getPeople()) {
			if(criteria.matches(p)){
				matches.add(p);
			}
		}
		return matches;
	}

	public List<Person> getPeople() {
		return delegate.getPeople();
	}

	public void addPerson(Person p) {
		delegate.addPerson(p);
	}

	public List<Person> findPeopleByName(String name) {
		return delegate.findPeopleByName(name);
	}

	public List<Person> findPeopleByGender(Gender gender) {
		return delegate.findPeopleByGender(gender);
	}

	public List<Person> findOldest() {
		return delegate.findOldest();
	}
	
	

}
