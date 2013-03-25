package org.feely.roster;

import org.feely.roster.domain.Employee;
import org.feely.roster.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NameGenerator {
	
	@Autowired
	private Capper myCapper;
	@Autowired
	private EmployeeService empService;
	@Autowired
	private Employee employee;
	
	private String name;
	
	public NameGenerator(){
	}
	
	
	
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String nameMaker(){
		employee=empService.findEmployee((long) 1);
		employee.setFirstName("alfred");
		empService.updateEmployee(employee);
		name = employee.getFirstName();
		
		name = myCapper.toCaps(employee);
		return name;
		
	}
	

	

}
