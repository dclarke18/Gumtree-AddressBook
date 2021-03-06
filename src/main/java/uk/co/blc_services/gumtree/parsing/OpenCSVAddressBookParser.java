/*
 * Copyright BLC IT Services Ltd 2015
 */
package uk.co.blc_services.gumtree.parsing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import uk.co.blc_services.gumtree.domain.Person;

/**
 * Parses CSV file in the format Name, Gender, DOB. DOB is in the format
 * dd/MM/yy Uses the OpenCSV parser.
 * Threadsafe.
 * TODO Improve the logging 
 * @author dave.clarke@blc-services.co.uk
 *
 */
public class OpenCSVAddressBookParser implements AddressBookParser {

	/*
	 * (non-Javadoc)
	 * 
	 * @see uk.co.blc_services.gumtree.parsing.AddressBookParser#parse(java.io.
	 * InputStream)
	 */
	@Override
	public List<Person> parse(InputStream is) {
		List<Person> parsedEntries = new LinkedList<>();
		try (CSVReader csvReader = new CSVReader(new BufferedReader(new InputStreamReader(is)))) {

			String[] nextLine;
			while ((nextLine = csvReader.readNext()) != null) {
				// FIXME array index safety!
				if (nextLine.length == 3) {
					Person p = fromStrings(nextLine[0], nextLine[1],
							nextLine[2]);
					if (p != null) {
						parsedEntries.add(p);
					} else {
						LOG.error(
								"Couldn't create a valid person from '{}', '{}', '{}' will return continue with next record.",
								nextLine[0], nextLine[1], nextLine[2]);
					}
				} else {
System.err.println(String.format("Parsed incorrect number of strings {} : {}", nextLine.length, Arrays.asList(nextLine)));
					LOG.error("Parsed incorrect number of strings {} : {}", nextLine.length, Arrays.asList(nextLine));
				}
			}
			return parsedEntries;
		} catch (IOException e) {
			throw new RuntimeException(
					"Failed while attempting to parse input", e);
		}

	}

}
