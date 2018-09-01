package com.example.demo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class RecordsController {
	private HashMap<Integer, HashSet<Integer>> matchingRecords = new HashMap<Integer, HashSet<Integer>>();
	private HashMap<Integer, Person> allRecords = new HashMap<Integer, Person>();

	@RequestMapping("/getRecords")
	public @ResponseBody TreeMap<String, ArrayList<Person>> greeting() {
		getFile();
		getPossibleDuplicates();
		
		TreeMap<String, ArrayList<Person>> returnObject = new TreeMap<String, ArrayList<Person>>();
		
		int duplicateCount = 1;
		ArrayList<Person> nonDuplicate = new ArrayList<Person>();

		for(Integer key: matchingRecords.keySet()){
			HashSet<Integer> tempHashSet = matchingRecords.get(key);
			if(tempHashSet.isEmpty()){
				nonDuplicate.add(allRecords.get(key));
			}else{
				ArrayList<Person> tempArrayList = new ArrayList<Person>();
				Iterator<Integer> it = tempHashSet.iterator();
				tempArrayList.add(allRecords.get(key));
			    while(it.hasNext()){
			    	tempArrayList.add(allRecords.get(it.next()));	
			    }
			    returnObject.put("duplicate" + duplicateCount, tempArrayList);
		    	duplicateCount++;
			}
		}
		
		returnObject.put("nonDuplicate", nonDuplicate);
		
		return returnObject;
	}
	
	private void getPossibleDuplicates(){
		HashSet<Integer> processedIds = new HashSet<Integer>();
		Person temp = null;
		String emailid = "";
		int excludeID = 0;
		
		for(Integer key: allRecords.keySet()){
			temp = allRecords.get(key);
			emailid = temp.getEmail();
			excludeID = temp.getId();
			
			if(!processedIds.contains(excludeID)){
				processedIds.add(excludeID);
				HashSet<Integer> mathchingIds = getListOfMatchingIds(excludeID, emailid);
				matchingRecords.put(excludeID, mathchingIds);
				
				Iterator<Integer> it = mathchingIds.iterator();
			     while(it.hasNext()){
			    	 processedIds.add(it.next());
			     }
			}
			
		}
	}
	
	private HashSet<Integer> getListOfMatchingIds(int excludeID, String emailid){
		HashSet<Integer> mathchingIds = new HashSet<Integer>();
		Person temp = null;
		
		for(Integer key: allRecords.keySet()){
			if(key != excludeID){
				temp = allRecords.get(key);
				if(temp.getEmail().equals(emailid))
					mathchingIds.add(temp.getId());
			}
		}
		
		return mathchingIds;
	}
	
	private void getFile() {
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		InputStream inputStream = getClass().getResourceAsStream("/static/normal.csv");
		
		try {

			br = new BufferedReader(new InputStreamReader(inputStream));
					
			int count = 0;
			while ((line = br.readLine()) != null) {
				if(count == 0){
					count++;
					continue;
				}
				
				String[] personData = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
						//line.split(cvsSplitBy);
				Person tempPerson = new Person();
				tempPerson.setId(count);
				tempPerson.setFirstName(personData[1]);
				tempPerson.setLastName(personData[2]);
				tempPerson.setCompany(personData[3]);
				tempPerson.setEmail(personData[4]);
				tempPerson.setAddress1(personData[5]);
				tempPerson.setAddress2(personData[6]);
				tempPerson.setZip(personData[7]);
				tempPerson.setCity(personData[8]);
				tempPerson.setState(personData[9]);
				tempPerson.setStateAbbr(personData[10]);
				tempPerson.setPhone(personData[11]);
				allRecords.put(count, tempPerson);
				count++;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}