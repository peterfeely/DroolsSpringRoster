package org.feely.roster.service;

import java.util.List;
import org.feely.roster.domain.Department;
import org.feely.roster.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
    DepartmentRepository departmentRepository;

	public long countAllDepartments() {
        return departmentRepository.count();
    }

	public void deleteDepartment(Department department) {
        departmentRepository.delete(department);
    }

	public Department findDepartment(Long id) {
        return departmentRepository.findOne(id);
    }

	public List<Department> findAllDepartments() {
        return departmentRepository.findAll();
    }

	public List<Department> findDepartmentEntries(int firstResult, int maxResults) {
        return departmentRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

	public void saveDepartment(Department department) {
        departmentRepository.save(department);
    }

	public Department updateDepartment(Department department) {
        return departmentRepository.save(department);
    }
}
