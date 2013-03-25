package org.feely.roster.service;

import java.util.List;

import org.feely.roster.domain.Employee;

public interface EmployeeService {

	public abstract long countAllEmployees();


	public abstract void deleteEmployee(Employee employee);


	public abstract Employee findEmployee(Long id);


	public abstract List<Employee> findAllEmployees();


	public abstract List<Employee> findEmployeeEntries(int firstResult, int maxResults);


	public abstract void saveEmployee(Employee employee);


	public abstract Employee updateEmployee(Employee employee);

}
