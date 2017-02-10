/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.model;

import com.latlab.common.constants.Quarter;
import com.latlab.common.model.DateRange;
import java.util.Objects;

/**
 *
 * @author ic securities
 */
public class YearQuarter 
{
    private int year;
    private Quarter quarter;
    private DateRange dateRange;

    public YearQuarter(int year, Quarter quarter)
    {
        this.year = year;
        this.quarter = quarter;
        dateRange = DateRange.getQuarterRange(quarter.getNominal(), year);
        
    }
    public YearQuarter()
    {
//        this.year = year;
//        this.quarter = quarter;
//        dateRange = DateRange.getQuarterRange(quarter.getNominal(), year);
        
    }
    
    
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Quarter getQuarter() {
        return quarter;
    }

    public void setQuarter(Quarter quarter) {
        this.quarter = quarter;
    }

    public DateRange getDateRange()
    {
        return dateRange;
    }

    public void setDateRange(DateRange dateRange)
    {
        this.dateRange = dateRange;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.year;
        hash = 23 * hash + Objects.hashCode(this.quarter);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final YearQuarter other = (YearQuarter) obj;
        if (this.year != other.year) {
            return false;
        }
        if (this.quarter != other.quarter) {
            return false;
        }
        return true;
    }
    
//    
//    public static void main(String[] args)
//    {
//        System.out.println(new YearQuarter(2013, Quarter.FIRST_QUARTER).getDateRange());
//    }
    
}
