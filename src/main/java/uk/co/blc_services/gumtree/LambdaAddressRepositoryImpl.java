/*
 * Copyright BLC IT Services Ltd 2015
 */
package uk.co.blc_services.gumtree;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;

import uk.co.blc_services.gumtree.domain.Gender;
import uk.co.blc_services.gumtree.domain.Person;
import uk.co.blc_services.gumtree.domain.PersonAgeComparator;

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
public class LambdaAddressRepositoryImpl implements AddressRepository {
	
	private SortedSet<Person> people;
	
	public LambdaAddressRepositoryImpl(Collection<Person> entries){
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
		return findMatching(p-> p.getName().equals(name));
	}

	/* (non-Javadoc)
	 * @see uk.co.blc_services.gumtree.AddressRepository#findPeopleByGender(uk.co.blc_services.gumtree.domain.Gender)
	 */
	@Override
	public List<Person> findPeopleByGender(Gender gender) {
		Optional<Gender> optGender = Optional.ofNullable(gender);
		return findMatching(p-> Optional.ofNullable(p.getGender()).equals(optGender));
	}

	/* (non-Javadoc)
	 * @see uk.co.blc_services.gumtree.AddressRepository#findOldest()
	 */
	@Override
	public List<Person> findOldest() {
		
		LocalDate oldestDOB = this.people.stream()
								.max(PersonAgeComparator.getInstance()).map(Person :: getDob).orElse(null);
		if(oldestDOB == null){
			return Collections.emptyList();
		}
		return findMatching(p-> oldestDOB.equals(p.getDob()));
	}
	

}
