package org.feely.roster.web;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.feely.roster.domain.Department;
import org.feely.roster.reference.Dept;
import org.feely.roster.reference.Grade;
import org.feely.roster.service.DepartmentService;
import org.feely.roster.service.WorkShiftService;
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

@RequestMapping("/departments")
@Controller
public class DepartmentController {

	@Autowired
    DepartmentService departmentService;

	@Autowired
    WorkShiftService workShiftService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Department department, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, department);
            return "departments/create";
        }
        uiModel.asMap().clear();
        departmentService.saveDepartment(department);
        return "redirect:/departments/" + encodeUrlPathSegment(department.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Department());
        return "departments/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("department", departmentService.findDepartment(id));
        uiModel.addAttribute("itemId", id);
        return "departments/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("departments", departmentService.findDepartmentEntries(firstResult, sizeNo));
            float nrOfPages = (float) departmentService.countAllDepartments() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("departments", departmentService.findAllDepartments());
        }
        return "departments/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Department department, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, department);
            return "departments/update";
        }
        uiModel.asMap().clear();
        departmentService.updateDepartment(department);
        return "redirect:/departments/" + encodeUrlPathSegment(department.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, departmentService.findDepartment(id));
        return "departments/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Department department = departmentService.findDepartment(id);
        departmentService.deleteDepartment(department);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/departments";
    }

	void populateEditForm(Model uiModel, Department department) {
        uiModel.addAttribute("department", department);
        uiModel.addAttribute("workshifts", workShiftService.findAllWorkShifts());
        uiModel.addAttribute("depts", Arrays.asList(Dept.values()));
        uiModel.addAttribute("grades", Arrays.asList(Grade.values()));
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
