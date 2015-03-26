/*
 * Copyright BLC IT Services Ltd 2015
 */
package uk.co.blc_services.gumtree.domain;

import java.util.Comparator;

/**
 * Compares people by their age ascending (nulls 1st followed by youngest -> oldest)
 * 
 * @author dave.clarke@blc-services.co.uk
 *
 */
public class PersonAgeComparator implements Comparator<Person> {

	private PersonAgeComparator() {
		super();
	}

	public static final PersonAgeComparator INST = new PersonAgeComparator();

	public static PersonAgeComparator getInstance() {
		return INST;
	}

	@Override
	public int compare(Person o1, Person o2) {
		if ((o1 == null || o1.getDob() == null)
				&& (o2 == null || o2.getDob() == null)) {
			// both null dobs or null objects
			return 0;
		} else {
			if (o1 == null || o1.getDob() == null) {
				// only o1 is null
				return -1;
			} else if (o2 == null || o2.getDob() == null) {
				// only o2 is null
				return 1;
			}
		}
		return Math.negateExact(o1.getDob().compareTo(o2.getDob()));
	}

}
