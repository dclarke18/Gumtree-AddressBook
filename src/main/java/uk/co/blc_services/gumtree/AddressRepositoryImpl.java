/*
 * Copyright BLC IT Services Ltd 2015
 */
package uk.co.blc_services.gumtree;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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
		List<Person> filtered = new ArrayList<>();
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
		//TODO refactor to not use sort! Can be done with cheaper 1 pass iteration
		List<Person> ageSortedPeople = getPeopleSortedByAgeAscending();
		List<Person> oldestPeople = new ArrayList<>();
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

	/* (non-Javadoc)
	 * @see uk.co.blc_services.gumtree.AddressRepository#getAgeDifferenceInDays(uk.co.blc_services.gumtree.domain.Person, uk.co.blc_services.gumtree.domain.Person)
	 */
	@Override
	public long getAgeDifferenceInDays(Person a, Person b) {
		if(a == null || a.getDob() == null || b == null || b.getDob() == null){
			throw new IllegalArgumentException("Can't give the difference between "+a+" and "+b);
		}
		return ChronoUnit.DAYS.between(a.getDob(), b.getDob());
	}
	
	static class PersonAgeComparator implements Comparator<Person>{
		
		private PersonAgeComparator() {
			super();
		}
		
		
		public static final PersonAgeComparator INST = new PersonAgeComparator();
		
		public static PersonAgeComparator getInstance(){
			return INST;
		}

		@Override
		public int compare(Person o1, Person o2) {
			if((o1 == null || o1.getDob() == null) && (o2 == null || o2.getDob() == null)){
				//both null dobs or null objects
				return 0;
			} else {
				if(o1 == null || o1.getDob() == null){
					//only o1 is null 
					return 1;
				} else if(o2 == null || o2.getDob() == null){
					//only o2 is null
					return -1;
				}
			}
			return o1.getDob().compareTo(o2.getDob());
		}

	}

}
