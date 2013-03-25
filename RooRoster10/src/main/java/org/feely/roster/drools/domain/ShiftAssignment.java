package org.feely.roster.drools.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.drools.planner.api.domain.solution.PlanningEntityCollectionProperty;
import org.drools.planner.core.score.buildin.hardandsoft.HardAndSoftScore;
import org.drools.planner.core.solution.Solution;
import org.feely.roster.domain.Employee;
import org.feely.roster.domain.WorkShift;

public class ShiftAssignment extends AbstractPersistable implements
		Solution<HardAndSoftScore> {

	private List<WorkShift> workShiftList;

	private List<Employee> employeeList;

	private HardAndSoftScore score;


	public List<WorkShift> getWorkShiftList() {
		return workShiftList;
	}

	public void setWorkShiftList(List<WorkShift> workShiftList) {
		this.workShiftList = workShiftList;
	}

	@PlanningEntityCollectionProperty
	public List<Employee> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}

	public HardAndSoftScore getScore() {
		return score;
	}

	public void setScore(HardAndSoftScore score) {
		this.score = score;
	}
	
	public Collection<? extends Object> getProblemFacts() {
		List<Object> facts = new ArrayList<Object>();
		facts.addAll(workShiftList);
		// Do not add the planning entity's (employeeList) because that will be
		// done automatically
		return facts;
	}

	/**
	 * Clone will only deep copy the {@link #employeeList}.
	 */
	public ShiftAssignment cloneSolution() {
		ShiftAssignment clone = new ShiftAssignment();
		clone.id = id;
		clone.workShiftList = workShiftList;
		List<Employee> clonedEmployeeList = new ArrayList<Employee>(
				employeeList.size());
		for (Employee employee : employeeList) {
			Employee clonedProcess = employee.clone();
			clonedEmployeeList.add(clonedProcess);
		}
		clone.employeeList = clonedEmployeeList;
		clone.score = score;
		return clone;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (id == null || !(o instanceof ShiftAssignment)) {
			return false;
		} else {
			ShiftAssignment other = (ShiftAssignment) o;
			if (employeeList.size() != other.employeeList.size()) {
				return false;
			}
			for (Iterator<Employee> it = employeeList.iterator(), otherIt = other.employeeList
					.iterator(); it.hasNext();) {
				Employee employee = it.next();
				Employee otherEmployee = otherIt.next();
				// Notice: we don't use equals()
				if (!employee.solutionEquals(otherEmployee)) {
					return false;
				}
			}
			return true;
		}
	}

	public int hashCode() {
		HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
		for (Employee employee : employeeList) {
			// Notice: we don't use hashCode()
			hashCodeBuilder.append(employee.solutionHashCode());
		}
		return hashCodeBuilder.toHashCode();
	}

}
