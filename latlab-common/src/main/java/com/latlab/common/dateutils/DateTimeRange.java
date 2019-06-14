/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.dateutils;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.Interval;

/**
 * @author Edwin
 */
public class DateTimeRange
{

    private static Date beginDate;
    private static Date endDate;
    private static DateTime dtBegin;
    private static DateTime dtEnd;

    
    private static Calendar cal;
    static int yearInStartDate;
    static int yearInEndDate;
    static int weekInYear_ofStartDate;
    static int weekInYear_ofEndDate;

    private static void extractEssentials()
    {
        cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);


        cal.setTime(beginDate);
        yearInStartDate = cal.get(Calendar.YEAR);
        weekInYear_ofStartDate = cal.get(Calendar.WEEK_OF_YEAR);


        cal.setTime(beginDate);
        yearInEndDate = cal.get(Calendar.YEAR);
        weekInYear_ofEndDate = cal.get(Calendar.WEEK_OF_YEAR);
    }

    public static List<Interval> getWeeklyIgnorMonthBreaks(Date beginDate, Date endDate)
    {

        List<Interval> intervalsList = new LinkedList<Interval>();
        DateTimeRange.beginDate = beginDate;
        DateTimeRange.endDate = endDate;
        extractEssentials();

        dtBegin = new DateTime(beginDate);
        dtEnd = new DateTime(endDate);
        

        int dayOfStartDate = dtBegin.getDayOfWeek();

        int dayDifferenceToStartDateWeekend = 7 - dayOfStartDate;

        DateTime startDateWeekedDate = dtBegin.plusDays(dayDifferenceToStartDateWeekend);

        Interval initInterval = new Interval(dtBegin, startDateWeekedDate);

        intervalsList.add(initInterval);


        DateTime mov = new DateTime(startDateWeekedDate);

        while (dtEnd.isAfter(mov))
        {
            mov = startDateWeekedDate.plusDays(1);
             startDateWeekedDate = mov.plusDays(6);

            intervalsList.add(new Interval(mov, startDateWeekedDate));

        }

        for (Interval interval : intervalsList)
        {
            System.out.println(DateTimeUtils.formatDateFully(interval.getStart().toDate())
                    + " - " + DateTimeUtils.formatDateFully(interval.getEnd().toDate())
                    + " diff = " + interval.toDuration().toPeriod().getDays());
        }

        System.out.println(intervalsList.size());

        
        return intervalsList;

    }

    public static List<Interval> getMonthRanges(Date beginDate, Date endDate)
    {

       final List<Interval> intervalsList = new LinkedList<Interval>();
        DateTimeRange.beginDate = beginDate;
        DateTimeRange.endDate = endDate;
        extractEssentials();

        dtBegin = new DateTime(beginDate);
        dtEnd = new DateTime(endDate);


        int dayOfStartDate = dtBegin.getDayOfMonth();


        int maxMonthDays = dtBegin.dayOfMonth().getMaximumValue();
        
        int dayDifferenceToStartDateMonthEnd = maxMonthDays - dayOfStartDate;

        DateTime startDateMontEndDate = dtBegin.plusDays(dayDifferenceToStartDateMonthEnd);

        Interval initInterval = new Interval(dtBegin, startDateMontEndDate);

        intervalsList.add(initInterval);


        DateTime mov = new DateTime(startDateMontEndDate);

        while (dtEnd.isAfter(startDateMontEndDate))
        {
            mov = startDateMontEndDate.plusDays(1);
             startDateMontEndDate = mov.plusDays(mov.dayOfMonth().getMaximumValue() - 1);

            intervalsList.add(new Interval(mov, startDateMontEndDate));

        }

        for (Interval interval : intervalsList)
        {
            System.out.println(DateTimeUtils.formatDateFully(interval.getStart().toDate())
                    + " - " + DateTimeUtils.formatDateFully(interval.getEnd().toDate())
                    + " diff = " + interval.toDuration().toPeriod().getDays());
        }
        
        return intervalsList;

    }

    public static void main(String[] args)
    {
        Date firstDate = new Date();

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2012);

        Date secondDate = c.getTime();

        DateTimeRange dp = new DateTimeRange();

        DateTimeRange.getMonthRanges(firstDate, secondDate);

    }

    public Date getBeginDate()
    {
        return beginDate;
    }

    public void setBeginDate(Date beginDate)
    {
        this.beginDate = beginDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }
}
