package org.feely.roster.service;

import java.util.List;

import org.feely.roster.domain.Department;

public interface DepartmentService {

	public abstract long countAllDepartments();


	public abstract void deleteDepartment(Department department);


	public abstract Department findDepartment(Long id);


	public abstract List<Department> findAllDepartments();


	public abstract List<Department> findDepartmentEntries(int firstResult, int maxResults);


	public abstract void saveDepartment(Department department);


	public abstract Department updateDepartment(Department department);

}
