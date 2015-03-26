/*
 * Copyright BLC IT Services Ltd 2015
 */
package uk.co.blc_services.gumtree;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import uk.co.blc_services.gumtree.CriteriaSearchableAddressRepository.PersonCriteria;
import uk.co.blc_services.gumtree.domain.Gender;
import uk.co.blc_services.gumtree.domain.Person;

/**
 * Test for Criteria searchability.
 * 
 * @author dave.clarke@blc-services.co.uk
 *
 */
public class CriteriaSearchableAddressRepositoryTest {
	
	private static CriteriaSearchableAddressRepository addressRepo = null;
	
	@BeforeClass
	public static void setup(){
		//initialise test data
		addressRepo = new CriteriaSearchableAddressRepositoryImpl(AddressRepositoryTest.getTestData());
	}
	
	protected CriteriaSearchableAddressRepository getRepo(){
		return addressRepo;
	}

	@Test
	public void testFindByCriteriaAll() {
		List<Person> matching = getRepo().findMatching(new PersonCriteria(){
			public boolean matches(Person p){
				return true;
			}
		});
		assertEquals(getRepo().getPeople(), matching);
	}
	
	@Test
	public void testFindByCriteriaNone() {
		List<Person> matching = getRepo().findMatching(new PersonCriteria(){
			public boolean matches(Person p){
				return false;
			}
		});
		assertEquals(Collections.EMPTY_LIST, matching);
	}
	
	@Test
	public void testFindByCriteriaGender() {
		List<Person> matching = getRepo().findMatching(new PersonCriteria(){
			public boolean matches(Person p){
				return p.getGender().equals(Gender.MALE);
			}
		});
		//This relies on the findPeopleByGender() having already been unit tested against the
		//expected result in other tests.
		assertEquals(getRepo().findPeopleByGender(Gender.MALE), matching);
	}

}
