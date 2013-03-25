package org.feely.roster.drools.planner.solver;

import org.drools.planner.core.score.buildin.hardandsoft.DefaultHardAndSoftScore;
import org.drools.planner.core.score.buildin.hardandsoft.HardAndSoftScore;
import org.drools.planner.core.score.director.simple.SimpleScoreCalculator;
import org.feely.roster.domain.Employee;
import org.feely.roster.domain.WorkShift;
import org.feely.roster.drools.domain.ShiftAssignment;

public class RosterScoreCalculator implements
		SimpleScoreCalculator<ShiftAssignment> {

	public HardAndSoftScore calculateScore(ShiftAssignment shiftAssignment) {

		int hardScore = 0;
		int softScore = 0;
		//
		for (WorkShift workShift : shiftAssignment.getWorkShiftList()) {
			int capacity = 0;

			int max = workShift.getDepartment().getMaxEarlyEmps();
			// Calculate usage
			for (Employee employee : shiftAssignment.getEmployeeList()) {
				if (workShift.equals(employee.getWorkshift())) {
					// int score = employee.;
					capacity += 1;
					if (workShift.getShiftType().toString()
							.equals(employee.getPreference().toString())) {
						hardScore += 100;
					}
					if (workShift.getDepartment().getRequiredGrade()
							.equals(employee.getGrade())) {
						hardScore += 150;
					}

				}
			}
			// // Hard constraints

			int overFlow = capacity - max;
			if (capacity > max) {
				hardScore -= (Math.pow(2, overFlow)) * capacity * 10;
			}

		}
		return DefaultHardAndSoftScore.valueOf(hardScore, softScore);
	}
}
