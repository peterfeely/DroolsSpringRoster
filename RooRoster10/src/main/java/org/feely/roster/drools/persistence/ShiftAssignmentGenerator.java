package org.feely.roster.drools.persistence;

import java.util.List;

import org.feely.roster.domain.Employee;
import org.feely.roster.domain.WorkShift;
import org.feely.roster.drools.domain.ShiftAssignment;

public class ShiftAssignmentGenerator {


	public ShiftAssignmentGenerator() {
	}



	public ShiftAssignment createShiftAssignment(int workShiftListSize,
			int employeeListSize, List<WorkShift> myworkShiftList, List<Employee> myemployeeList) {
		ShiftAssignment shiftAssignment = new ShiftAssignment();
		shiftAssignment.setId(0L);
		createWorkShiftList(shiftAssignment, workShiftListSize,myworkShiftList);
		createEmployeeList(shiftAssignment, employeeListSize, myemployeeList);
		return shiftAssignment;
	}

	@SuppressWarnings("unchecked")
	private void createWorkShiftList(ShiftAssignment shiftAssignment,
			int workShiftListSize, List<WorkShift> myworkShiftList) {
		shiftAssignment.setWorkShiftList(myworkShiftList);
	}

	@SuppressWarnings("unchecked")
	private void createEmployeeList(ShiftAssignment shiftAssignment,
			int employeeListSize, List<Employee> myemployeeList) {

		shiftAssignment.setEmployeeList(myemployeeList);
	}

}

