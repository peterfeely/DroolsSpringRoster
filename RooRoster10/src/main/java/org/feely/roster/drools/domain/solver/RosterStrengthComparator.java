package org.feely.roster.drools.domain.solver;

import java.io.Serializable;
import java.util.Comparator;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.feely.roster.domain.WorkShift;

public class RosterStrengthComparator implements Comparator<WorkShift>, Serializable {

    public int compare(WorkShift a, WorkShift b) {
        return new CompareToBuilder()
                .append(a.getShiftType(), b.getShiftType())
                .append(a.getDepartment().getMaxStandardEmps(),b.getDepartment().getMaxStandardEmps())
                .toComparison();
        
    }

}
