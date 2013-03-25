package org.feely.roster.web;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.feely.roster.domain.Employee;
import org.feely.roster.reference.Grade;
import org.feely.roster.reference.Preference;
import org.feely.roster.reference.ShiftPattern;
import org.feely.roster.service.EmployeeService;
import org.feely.roster.service.PersonalHolidayService;
import org.feely.roster.service.PublicHolidayService;
import org.feely.roster.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

@RequestMapping("/employees")
@Controller
public class EmployeeController {

	@Autowired
    EmployeeService employeeService;

	@Autowired
    PersonalHolidayService personalHolidayService;

	@Autowired
    PublicHolidayService publicHolidayService;

	@Autowired
    ShiftService shiftService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Employee employee, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, employee);
            return "employees/create";
        }
        uiModel.asMap().clear();
        employeeService.saveEmployee(employee);
        return "redirect:/employees/" + encodeUrlPathSegment(employee.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Employee());
        return "employees/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("employee", employeeService.findEmployee(id));
        uiModel.addAttribute("itemId", id);
        return "employees/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("employees", employeeService.findEmployeeEntries(firstResult, sizeNo));
            float nrOfPages = (float) employeeService.countAllEmployees() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("employees", employeeService.findAllEmployees());
        }
        return "employees/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Employee employee, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, employee);
            return "employees/update";
        }
        uiModel.asMap().clear();
        employeeService.updateEmployee(employee);
        return "redirect:/employees/" + encodeUrlPathSegment(employee.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, employeeService.findEmployee(id));
        return "employees/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Employee employee = employeeService.findEmployee(id);
        employeeService.deleteEmployee(employee);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/employees";
    }

	void populateEditForm(Model uiModel, Employee employee) {
        uiModel.addAttribute("employee", employee);
        uiModel.addAttribute("personalholidays", personalHolidayService.findAllPersonalHolidays());
        uiModel.addAttribute("publicholidays", publicHolidayService.findAllPublicHolidays());
        uiModel.addAttribute("shifts", shiftService.findAllShifts());
        uiModel.addAttribute("grades", Arrays.asList(Grade.values()));
        uiModel.addAttribute("preferences", Arrays.asList(Preference.values()));
        uiModel.addAttribute("shiftpatterns", Arrays.asList(ShiftPattern.values()));
    }

	String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
}
