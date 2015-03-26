/*
 * Copyright BLC IT Services Ltd 2015
 */
package uk.co.blc_services.gumtree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import uk.co.blc_services.gumtree.domain.Gender;
import uk.co.blc_services.gumtree.domain.Person;
import uk.co.blc_services.gumtree.domain.PersonAgeComparator;

/**
 * Service interface for accessing the AddressBook Entries.
 * TODO Should this be named AddressService/AddressBookService?
 * 
 * @author dave.clarke@blc-services.co.uk
 *
 */
public interface AddressRepository {
	
	/**
	 * Gets all the people in the repository sorted by name property.
	 * @return all people
	 */
	public List<Person> getPeople();
	
	/**
	 * Adds an additional person in to the repository.
	 * @param p
	 */
	public void addPerson(Person p);
	
	/**
	 * Finds people with an exact (case sensitive) match on name and returns them.
	 * Null will find all entries without a name set.
	 * If not found will return an empty list.
	 * @param name
	 * @return matching people
	 */
	public List<Person> findPeopleByName(String name);
	
	/**
	 * Gets all the people with a matching gender.
	 * Null will find all entries without a gender set.
	 * If not found will return an empty list.
	 * @param gender
	 * @return matching people
	 */
	public List<Person> findPeopleByGender(Gender gender);
	
	/**
	 * Finds the oldest person/people in the repository and returns them.
	 * If are multiple people in a tie they are all returned.
	 * @return
	 */
	public List<Person> findOldest();
	
	/**
	 * 	TODO Inefficient implementation... throws away the sorted collection
	 * @return
	 */
	public default List<Person> getPeopleSortedByAgeAscending(){
		
		List<Person> ageSortedPeople = new ArrayList<>(getPeople());
		Collections.sort(ageSortedPeople, PersonAgeComparator.getInstance());
		return ageSortedPeople;
	}
	
	/**
	 * 	TODO Inefficient implementation... throws away the sorted collection Override and cache?
	 * @return
	 */
	public default List<Person> getPeopleSortedByAgeDecending(){
		
		List<Person> ageSortedPeople = new ArrayList<>(getPeople());
		Collections.sort(ageSortedPeople, PersonAgeComparator.getInstance().reversed());
		return ageSortedPeople;
	}
	
	public default List<Person> findMatching(PersonCriteria criteria){
		return getPeople().stream().filter(criteria).collect(Collectors.toList());	
	}
	
	static interface PersonCriteria extends Predicate<Person>{
	}
	
	
	

}
