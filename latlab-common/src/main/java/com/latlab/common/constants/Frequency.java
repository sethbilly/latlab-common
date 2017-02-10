/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.latlab.common.constants;

import java.util.Calendar;

/**
 *
 * @author Edwin
 */
public enum Frequency {

    DAILY(Calendar.DAY_OF_MONTH, 1, 1, "Daily"),
    WEEKLY(Calendar.WEEK_OF_MONTH, 1, 7, "Weekly"),
    FORTHNIGHT (Calendar.WEEK_OF_MONTH, 1, 14, "Forthnight"),
    MONTHLY(Calendar.MONTH, 1, 31, "Monthly"),
    QUARTERLY(Calendar.MONTH, 3, 124, "Quarterly"),
    YEARLY (Calendar.YEAR, 1, 365, "Yearly");

    private int calenderField;
    private int unitIncrement;
    private int dayIntervals;
    private String label;

    private Frequency(int calenderField, int unitIncrement,int dayIntervals, String label)
    {
        this.calenderField = calenderField;
        this.unitIncrement = unitIncrement;
        this.dayIntervals = dayIntervals;
        this.label = label;
    }

    public int getCalenderField()
    {
        return calenderField;
    }

    public int getUnitIncrement()
    {
        return unitIncrement;
    }
    
    public int getDayIntervals()
    {
        return dayIntervals;
    }

    public void setDayIntervals(int dayIntervals)
    {
        this.dayIntervals = dayIntervals;
    }

    @Override
    public String toString() 
    {
        return label;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }
    
    
}
