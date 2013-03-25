package org.feely.roster.web;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.feely.roster.domain.WorkShift;
import org.feely.roster.reference.ShiftType;
import org.feely.roster.service.DepartmentService;
import org.feely.roster.service.PersonalHolidayService;
import org.feely.roster.service.PublicHolidayService;
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

@RequestMapping("/workshifts")
@Controller
public class WorkShiftController {

	@Autowired
    WorkShiftService workShiftService;

	@Autowired
    DepartmentService departmentService;

	@Autowired
    PersonalHolidayService personalHolidayService;

	@Autowired
    PublicHolidayService publicHolidayService;

	@Autowired
    ShiftService shiftService;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid WorkShift workShift, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, workShift);
            return "workshifts/create";
        }
        uiModel.asMap().clear();
        workShiftService.saveWorkShift(workShift);
        return "redirect:/workshifts/" + encodeUrlPathSegment(workShift.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new WorkShift());
        return "workshifts/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("workshift", workShiftService.findWorkShift(id));
        uiModel.addAttribute("itemId", id);
        return "workshifts/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("workshifts", workShiftService.findWorkShiftEntries(firstResult, sizeNo));
            float nrOfPages = (float) workShiftService.countAllWorkShifts() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("workshifts", workShiftService.findAllWorkShifts());
        }
        addDateTimeFormatPatterns(uiModel);
        return "workshifts/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid WorkShift workShift, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, workShift);
            return "workshifts/update";
        }
        uiModel.asMap().clear();
        workShiftService.updateWorkShift(workShift);
        return "redirect:/workshifts/" + encodeUrlPathSegment(workShift.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, workShiftService.findWorkShift(id));
        return "workshifts/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        WorkShift workShift = workShiftService.findWorkShift(id);
        workShiftService.deleteWorkShift(workShift);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/workshifts";
    }

	void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("workShift_shiftdate_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }

	void populateEditForm(Model uiModel, WorkShift workShift) {
        uiModel.addAttribute("workShift", workShift);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("departments", departmentService.findAllDepartments());
        uiModel.addAttribute("personalholidays", personalHolidayService.findAllPersonalHolidays());
        uiModel.addAttribute("publicholidays", publicHolidayService.findAllPublicHolidays());
        uiModel.addAttribute("shifts", shiftService.findAllShifts());
        uiModel.addAttribute("shifttypes", Arrays.asList(ShiftType.values()));
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
