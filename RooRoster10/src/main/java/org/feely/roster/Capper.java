package org.feely.roster;

import org.feely.roster.domain.Employee;
import org.springframework.stereotype.Service;

@Service
public class Capper {
	
	private String word;
	public Capper(){
	}
	
	public String toCaps(Employee employee){
		word = employee.getFirstName();
		word=word.toUpperCase();
		return word;
	}
	

}
