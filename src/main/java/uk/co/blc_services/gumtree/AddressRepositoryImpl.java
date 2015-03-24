/*
 * Copyright BLC IT Services Ltd 2015
 */
package uk.co.blc_services.gumtree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import uk.co.blc_services.gumtree.domain.Gender;
import uk.co.blc_services.gumtree.domain.Person;

/**
 * Service class which provides access to the entries in the address book.
 * Non threadsafe implementation which exposes the underlying collection
 * so is open to modification.
 * @author dave.clarke@blc-services.co.uk
 *
 */
public class AddressRepositoryImpl implements AddressRepository {
	
	private List<Person> people;
	
	public AddressRepositoryImpl(Collection<Person> entries){
		this.people = new ArrayList<>(entries);
		Collections.sort(this.people);
	}

	/* (non-Javadoc)
	 * @see uk.co.blc_services.gumtree.AddressRepository#getPeople()
	 */
	@Override
	public List<Person> getPeople() {
		return people;
	}

	/* (non-Javadoc)
	 * @see uk.co.blc_services.gumtree.AddressRepository#findPeopleByName(java.lang.String)
	 */
	@Override
	public List<Person> findPeopleByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see uk.co.blc_services.gumtree.AddressRepository#findPeopleByGender(uk.co.blc_services.gumtree.domain.Gender)
	 */
	@Override
	public List<Person> findPeopleByGender(Gender gender) {
		List<Person> filtered = new ArrayList<>();
		for (Person person : people) {
			if((person.getGender() == null && gender ==null) ||
			person.getGender().equals(gender)){
				filtered.add(person);
			}
		}
		return filtered;
	}

	/* (non-Javadoc)
	 * @see uk.co.blc_services.gumtree.AddressRepository#findOldest()
	 */
	@Override
	public List<Person> findOldest() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see uk.co.blc_services.gumtree.AddressRepository#getAgeDifferenceInDays(uk.co.blc_services.gumtree.domain.Person, uk.co.blc_services.gumtree.domain.Person)
	 */
	@Override
	public int getAgeDifferenceInDays(Person a, Person b) {
		// TODO Auto-generated method stub
		return 0;
	}

}
