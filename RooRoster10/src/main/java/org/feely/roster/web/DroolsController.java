package org.feely.roster.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.feely.roster.domain.Employee;
import org.feely.roster.domain.WorkShift;
import org.feely.roster.drools.RosterGeneratorbak;
import org.feely.roster.service.EmployeeService;
import org.feely.roster.service.WorkShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class DroolsController {
	
	@Autowired
	private EmployeeService emps;
	@Autowired
	private WorkShiftService wss;
	
	
	
	@RequestMapping(value="/drools2")
    public void service(HttpServletResponse res) throws ServletException,IOException
      {
           PrintWriter out=res.getWriter();
           System.out.println("[DroolsInvokerServlet] Inside service() of DroolsInvokerServlet.");
           try
            {
           Long allEmps = emps.countAllEmployees();
           Long allWss = wss.countAllWorkShifts();
           List<WorkShift> workShiftList = wss.findAllWorkShifts();
   		List<Employee> employeeList = emps.findAllEmployees();
           int aWss = Integer.valueOf(allEmps.intValue());
           int aEmps = Integer.valueOf(allWss.intValue());
//              RosterGenerator.solveShiftProblem(aWss,aEmps,workShiftList,employeeList);
              out.println("Check JBoss Console for the Output of Drools Execution.");
            }
           catch(Exception e)
            {
                System.out.println("[DroolsInvokerServlet] throws Exception: "+e);
                e.printStackTrace();
            }
           System.out.println("Exiting service() of DroolsInvokerServlet.");
      }
 

}
