package org.feely.roster.drools;

import java.io.InputStream;
import java.util.List;

import org.drools.planner.config.SolverFactory;
import org.drools.planner.config.XmlSolverFactory;
import org.drools.planner.core.Solver;
import org.feely.roster.NameGenerator;
import org.feely.roster.domain.Employee;
import org.feely.roster.domain.Shift;
import org.feely.roster.domain.WorkShift;
import org.feely.roster.drools.domain.ShiftAssignment;
import org.feely.roster.drools.persistence.ShiftAssignmentGenerator;
import org.feely.roster.service.EmployeeService;
import org.feely.roster.service.ShiftService;
import org.feely.roster.service.WorkShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public class RosterGeneratorbak {
	
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private WorkShiftService workShiftService;
	@Autowired
	private ShiftService shiftService;
//	
	private Long empCount;
	private Long workCount;
	private List<WorkShift> workShiftList;
	private List<Employee> employeeList;
	private int workCountint;
	private int empCountint;
	
	public RosterGeneratorbak(){
		empCount = employeeService.countAllEmployees();
		workCount = workShiftService.countAllWorkShifts();
		workShiftList = workShiftService.findAllWorkShifts();
		employeeList = employeeService.findAllEmployees();
		workCountint = Integer.valueOf(workCount.intValue());
		empCountint = Integer.valueOf(empCount.intValue());
	}
	
	
	
	public void solveShiftProblem() {
		InputStream inputStream = null;
		Resource resource = null;

		try {
			resource = new FileSystemResource("src/main/resources/META-INF/myconfig.xml");
			inputStream = resource.getInputStream();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		SolverFactory solverFactory = new XmlSolverFactory()
				.configure(inputStream);
		Solver solver = solverFactory.buildSolver();
		ShiftAssignment unsolvedShiftAssignment = new ShiftAssignmentGenerator()
				.createShiftAssignment(workCountint, empCountint,workShiftList,employeeList);
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
	
	public  void persistWorkShift(ShiftAssignment shiftAssignment) {
		Employee joe = employeeService.findEmployee((long) 3);
		for (Employee employee: shiftAssignment.getEmployeeList()) {
			System.out.println(employee.getFirstName()+" "+employee.getId());
			
			System.out.println(joe.getFirstName());
//			employee.setFirstName("ann");
//			emps.saveEmployee(employee);
//			System.out.println(employee.getFirstName());
//			Shift shift = new Shift();
//			shift.setWorkShift(employee.getWorkshift());
//			System.out.println(employee.getWorkshift().getShiftDate());
//			employee.getShift().add(shift);
//			System.out.println(employee.getLabel());
//			emps.updateEmployee(employee);
//			shoot.saveShift(shift);
		}
		
	}
	
	public  String toDisplayString(ShiftAssignment shiftAssignment) {
		return null;
//		StringBuilder displayString = new StringBuilder();
//		for (Employee employee : shiftAssignment.getEmployeeList()) {
//			WorkShift workShift = employee.getWorkshift();
//			displayString.append(employee.getLabel()).append("\t")
//					.append(" -> ").append(employee.getPreference())
//					.append("\t").append(" got -> ")
//					.append(workShift.getShiftType()).append("\t")
//					.append(workShift.getDepartment().getDept())
//					.append("\t")
//					.append(workShift.getDepartment().getRequiredGrade())
//					.append(" wanted-> ").append(employee.getGrade())
//					.append("\t").append(" -> ")
//					.append(workShift == null ? null : workShift.getShiftDate())
//					.append("\n");
//		}
//		return displayString.toString();
	}

}
