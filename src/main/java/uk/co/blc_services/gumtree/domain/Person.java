/*
 * Copyright BLC IT Services Ltd 2015
 */
package uk.co.blc_services.gumtree.domain;

import java.time.LocalDate;

/**
 * Represents an individual and some basic details
 * about them.
 * @author dave.clarke@blc-services.co.uk
 *
 */
public class Person {
	
	/**
	 * Persons full name including any titles
	 */
	private String name;
	
	/**
	 * If they are male or female
	 */
	private Gender gender;
	
	/**
	 * The date on which they were born
	 */
	private LocalDate dob;
	
	
	public Person(String name, Gender gender, LocalDate dob) {
		super();
		this.name = name;
		this.gender = gender;
		this.dob = dob;
	}
	
	public String getName() {
		return name;
	}
	public Gender getGender() {
		return gender;
	}
	public LocalDate getDob() {
		return dob;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dob == null) ? 0 : dob.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (dob == null) {
			if (other.dob != null)
				return false;
		} else if (!dob.equals(other.dob))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", gender=" + gender+ ", dob=" + dob + "]";
	}
	
	
	

}
