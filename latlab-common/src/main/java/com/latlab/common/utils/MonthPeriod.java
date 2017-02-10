/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.utils;

import com.latlab.common.constants.Month;
import com.latlab.common.model.DateRange;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class MonthPeriod implements Serializable {

    private DateRange datePeriod;
    private Month month;
    private int year;

    public MonthPeriod() {

    }

    public static void main(String[] args) {
        String datestr = 201002 + "";
        System.out.println(DateTimeUtils.parseDate(datestr, "yyyyMM"));
    }

    public MonthPeriod(Integer monthCode) {
//        String datestr  = monthCode+"";
        Date date = DateTimeUtils.parseDate(monthCode + "", "yyyyMM");

        this.month = Month.getMonth(date);
        this.year = DateTimeUtils.getYearInDate(date);
    }

    public MonthPeriod(Date date) {
        this.month = Month.getMonth(date);
        this.year = DateTimeUtils.getYearInDate(date);
    }

    public MonthPeriod(Month month, int year) {
        this.month = month;
        this.year = year;
    }

    public MonthPeriod(int year) {
        this.year = year;
    }

    public String getDescription() {
        return month + " " + year;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public DateRange getDatePeriod() {
        return datePeriod;
    }

    public void findDates() {
        Calendar lastCalendar = Calendar.getInstance();

        lastCalendar.set(Calendar.YEAR, year);
        lastCalendar.set(Calendar.MONTH, month.getIndex());
        lastCalendar.set(Calendar.DAY_OF_MONTH, 1);
        lastCalendar.add(Calendar.DATE, -1);
        Date lastDate = lastCalendar.getTime();

        Calendar beginCalendar = Calendar.getInstance();
        beginCalendar.set(Calendar.YEAR, year);
        beginCalendar.set(Calendar.MONTH, month.getIndex() - 1);
        beginCalendar.set(Calendar.DAY_OF_MONTH, 1);

        Date firstDate = beginCalendar.getTime();

        datePeriod = new DateRange(firstDate, lastDate);

    }

    @Override
    public String toString() {
        return month + " " + year + "(" + datePeriod + ")";
    }

    public Integer monthCode() {
        return Month.monthCode(datePeriod.getFromDate());
    }
}
