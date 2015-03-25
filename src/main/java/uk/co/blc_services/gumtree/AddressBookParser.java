/*
 * Copyright BLC IT Services Ltd 2015
 */
package uk.co.blc_services.gumtree;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	
	private static final Logger LOG = LoggerFactory.getLogger(AddressBookParser.class);
	
	/* These are threadsafe unlike SimpleDateFormat used to be */
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yy");
	
	public List<Person> parse(InputStream is){
		List<Person> parsedEntries = new ArrayList<>();
		try (InputStreamReader reader = new InputStreamReader(is)){
			
			LOG.debug("Attempting to parse stream using default encoding '{}'", reader.getEncoding());
			Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
			LOG.info("Parsed records from CSV reader using encoding {}", reader.getEncoding());
			for (CSVRecord record : records) {
			    String name = record.get(0);
			    String genderString = record.get(1);
			    String dobString = record.get(2);
			    LOG.debug("Parsed entry '{}', '{}', '{}' from {}", name, genderString, dobString, record);
			    LocalDate dob = null;
			    Gender gender = null;
			    try {
					dob = LocalDate.parse(dobString.trim(), DATE_FORMATTER);
					//Java8 doesn't support the joda time pivot year concept (yet?).
					//check that this date isn't in the future if it is deduct 100 years
					//TODO - Get the users to accept an unambigous yyyy date format!!
					if(LocalDate.now().isBefore(dob) ){
						dob = dob.minusYears(100);
					}
					LOG.debug("Converted {} into {}", dobString, dob);
				} catch (Exception e) {
					LOG.warn("Failed to parse "+dobString+" using "+DATE_FORMATTER, e);
				}
			    
			    try {
					gender = Gender.valueOf(Gender.class, genderString.trim().toUpperCase());
				} catch (Exception e) {
					LOG.warn("Failed to parse "+genderString+" into a gender, gender for "+name+ " will be set to null",e);
				}
			    Person p;
				try {
					p = new Person(name, gender , dob);
				    parsedEntries.add(p);
				    LOG.debug("Added : {}\n total parsed = {}", p, parsedEntries.size());
				} catch (Exception e) {
					LOG.error("Couldn't create a valid person from {} will skip this entry and continue.", record);
				}


			}
			return parsedEntries;
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
