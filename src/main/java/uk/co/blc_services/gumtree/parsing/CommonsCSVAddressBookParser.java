/*
 * Copyright BLC IT Services Ltd 2015
 */
package uk.co.blc_services.gumtree.parsing;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.blc_services.gumtree.domain.Gender;
import uk.co.blc_services.gumtree.domain.Person;

/**
 * Parses CSV file in the format Name, Gender, DOB.
 * DOB is in the format dd/MM/yy
 * Uses Apache Commons CSV which is not threadsafe.
 * TODO Raise bug with Commons CSV about the code or if not their documentation as it's not clear
 * that the parser isn't threadsafe. See github issue #5.
 * 
 * @author dave.clarke@blc-services.co.uk
 *
 */
public class CommonsCSVAddressBookParser implements AddressBookParser {
	
	private static final Logger LOG = LoggerFactory.getLogger(CommonsCSVAddressBookParser.class);
	
	public List<Person> parse(InputStream is){
		List<Person> parsedEntries = new LinkedList<>();
		try (InputStreamReader reader = new InputStreamReader(is)){
			
			LOG.debug("Attempting to parse stream using default encoding '{}'", reader.getEncoding());
			Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
			LOG.info("Parsed records from CSV reader using encoding {}", reader.getEncoding());
			for (CSVRecord record : records) {
			    String name = record.get(0);
			    String gender = record.get(1);
			    String dob = record.get(2);
			    LOG.debug("Parsed entry '{}', '{}', '{}' from {}", name, gender, dob, record);
			    
			    Person p = fromStrings(name, gender, dob);
			    if(p != null){
			    	parsedEntries.add(p);
			    } else {
			    	LOG.error("Couldn't create a valid person from '{}', '{}', '{}' will return continue with next record.", name, gender, dob);
			    }
			}
			return parsedEntries;
			
		} catch (IOException e) {
			throw new RuntimeException("Failed while attempting to parse input", e);
		}
	}

}
