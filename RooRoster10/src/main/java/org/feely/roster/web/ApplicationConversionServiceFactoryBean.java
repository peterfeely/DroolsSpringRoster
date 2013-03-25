package org.feely.roster.web;

import org.feely.roster.domain.Department;
import org.feely.roster.domain.Employee;
import org.feely.roster.domain.PersonalHoliday;
import org.feely.roster.domain.PublicHoliday;
import org.feely.roster.domain.Shift;
import org.feely.roster.domain.WorkShift;
import org.feely.roster.service.DepartmentService;
import org.feely.roster.service.EmployeeService;
import org.feely.roster.service.PersonalHolidayService;
import org.feely.roster.service.PublicHolidayService;
import org.feely.roster.service.ShiftService;
import org.feely.roster.service.WorkShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.stereotype.Service;

@Configurable
/**
 * A central place to register application converters and formatters. 
 */
@Service
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
	}

	@Autowired
    DepartmentService departmentService;

	@Autowired
    EmployeeService employeeService;

	@Autowired
    PersonalHolidayService personalHolidayService;

	@Autowired
    PublicHolidayService publicHolidayService;

	@Autowired
    ShiftService shiftService;

	@Autowired
    WorkShiftService workShiftService;

	public Converter<Department, String> getDepartmentToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.feely.roster.domain.Department, java.lang.String>() {
            public String convert(Department department) {
                return new StringBuilder().append(department.getMaxEarlyEmps()).append(' ').append(department.getMaxStandardEmps()).append(' ').append(department.getMaxLateEmps()).append(' ').append(department.getMinEarlyEmps()).toString();
            }
        };
    }

	public Converter<Long, Department> getIdToDepartmentConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, org.feely.roster.domain.Department>() {
            public org.feely.roster.domain.Department convert(java.lang.Long id) {
                return departmentService.findDepartment(id);
            }
        };
    }

	public Converter<String, Department> getStringToDepartmentConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.feely.roster.domain.Department>() {
            public org.feely.roster.domain.Department convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Department.class);
            }
        };
    }

	public Converter<Employee, String> getEmployeeToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.feely.roster.domain.Employee, java.lang.String>() {
            public String convert(Employee employee) {
                return new StringBuilder().append(employee.getFirstName()).append(' ').append(employee.getLastName()).append(' ').append(employee.getEmail()).append(' ').append(employee.getUsername()).toString();
            }
        };
    }

	public Converter<Long, Employee> getIdToEmployeeConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, org.feely.roster.domain.Employee>() {
            public org.feely.roster.domain.Employee convert(java.lang.Long id) {
                return employeeService.findEmployee(id);
            }
        };
    }

	public Converter<String, Employee> getStringToEmployeeConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.feely.roster.domain.Employee>() {
            public org.feely.roster.domain.Employee convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Employee.class);
            }
        };
    }

	public Converter<PersonalHoliday, String> getPersonalHolidayToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.feely.roster.domain.PersonalHoliday, java.lang.String>() {
            public String convert(PersonalHoliday personalHoliday) {
                return new StringBuilder().append(personalHoliday.getStartDate()).append(' ').append(personalHoliday.getEndDate()).toString();
            }
        };
    }

	public Converter<Long, PersonalHoliday> getIdToPersonalHolidayConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, org.feely.roster.domain.PersonalHoliday>() {
            public org.feely.roster.domain.PersonalHoliday convert(java.lang.Long id) {
                return personalHolidayService.findPersonalHoliday(id);
            }
        };
    }

	public Converter<String, PersonalHoliday> getStringToPersonalHolidayConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.feely.roster.domain.PersonalHoliday>() {
            public org.feely.roster.domain.PersonalHoliday convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), PersonalHoliday.class);
            }
        };
    }

	public Converter<PublicHoliday, String> getPublicHolidayToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.feely.roster.domain.PublicHoliday, java.lang.String>() {
            public String convert(PublicHoliday publicHoliday) {
                return new StringBuilder().append(publicHoliday.getStartDate()).append(' ').append(publicHoliday.getEndDate()).append(' ').append(publicHoliday.getHolidayPeriod()).toString();
            }
        };
    }

	public Converter<Long, PublicHoliday> getIdToPublicHolidayConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, org.feely.roster.domain.PublicHoliday>() {
            public org.feely.roster.domain.PublicHoliday convert(java.lang.Long id) {
                return publicHolidayService.findPublicHoliday(id);
            }
        };
    }

	public Converter<String, PublicHoliday> getStringToPublicHolidayConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.feely.roster.domain.PublicHoliday>() {
            public org.feely.roster.domain.PublicHoliday convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), PublicHoliday.class);
            }
        };
    }

	public Converter<Shift, String> getShiftToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.feely.roster.domain.Shift, java.lang.String>() {
            public String convert(Shift shift) {
                return new StringBuilder().append(shift.getShiftDate()).toString();
            }
        };
    }

	public Converter<Long, Shift> getIdToShiftConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, org.feely.roster.domain.Shift>() {
            public org.feely.roster.domain.Shift convert(java.lang.Long id) {
                return shiftService.findShift(id);
            }
        };
    }

	public Converter<String, Shift> getStringToShiftConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.feely.roster.domain.Shift>() {
            public org.feely.roster.domain.Shift convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Shift.class);
            }
        };
    }

	public Converter<WorkShift, String> getWorkShiftToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.feely.roster.domain.WorkShift, java.lang.String>() {
            public String convert(WorkShift workShift) {
                return new StringBuilder().append(workShift.getId()).toString();
            }
        };
    }

	public Converter<Long, WorkShift> getIdToWorkShiftConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, org.feely.roster.domain.WorkShift>() {
            public org.feely.roster.domain.WorkShift convert(java.lang.Long id) {
                return workShiftService.findWorkShift(id);
            }
        };
    }

	public Converter<String, WorkShift> getStringToWorkShiftConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, org.feely.roster.domain.WorkShift>() {
            public org.feely.roster.domain.WorkShift convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), WorkShift.class);
            }
        };
    }

	public void installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getDepartmentToStringConverter());
        registry.addConverter(getIdToDepartmentConverter());
        registry.addConverter(getStringToDepartmentConverter());
        registry.addConverter(getEmployeeToStringConverter());
        registry.addConverter(getIdToEmployeeConverter());
        registry.addConverter(getStringToEmployeeConverter());
        registry.addConverter(getPersonalHolidayToStringConverter());
        registry.addConverter(getIdToPersonalHolidayConverter());
        registry.addConverter(getStringToPersonalHolidayConverter());
        registry.addConverter(getPublicHolidayToStringConverter());
        registry.addConverter(getIdToPublicHolidayConverter());
        registry.addConverter(getStringToPublicHolidayConverter());
        registry.addConverter(getShiftToStringConverter());
        registry.addConverter(getIdToShiftConverter());
        registry.addConverter(getStringToShiftConverter());
        registry.addConverter(getWorkShiftToStringConverter());
        registry.addConverter(getIdToWorkShiftConverter());
        registry.addConverter(getStringToWorkShiftConverter());
    }

	public void afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
}
