/*
 * Copyright BLC IT Services Ltd 2015
 */
package uk.co.blc_services.gumtree;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import uk.co.blc_services.gumtree.domain.Gender;
import uk.co.blc_services.gumtree.domain.Person;

/**
 * Service class which provides access to the entries in the address book.
 * Removes duplicates from the added entries and sorts by natural order as
 * per {@link Person#compareTo(Person)}.
 * 
 * Threadsafe implementation which wraps and returned Lists in {@link Collections#unmodifiableList(List)}
 * so is closed to modification except through the add method.
 * 
 * TODO Use cases are uncertain - could probably do with multiple implementations with different threadsafety & collections implmentations
 * all extending a base class. Eg. CopyOnWriteArrayList for 'directory' read often write rarely cases.
 * 
 * 
 * @author dave.clarke@blc-services.co.uk
 *
 */
public class AddressRepositoryImpl implements AddressRepository {
	
	private SortedSet<Person> people;
	
	public AddressRepositoryImpl(Collection<Person> entries){
		this.people = new ConcurrentSkipListSet<>(entries);
	}

	/* (non-Javadoc)
	 * @see uk.co.blc_services.gumtree.AddressRepository#getPeople()
	 */
	@Override
	public List<Person> getPeople() {
		//Duplicating the collection is painful.. if we need the underlying
		//to be protected and threadsafe we could cache this List. Or consider changing the interface.
		return Collections.unmodifiableList(new ArrayList<>(people));
	}
	

	/*
	 * (non-Javadoc)
	 * @see uk.co.blc_services.gumtree.AddressRepository#addPerson(uk.co.blc_services.gumtree.domain.Person)
	 */
	@Override
	public void addPerson(Person p){
		people.add(p);
	}

	/* (non-Javadoc)
	 * @see uk.co.blc_services.gumtree.AddressRepository#findPeopleByName(java.lang.String)
	 */
	@Override
	public List<Person> findPeopleByName(String name) {
		List<Person> matchingPeople = this.people.stream()
				.filter(p-> p.getName().equals(name)).collect(Collectors.toList());
		
		return Collections.unmodifiableList(matchingPeople);
	}

	/* (non-Javadoc)
	 * @see uk.co.blc_services.gumtree.AddressRepository#findPeopleByGender(uk.co.blc_services.gumtree.domain.Gender)
	 */
	@Override
	public List<Person> findPeopleByGender(Gender gender) {
		List<Person> filtered = this.people.stream()
				.filter(p-> (p.getGender() == null && gender ==null) ||
			p.getGender().equals(gender)).collect(Collectors.toList());
		
		return Collections.unmodifiableList(filtered);
	}

	/* (non-Javadoc)
	 * @see uk.co.blc_services.gumtree.AddressRepository#findOldest()
	 */
	@Override
	public List<Person> findOldest() {
		
		LocalDate oldestDOB = LocalDate.now();
		//TODO can we do this bit all in one iteration through the collection?
		//Is there a cheaper way?
		for (Person person : this.people) {
			if(person.getDob() != null && oldestDOB.isAfter(person.getDob())){
				oldestDOB = person.getDob();
			}
		}
		final LocalDate filterDOB = oldestDOB;
		List<Person> oldestPeople = this.people.stream()
				.filter(p-> p.getDob() != null && p.getDob().equals(filterDOB))
				.collect(Collectors.toList());
		
		return Collections.unmodifiableList(oldestPeople);
	}
	
	public List<Person> getPeopleSortedByAgeAscending(){
		//TODO Inefficent implementation... throws away the sorting
		//improve once threadsafe
		
		List<Person> ageSortedPeople = new ArrayList<>(this.people);
		Collections.sort(ageSortedPeople, PersonAgeComparator.getInstance());
		return ageSortedPeople;
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
