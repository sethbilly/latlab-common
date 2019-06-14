/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.dateutils;


import com.latlab.common.constants.Month;
import com.latlab.common.model.NumberRange;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class DateChooser
{
    private int day;
    private Month month;
    private int year;
    
    private int maxYear;
    private int minYear;
    private int minDays = 1;
    private int maxDays = 31;
    
    private Date valueDate = null;
    
    private List<Integer> daysList = new LinkedList<>();
    private List<Month> monthsList = new LinkedList<>();
    private List<Integer> yearsList = new LinkedList<>();

    public DateChooser(int minYear, int maxYear)
    {
        this.maxYear = maxYear;
        this.minYear = minYear;
        init();
    }

    public DateChooser(Date defaultDate, int minYear, int maxYear)
    {
        this.valueDate = defaultDate;
        this.maxYear = maxYear;
        this.minYear = minYear;
        init();
    }
    
    public void reset()
    {
        valueDate = null;
        month = null;
        day = 0;
        year = 0;
    }
    
    public Date getDate()
    {
        System.out.println("forming date : day :" + day + " month : " + month + " year : " + year);
        try
        {
            if(month == null)
            {
                return null;
            }
            valueDate =  DateTimeUtils.parseDate(day + "/" + month.getIndex() + "/" + year, "dd/MM/yyyy");
        } catch (Exception e)
        {
        }
        
        
        return valueDate;
    }
    
    public void init()
    {
        if(minYear == 0)
        {
            minYear = DateTimeUtils.getCurrentYear();
        }
        if(maxYear == 0)
        {
            minYear = DateTimeUtils.getCurrentYear() + 20;
        }
        
        daysList = NumberRange.generateRange(minDays, maxDays);
        yearsList = NumberRange.generateRange(minYear, maxYear);
        monthsList = Arrays.asList(Month.values());
        
        extract();
    }
    
    public void adjustDays()
    {
        
        Calendar calender = Calendar.getInstance();
        
        if(month != null)
        {
            try
            {
                calender.set(Calendar.MONTH, month.getIndex() -1);
            } catch (Exception e)
            {
            }
            
        }
        
        maxDays =  calender.getActualMaximum(Calendar.DAY_OF_MONTH);
        
        daysList = NumberRange.generateRange(maxDays, maxDays);
        
    }
    
    public void setDate(Date date)
    {
        valueDate = date;
        extract();
    }
    
    private void extract()
    {
        if(valueDate == null)
        {
            return;
        }
        
        day = DateTimeUtils.getDayInDate(valueDate);
        int monthIndex = DateTimeUtils.getMonthInDate(valueDate);
        month = Month.getMonth(monthIndex);
        year = DateTimeUtils.getYearInDate(valueDate);
        
        adjustDays();
    }

    public int getDay()
    {
        return day;
    }

    public void setDay(int day)
    {
        this.day = day;
    }

    public Month getMonth()
    {
        return month;
    }

    public void setMonth(Month month)
    {
        this.month = month;
    }

    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    public int getMaxYear()
    {
        return maxYear;
    }

    public void setMaxYear(int maxYear)
    {
        this.maxYear = maxYear;
    }

    public int getMinYear()
    {
        return minYear;
    }

    public void setMinYear(int minYear)
    {
        this.minYear = minYear;
    }

    public Date getValueDate()
    {
        return valueDate;
    }

    public void setValueDate(Date valueDate)
    {
        this.valueDate = valueDate;
    }

    public List<Integer> getDaysList()
    {
        return daysList;
    }

    public void setDaysList(List<Integer> daysList)
    {
        this.daysList = daysList;
    }

    public List<Month> getMonthsList()
    {
        return monthsList;
    }

    public void setMonthsList(List<Month> monthsList)
    {
        this.monthsList = monthsList;
    }

    public List<Integer> getYearsList()
    {
        return yearsList;
    }

    public void setYearsList(List<Integer> yearsList)
    {
        this.yearsList = yearsList;
    }

    public int getMinDays()
    {
        return minDays;
    }

    public void setMinDays(int minDays)
    {
        this.minDays = minDays;
    }

    public int getMaxDays()
    {
        return maxDays;
    }

    public void setMaxDays(int maxDays)
    {
        this.maxDays = maxDays;
    }
    
    
    
}
