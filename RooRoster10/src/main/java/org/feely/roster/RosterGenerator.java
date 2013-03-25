package org.feely.roster;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.drools.planner.config.SolverFactory;
import org.drools.planner.config.XmlSolverFactory;
import org.drools.planner.core.Solver;
import org.feely.roster.domain.Employee;
import org.feely.roster.domain.Shift;
import org.feely.roster.domain.WorkShift;
import org.feely.roster.drools.domain.ShiftAssignment;
import org.feely.roster.drools.persistence.ShiftAssignmentGenerator;
import org.feely.roster.service.EmployeeService;
import org.feely.roster.service.ShiftService;
import org.feely.roster.service.WorkShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class RosterGenerator {

	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private WorkShiftService workShiftService;
	@Autowired
	private ShiftService shiftService;

	private Long empCount;
	private Long workCount;
	private List<WorkShift> workShiftList;
	private List<Employee> employeeList;
	private int workCountint;
	private int empCountint;

	public RosterGenerator() {
	}

	public void createRoster() {
		empCount = employeeService.countAllEmployees();
		workCount = workShiftService.countAllWorkShifts();
		workShiftList = workShiftService.findAllWorkShifts();
		employeeList = employeeService.findAllEmployees();
		workCountint = Integer.valueOf(workCount.intValue());
		empCountint = Integer.valueOf(empCount.intValue());

		InputStream inputStream = null;
//		Resource resource = null;
		
		ClassPathResource cpr = new ClassPathResource("META-INF/myconfig.xml");
		try {
			 inputStream = cpr.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		try {
//			resource = new FileSystemResource(
//					"src/main/resources/META-INF/myconfig.xml");
//			inputStream = resource.getInputStream();
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}

		SolverFactory solverFactory = new XmlSolverFactory()
				.configure(inputStream);
		Solver solver = solverFactory.buildSolver();
		ShiftAssignment unsolvedShiftAssignment = new ShiftAssignmentGenerator()
				.createShiftAssignment(workCountint, empCountint,
						workShiftList, employeeList);
		// Solve the problem
		solver.setPlanningProblem(unsolvedShiftAssignment);
		solver.solve();
		ShiftAssignment solvedShiftAssignment = (ShiftAssignment) solver
				.getBestSolution();
		System.out
				.println("\nSolved shiftRoster wioth 4 departments and 1oo employees:\n"
						+ "  Employee Details \t    ShiftPref \t assigned \t RequiredGrade \t EmployeeGrade :\n"
						+ toDisplayString(solvedShiftAssignment));
		persistWorkShift(solvedShiftAssignment);

	}

	public void persistWorkShift(ShiftAssignment shiftAssignment) {
		Date today = new Date();

		for (WorkShift workShift : shiftAssignment.getWorkShiftList()) {

			Shift shift = new Shift();
			shift.setShiftDate(today);
			shiftService.saveShift(shift);
			workShift = workShiftService.findWorkShift(workShift.getId());
			shift.setWorkShift(workShift);

			shiftService.updateShift(shift);
			Long workshiftId = workShift.getId();
			Long shiftId = shift.getId();
			persistEmployeeShift(shiftAssignment, shiftId, workshiftId);
		}
	}

	public void persistEmployeeShift(ShiftAssignment shiftAssignment,
			Long shiftId, Long workshiftId) {
		Shift myShift = shiftService.findShift(shiftId);
		for (Employee ployee : shiftAssignment.getEmployeeList()) {
			Employee myEmp = employeeService.findEmployee(ployee.getId());
			Long a = ployee.getWorkshift().getId();
			Long b = workshiftId;
			int a1 = Integer.valueOf(a.intValue());
			int b1 = Integer.valueOf(b.intValue());
			if (a1 == b1) {
				myShift.getEmployees().add(myEmp);
			}

		}
		shiftService.updateShift(myShift);
	}

	public String toDisplayString(ShiftAssignment shiftAssignment) {
		StringBuilder displayString = new StringBuilder();
		for (Employee employee : shiftAssignment.getEmployeeList()) {
			WorkShift workShift = employee.getWorkshift();
			displayString
					.append(employee.getLabel())
					.append("\t")
					.append(" -> ")
					.append(employee.getPreference())
					.append("\t")
					.append(" got -> ")
					.append(workShift.getShiftType())
					.append("\t")
					// .append(workShift.getDepartment().getDept()).append("\t")
					// .append(workShift.getDepartment().getRequiredGrade())
					.append(" wanted-> ").append(employee.getGrade())
					.append("\t").append(" -> ")
					.append(workShift == null ? null : workShift.getId())
					.append("\n");
		}
		return displayString.toString();
	}

}
