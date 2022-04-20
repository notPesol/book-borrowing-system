package com.pesol.spring.util;

import java.util.Date;

public class DateRangeValidate {
    
    private Date startDate;
    private Date endDate;

    public DateRangeValidate(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isWithinRange(Date testDate) {
        return testDate.getTime() >= startDate.getTime() &&
                testDate.getTime() <= endDate.getTime();
    }


}
