/*
 * Copyright BLC IT Services Ltd 2015
 */
package uk.co.blc_services.gumtree;

import java.util.List;

import uk.co.blc_services.gumtree.domain.*;

/**
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
	 * Finds people with an exact (case sensitive) match on name and returns them.
	 * Null will find all entries without a name set.
	 * If not found will return an empty list.
	 * @param name
	 * @return matching people
	 */
	public List<Person> getPeopleByName(String name);
	
	/**
	 * Gets all the people with a matching gender.
	 * Null will find all entries without a gender set.
	 * If not found will return an empty list.
	 * @param gender
	 * @return matching people
	 */
	public List<Person> getPeopleOfGender(Gender gender);
	
	/**
	 * Finds the oldest person/people in the repository and returns them.
	 * If are multiple people in a tie they are all returned.
	 * @return
	 */
	public List<Person> getOldest();
	
	
	/**
	 * Calculates the difference in age between 2 people and returns this in days.
	 * Always a positive number or zero if there is no difference.
	 * 
	 * TODO This method doesn't really belong in this interface. Find a  better place
	 * 
	 * @param person a
	 * @param person b
	 * @return days between their dob
	 */
	public int ageDifferenceInDays(Person a, Person b);
	
	

}
