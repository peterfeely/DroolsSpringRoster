package org.feely.roster.web;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.feely.roster.domain.Shift;
import org.feely.roster.service.EmployeeService;
import org.feely.roster.service.ShiftService;
import org.feely.roster.service.WorkShiftService;

import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

@RequestMapping("/shifts")
@Controller
public class ShiftController {

	@Autowired
    ShiftService shiftService;

	@Autowired
    EmployeeService employeeService;

	@Autowired
    WorkShiftService workShiftService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Shift shift, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, shift);
            return "shifts/create";
        }
        uiModel.asMap().clear();
        shiftService.saveShift(shift);
        return "redirect:/shifts/" + encodeUrlPathSegment(shift.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Shift());
        return "shifts/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("shift", shiftService.findShift(id));
        uiModel.addAttribute("itemId", id);
        return "shifts/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("shifts", shiftService.findShiftEntries(firstResult, sizeNo));
            float nrOfPages = (float) shiftService.countAllShifts() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("shifts", shiftService.findAllShifts());
        }
        addDateTimeFormatPatterns(uiModel);
        return "shifts/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Shift shift, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, shift);
            return "shifts/update";
        }
        uiModel.asMap().clear();
        shiftService.updateShift(shift);
        return "redirect:/shifts/" + encodeUrlPathSegment(shift.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, shiftService.findShift(id));
        return "shifts/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Shift shift = shiftService.findShift(id);
        shiftService.deleteShift(shift);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/shifts";
    }

	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("shift_shiftdate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }

	void populateEditForm(Model uiModel, Shift shift) {
        uiModel.addAttribute("shift", shift);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("employees", employeeService.findAllEmployees());
        uiModel.addAttribute("workshifts", workShiftService.findAllWorkShifts());
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
