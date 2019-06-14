package com.latlab.common.dateutils;


import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
//import org.joda.time.Period;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class PeriodUtils {

    /**
     * Same as {@link #getQuarterDates(int) }, with the year assumed to be this
     * year.
     *
     * @return
     */
    public static Map<Period, DateRange> getCurrentQuarterDates() {
        return getQuarterDates(new DateTime().getYear());
    }

    /**
     * Same as {@link #getQuarterDates(int) }, with the year assumed to be last
     * year.
     *
     * @return
     */
    public static Map<Period, DateRange> getPreviousQuarterDates() {

        return getQuarterDates(new DateTime().getYear() - 1);
    }

    /**
     * Obtains a Map of {@link YearQuarterDate} for all the quarters of the
     * specified year. If <code>year</code> value is 0 or less, it is assumed
     * that the year is the current year.
     *
     * @param year
     * @return
     */
    public static Map<Period, DateRange> getQuarterDates(int year) {
        Map<Period, DateRange> quarterMap = new HashMap<>();

        DateMidnight refDate = new DateMidnight();
        if (year > 0) {
            refDate = refDate.withYear(year);
        }

        refDate = refDate.withMonthOfYear(1).withDayOfMonth(1);
        Date startDate1 = refDate.toDate();
        refDate = refDate.plusMonths(2);
        refDate = refDate.withDayOfMonth(refDate.dayOfMonth().getMaximumValue());
        Date endDate1 = new DateTime(refDate.toDateTime()).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).toDate();

        DateRange quarterDate = new DateRange(Period.FIRST_QUARTER, year, startDate1, endDate1);
        quarterMap.put(quarterDate.getPeriod(), quarterDate);

        refDate = refDate.withMonthOfYear(4).withDayOfMonth(1);
        Date starteDate2 = refDate.toDate();
        refDate = refDate.plusMonths(2);
        refDate = refDate.withDayOfMonth(refDate.dayOfMonth().getMaximumValue());
        Date endDate2 = new DateTime(refDate.toDateTime()).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).toDate();
        DateRange quarterDate2 = new DateRange(Period.SECOND_QUARTER, year, starteDate2, endDate2);
        quarterMap.put(quarterDate2.getPeriod(), quarterDate2);

        refDate = refDate.withMonthOfYear(7).withDayOfMonth(1);
        Date starteDate3 = refDate.toDate();
        refDate = refDate.plusMonths(2);
        refDate = refDate.withDayOfMonth(refDate.dayOfMonth().getMaximumValue());
        Date endDate3 = new DateTime(refDate.toDateTime()).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).toDate();
        DateRange quarterDate3 = new DateRange(Period.THIRD_QUARTER, year, starteDate3, endDate3);
        quarterMap.put(quarterDate3.getPeriod(), quarterDate3);

        refDate = refDate.withMonthOfYear(10).withDayOfMonth(1);
        Date starteDate4 = refDate.toDate();
        refDate = refDate.plusMonths(2);
        refDate = refDate.withDayOfMonth(refDate.dayOfMonth().getMaximumValue());
        Date endDate4 = new DateTime(refDate.toDateTime()).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).toDate();
        DateRange quarterDate4 = new DateRange(Period.LAST_QUARTER, refDate.getYear(), starteDate4, endDate4);
        quarterMap.put(quarterDate4.getPeriod(), quarterDate4);
        return quarterMap;
    }

    /**
     * Uses the date supplied to determine the {@link YearQuarterDate} in which
     * the date fits.
     *
     * @param currentDate
     * @return
     */
    public static DateRange getQuarterDate(Date currentDate) {
        Map<Period, DateRange> quarterDates = getQuarterDates(new LocalDate(currentDate.getTime()).getYear());
        DateRange yqd = null;
        for (DateRange yearQuarterDate : quarterDates.values()) {
            if (yearQuarterDate.isWithinRange(currentDate)) {
                yqd = yearQuarterDate;
                break;
            }
        }

        return yqd;
    }

    /**
     * Takes the date supplied and computes the previous {@link YearQuarterDate}
     *
     * @param currentDate
     * @return
     */
    public static DateRange getPreviousQuarter(Date currentDate) {
        Date date = new DateTime(currentDate).minusMonths(3).toDate();
        return getQuarterDate(date);
//        DateRange quarterDate = getQuarterDate(currentDate);
//        
//        if(quarterDate == null)
//        {
//            throw new IllegalStateException("Unable to compute quarter from supplied date. Something is wrong with "+PeriodUtils.class.getName());
//        }
//        
//        int ordinal = quarterDate.getRangeOption().ordinal();
//        Period previousQuaryer = Period.getPreviousFromOrdinal(ordinal);
//        //if previous was 3 then it means the year has reduced, so reduce year.
//        if(previousQuaryer.ordinal()==3){
//            int year = new LocalDate(currentDate.getTime()).plusYears(-1).getYear();
//            return getQuarter(year,previousQuaryer);
//        }else{
//            int year = new LocalDate(currentDate.getTime()).getYear();
//            return getQuarter(year,previousQuaryer);
//        }        
    }

    /**
     * Takes the date supplied, and computes the next {@link YearQuarterDate}
     * for it
     *
     * @param currentDate
     * @return
     */
    public static DateRange getNextQuarter(Date currentDate) {
        DateRange quarterDate = getQuarterDate(currentDate);
        if (quarterDate == null) {
            throw new IllegalStateException("Unable to compute quarter from supplied date. Something is wrong with " + PeriodUtils.class.getName());
        }

        int ordinal = quarterDate.getPeriod().ordinal();
        Period nextQuarter = Period.getNextFromOrdinal(ordinal);
        //if next was 0 then it means the year has increased, so increase year.
        if (nextQuarter.ordinal() == 0) {
            int year = new LocalDate(currentDate.getTime()).plusYears(1).getYear();
            return getQuarter(year, nextQuarter);
        } else {
            int year = new LocalDate(currentDate.getTime()).getYear();
            return getQuarter(year, nextQuarter);
        }
    }

    /**
     * Obtains the quarter {@link YearQuarterDate} for the supplied year
     *
     * @param year
     * @param quarter
     * @return
     */
    public static DateRange getQuarter(int year, Period quarter) {
        Map<Period, DateRange> quarterDates = getQuarterDates(year);
        return quarterDates.get(quarter);
    }

    /**
     * Obtains the 1st quarter {@link YearQuarterDate} for the supplied year
     *
     * @param year
     * @return
     */
    public static DateRange getFirstQuarter(int year) {
        return getQuarter(year, Period.FIRST_QUARTER);
    }

    /**
     * Obtains the 2nd quarter {@link YearQuarterDate} for the supplied year
     *
     * @param year
     * @return
     */
    public static DateRange getSecondQuarter(int year) {
        return getQuarter(year, Period.SECOND_QUARTER);
    }

    /**
     * Obtains the 3rd quarter {@link YearQuarterDate} for the supplied year
     *
     * @param year
     * @return
     */
    public static DateRange getThirdQuarter(int year) {
        return getQuarter(year, Period.THIRD_QUARTER);
    }

    /**
     * Obtains the 4th quarter {@link YearQuarterDate} for the supplied year
     *
     * @param year
     * @return
     */
    public static DateRange getFourthQuarter(int year) {
        return getQuarter(year, Period.LAST_QUARTER);
    }

    public static LinkedList<DateRange> getQuarterDatesFrom(Date currentDate) {
        LinkedList<DateRange> quarters = new LinkedList<>();
        DateRange first = PeriodUtils.getQuarterDate(currentDate);
        first.setYear(new DateTime(first.getFromDate()).getYear());
        first.setFromDate(currentDate);
        quarters.add(first);

        DateRange second = PeriodUtils.getNextQuarter(first.getToDate());
        second.setYear(new DateTime(second.getFromDate()).getYear());
        quarters.add(second);

        DateRange third = PeriodUtils.getNextQuarter(second.getToDate());
        third.setYear(new DateTime(third.getFromDate()).getYear());
        quarters.add(third);

        DateRange last = PeriodUtils.getNextQuarter(third.getToDate());
        last.setYear(new DateTime(last.getFromDate()).getYear());
        quarters.add(last);

        return quarters;
    }

    /**
     * Uses the supplied date to compute all the 4 quarters of a year from the
     * date supplied, and sets the "year" field appropriately.
     *
     * @param currentDate
     * @return
     */
    public static LinkedList<Period> getQuartersFrom(Date currentDate) {

        if (currentDate == null) {
            throw new IllegalArgumentException("Cannot determine quarters from null date");
        }
        int year = new DateTime(currentDate).getYear();
        Period first = PeriodUtils.getQuarterDate(currentDate).getPeriod();
        first.setYear(year);
        LinkedList<Period> list = new LinkedList();
        list.add(first);
        Period second = Period.getNextFromOrdinal(first.ordinal());
        if (second.ordinal() < first.ordinal()) {
            year++;
            second.setYear(year);
        } else {
            second.setYear(year);
        }
        list.add(second);
        Period third = Period.getNextFromOrdinal(second.ordinal());
        if (third.ordinal() < second.ordinal()) {
            year++;
            third.setYear(year);
        } else {
            third.setYear(year);
        }
        list.add(third);
        Period fourth = Period.getNextFromOrdinal(third.ordinal());
        if (fourth.ordinal() < third.ordinal()) {
            year++;
            fourth.setYear(year);
        } else {
            fourth.setYear(year);
        }
        list.add(fourth);
        return list;
    }

    public static void main(String[] args) {
        System.out.println(getQuarterDates(2014));

    }
}
