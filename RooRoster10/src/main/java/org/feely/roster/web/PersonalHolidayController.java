package org.feely.roster.web;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.joda.time.format.DateTimeFormat;
import org.feely.roster.domain.PersonalHoliday;
import org.feely.roster.service.EmployeeService;
import org.feely.roster.service.PersonalHolidayService;
import org.feely.roster.service.WorkShiftService;
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

@RequestMapping("/personalholidays")
@Controller
public class PersonalHolidayController {

	@Autowired
    PersonalHolidayService personalHolidayService;

	@Autowired
    EmployeeService employeeService;

	@Autowired
    WorkShiftService workShiftService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid PersonalHoliday personalHoliday, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, personalHoliday);
            return "personalholidays/create";
        }
        uiModel.asMap().clear();
        personalHolidayService.savePersonalHoliday(personalHoliday);
        return "redirect:/personalholidays/" + encodeUrlPathSegment(personalHoliday.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new PersonalHoliday());
        return "personalholidays/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("personalholiday", personalHolidayService.findPersonalHoliday(id));
        uiModel.addAttribute("itemId", id);
        return "personalholidays/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("personalholidays", personalHolidayService.findPersonalHolidayEntries(firstResult, sizeNo));
            float nrOfPages = (float) personalHolidayService.countAllPersonalHolidays() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("personalholidays", personalHolidayService.findAllPersonalHolidays());
        }
        addDateTimeFormatPatterns(uiModel);
        return "personalholidays/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid PersonalHoliday personalHoliday, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, personalHoliday);
            return "personalholidays/update";
        }
        uiModel.asMap().clear();
        personalHolidayService.updatePersonalHoliday(personalHoliday);
        return "redirect:/personalholidays/" + encodeUrlPathSegment(personalHoliday.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, personalHolidayService.findPersonalHoliday(id));
        return "personalholidays/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        PersonalHoliday personalHoliday = personalHolidayService.findPersonalHoliday(id);
        personalHolidayService.deletePersonalHoliday(personalHoliday);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/personalholidays";
    }

	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("personalHoliday_startdate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("personalHoliday_enddate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }

	void populateEditForm(Model uiModel, PersonalHoliday personalHoliday) {
        uiModel.addAttribute("personalHoliday", personalHoliday);
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
