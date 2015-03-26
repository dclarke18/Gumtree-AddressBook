/*
 * Copyright BLC IT Services Ltd 2015
 */
package uk.co.blc_services.gumtree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import uk.co.blc_services.gumtree.domain.Gender;
import uk.co.blc_services.gumtree.domain.Person;
import uk.co.blc_services.gumtree.domain.PersonAgeComparator;

/**
 * Service class which provides access to the entries in the address book.
 * Non threadsafe implementation which doesn't protect the lists returned
 * Inefficent in many areas only suitable for small datasets where performance not an issue.
 * Functional but only a basic implementation to build on.
 * 
 * @author dave.clarke@blc-services.co.uk
 *
 */
public class SimpleAddressRepositoryImpl implements AddressRepository {
	
	private Set<Person> people;
	
	public SimpleAddressRepositoryImpl(Collection<Person> entries){
		this.people = new HashSet<>(entries);
	}

	/* (non-Javadoc)
	 * @see uk.co.blc_services.gumtree.AddressRepository#getPeople()
	 */
	@Override
	public List<Person> getPeople() {
		List<Person> sortedPeople = new ArrayList<>(people);
		Collections.sort(sortedPeople);
		return Collections.unmodifiableList(sortedPeople);
	}

	/* (non-Javadoc)
	 * @see uk.co.blc_services.gumtree.AddressRepository#findPeopleByName(java.lang.String)
	 */
	@Override
	public List<Person> findPeopleByName(String name) {
		List<Person> filtered = new LinkedList<>();
		for (Person person : people) {
			if((person.getName() == null && name ==null) ||
			person.getName().equals(name)){
				filtered.add(person);
			}
		}
		return filtered;
	}

	/* (non-Javadoc)
	 * @see uk.co.blc_services.gumtree.AddressRepository#findPeopleByGender(uk.co.blc_services.gumtree.domain.Gender)
	 */
	@Override
	public List<Person> findPeopleByGender(Gender gender) {
		List<Person> filtered = new LinkedList<>();
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
		//TODO refactor to not use sort! Can be done with cheaper 1 pass iteration
		List<Person> ageSortedPeople = getPeopleSortedByAgeAscending();
		List<Person> oldestPeople = new LinkedList<>();
		for (Person person : ageSortedPeople) {
			if(person.getDob() == null){
				//if they all have null dobs theres no oldest
				break;
			}
			if(oldestPeople.isEmpty() || oldestPeople.get(0).getDob().equals(person.getDob())){
				//if it's the 1st in the list or the same dob as the 1st
				oldestPeople.add(person);
			} else{
				//We can stop looking for oldest people now
				break;
			}
		}
		return oldestPeople;
	}
	
	public List<Person> getPeopleSortedByAgeAscending(){
		//TODO Inefficent implementation... throws away the sorting
		//improve once threadsafe
		
		List<Person> ageSortedPeople = new ArrayList<>(this.people);
		Collections.sort(ageSortedPeople, PersonAgeComparator.getInstance());
		return ageSortedPeople;
	}

	@Override
	public void addPerson(Person p) {
		this.people.add(p);
	}

}
