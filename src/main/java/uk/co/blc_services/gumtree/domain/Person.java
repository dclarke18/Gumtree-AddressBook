/*
 * Copyright BLC IT Services Ltd 2015
 */
package uk.co.blc_services.gumtree.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


/**
 * Represents an individual and some basic details
 * about them.
 * Immutable instances so inherently threadsafe.
 * @author dave.clarke@blc-services.co.uk
 *
 */
public class Person implements Comparable<Person>, Serializable{
	
	private static final long serialVersionUID = 8123848616276231743L;

	/**
	 * Constructor for Person instances
	 * @param name
	 * @param gender
	 * @param dob
	 * @throws IllegalArgumentException if the instance doesn't pass the {@link #validate(Person)} validation
	 */
	public Person(String name, Gender gender, LocalDate dob) {
		super();
		this.name = name;
		this.gender = gender;
		this.dob = dob;
		validate(this);
	}
	
	
	/**
	 * Checks the person has a non null non empty name.
	 * TODO Add Guava dependency for StringUtils
	 * TODO Refactor validation logic out of the bean - perhaps hibernate validator?
	 * @param p
	 */
	public static void validate(Person p){
		if(p == null || p.getName() == null || p.getName().trim().isEmpty()){
			throw new IllegalArgumentException("Invalid person instance - must have a non null name");
		}
	}
	
	
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
	
	public String getName() {
		return name;
	}
	public Gender getGender() {
		return gender;
	}
	public LocalDate getDob() {
		return dob;
	}
	
	/**
	 * Calculates the difference in age between this and another person.
	 * Always a positive number or zero if there is no difference.
	 * If the toCompare person or this has no DOB will return null.
	 * 
	 * @param person a
	 * @return days between their dob
	 */
	public Long getAgeDifferenceInDays(Person toCompare) {
		if(this.getDob() == null || toCompare == null || toCompare.getDob() == null){
			return null;
		}
		return Math.abs(ChronoUnit.DAYS.between(this.getDob(), toCompare.getDob()));
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
	@Override
	public int compareTo(Person o) {
		if(o == null || o.getName() == null){
			return -1;
		} else {
			return this.getName().compareTo(o.getName());
		}
	}
	
	
	

}
