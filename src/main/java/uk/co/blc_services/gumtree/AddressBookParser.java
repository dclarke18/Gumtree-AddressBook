/*
 * Copyright BLC IT Services Ltd 2015
 */
package uk.co.blc_services.gumtree;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import uk.co.blc_services.gumtree.domain.*;

/**
 * Parses CSV file in the format Name, Gender, DOB.
 * DOB is in the format dd/MM/yy
 * Uses Apache Commons CSV.
 * TODO Add logging
 * @author dave.clarke@blc-services.co.uk
 *
 */
public class AddressBookParser {
	
	/* These are threadsafe unlike SimpleDateFormat used to be */
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yy");
	
	public List<Person> parse(InputStream is){
		List<Person> parsedEntries = new ArrayList<>();
		try (Reader reader = new InputStreamReader(is)){
			
			Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
			
			for (CSVRecord record : records) {
			    String name = record.get(0);
			    String genderString = record.get(1);
			    String dobString = record.get(2);
			    LocalDate dob = null;
			    Gender gender = null;
			    try {
					dob = LocalDate.parse(dobString.trim(), DATE_FORMATTER);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    
			    try {
					gender = Gender.valueOf(Gender.class, genderString.trim().toUpperCase());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    		
			    parsedEntries.add(new Person(name, gender , dob));
			    
			}
			return parsedEntries;
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
