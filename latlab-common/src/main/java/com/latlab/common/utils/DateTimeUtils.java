/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.utils;

import com.latlab.common.constants.Month;
import com.latlab.common.constants.Quarter;
import com.latlab.common.formating.NumberFormattingUtils;
import com.latlab.common.model.DateRange;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joda.time.Interval;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;


public class DateTimeUtils {

  private static Date toDay = new Date();
//    private static Calendar cal = Calendar.getInstance();
    private static SimpleDateFormat sdf = new SimpleDateFormat();
//    private static NumberFormat nf = NumberFormat.getInstance();

    public static String NOT_SET = "<NOT SET>";

    public static String YEAR = "yyyy";

    public static final String PATTERN_SHORT_DATE = "EEE, d MMM, yyyy";
    public static final String PATTERN_LONG_DATE = "EEEE, d MMMM, yyyy";
    public static final String PATTERN_COMPACT_DATE = "dd.MM.yy";
    public static final String PATTERN_SHORT_DATE_WITHOUT_YEAR = "EEE, d MMM";
    public static final String PATTERN_TIME = "h:mm a";
    public static final String PATTERN_SHORT_NO_DAY = "d MMM yyyy";
    public static final String PATTERN_NOS = "dd-MM-yyyy";
    public static final String PATTERN_DATE_AND_TIME = "EEE, d MMM, yyyy h:mm a";

    public static final String PATTERN_MONTH_YEAR_SHORT = "MMM, yyyy";

    public static final String PATTERN_MONTH_YEAR_LONG = "MMMM, yyyy";
    public static final String SIMPLE_PATTERN = "dd/MM/yyyy";
    public static final String TIME = "hh:mm:ss";
    public static final String SQL_PATTERN = "yyyy-MM-dd";

    public static String DAY = "d";
    private static String MONTH_PATTERN = "M";

    public static String getLastMonthName() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        return new SimpleDateFormat("MMM").format(cal.getTime());
    }
//    

    /**
     * @param date Date to format. The pattern to used is "EEE, d MMM, yyyy". eg
     * Mon, 3 Jun, 2009
     * @return formated date as String
     */
    public static String formatDateShort(Date date) {
        if (date == null) {
            return "";
        }

        sdf.applyPattern(PATTERN_SHORT_DATE);
        return sdf.format(date);
    }

    /**
     * @param date Date to format. The pattern to used is "EEEE, d MMMM, yyyy".
     * eg Monday, 3 June, 2009
     * @return formated date as String
     */
    public static String formatDateFully(Date date) {
        if (date == null) {
            return "";
        }
        sdf.applyPattern(PATTERN_LONG_DATE);
        return sdf.format(date);
    }

    /**
     * @param date Date to format. The pattern to used should be a valid
     * @param pattern eg "EEEE, d MMMM, yyyy".
     * @return formated date as String
     */
    public static String formatDate(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        //sdf.setLenient(false);
        sdf.applyPattern(pattern);
        sdf.getCalendar().setFirstDayOfWeek(Calendar.MONDAY);
        return sdf.format(date);
    }

    /**
     * @param date Date to format. The pattern to used is "h:mm a". eg Mon, 3
     * Jun, 2009
     * @return formated time as String
     */
    public static String formatTime(Date date) {
        if (date == null) {
            return "";
        }

        sdf.applyPattern(PATTERN_TIME);
        return sdf.format(date);
    }

    /**
     * @param date Date to format. The pattern to used is "MMM, yyyy". eg Jun,
     * 2009
     * @return formated time as String
     */
    public static String formatDate_monthYear(Date date) {
        if (date == null) {
            return "";
        }

        sdf.applyPattern(PATTERN_MONTH_YEAR_SHORT);
        return sdf.format(date);
    }

    /**
     * @param date Date to format. The pattern to used is "EEE, d MMM, yyyy h:mm
     * a". eg Mon, 3 Jun, 2009 6:34 pm
     * @return formated time as String
     */
    public static String formatForTimeAndDate(Date date) {
        if (date == null) {
            return "";
        }

        sdf.applyPattern(PATTERN_DATE_AND_TIME);
        return sdf.format(date);
    }

    /**
     * @return the current year and month formated as "yyyy/mm" eg 2009/07
     */
    public static String getYearMonth() {
        String yearMonth = "/";
        Date d = new Date();

        sdf.applyPattern(YEAR);

        yearMonth = sdf.format(d) + "/";

        sdf.applyPattern(MONTH_PATTERN);

        yearMonth += NumberFormattingUtils.formatNumber(sdf.format(d), 2);

        return yearMonth;
    }

    /**
     * @param date Date to parse. The pattern to used .
     *
     * @return formated time as String
     */
    public static Date parseDate(String dateString, String pattern) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            dateFormat.setLenient(false);
            return dateFormat.parse(dateString);
        } catch (Exception e) {

        }

        return null;
    }

    public static List<Date> getMonthEndingDates(int begin, int end) {
        List<Date> datesList = new LinkedList<>();
        Date today = new Date();

        for (int year = begin; year <= end; year++) {

            for (int month = 1; month <= 12; month++) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(today);

                // calendar.add(Calendar.MONTH, 1);
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.add(Calendar.DATE, -1);

                datesList.add(calendar.getTime());

            }
        }

        return datesList;
    }

    public static List<Date> getMonthEndingFromDate(Date baseDate, int numberOfMonths) {
        List<Date> datesList = new LinkedList<>();
//        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(baseDate);

        System.out.println("date : " + baseDate);
        for (int begin = 0; begin <= numberOfMonths; begin++) {
//d
//            for (int month = 1; month <= 12; month++)
//            {

            // calendar.add(Calendar.MONTH, 1);
//                calendar.set(Calendar.YEAR, year);
//                calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

            datesList.add(calendar.getTime());

            calendar.add(Calendar.MONTH, -1);

//            }
        }

        return datesList;
    }

    public static List<Date> getMonthEndingFromDate(Date fromDate, Date toDate) {
        List<Date> datesList = new LinkedList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fromDate);

        while (calendar.getTime().before(toDay)) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

            datesList.add(calendar.getTime());

//            System.out.println(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        return datesList;
    }

    public static List<Date> getMonthEndingFromDate(DateRange dateRange) {

        return getMonthEndingFromDate(dateRange.getFromDate(), dateRange.getToDate());
    }

    public static List<MonthPeriod> getMonthPeriodsByYear(int year) {
        List<MonthPeriod> monthPeriodsList = new LinkedList<>();

//        Date today = new Date();
        for (int month = 1; month <= 12; month++) {
            MonthPeriod monthPeriod = new MonthPeriod();
            monthPeriod.setYear(year);
            monthPeriod.setMonth(Month.getMonth(month));
            monthPeriod.findDates();

            monthPeriodsList.add(monthPeriod);

        }

        return monthPeriodsList;
    }

    public static List<MonthPeriod> getMonthPeriodsBetweenDates(Date startDate, Date endDate) {
        List<MonthPeriod> monthPeriodsList = new LinkedList<>();

        Calendar c = Calendar.getInstance();
        c.setTime(endDate);

//        System.out.println("testing : " + startDate + " : " + endDate);
        while (c.getTime().after(startDate)) {

            int month = c.get(Calendar.MONTH) + 1;
            int year = c.get(Calendar.YEAR);

            MonthPeriod monthPeriod = new MonthPeriod();
            monthPeriod.setYear(year);
            monthPeriod.setMonth(Month.getMonth(month));
            monthPeriod.findDates();

            monthPeriodsList.add(monthPeriod);

            c.add(Calendar.MONTH, -1);
        }

        return monthPeriodsList;
    }

    /**
     * @param date Date
     * @param numberOfWorkingDays
     * @param exclusionDates list of dates to exclude
     *
     * @return Date
     */
    public static Date getWorkingDaysFromDate(Date date, int numberOfWorkingDays, List<Date> exclusionDates) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(date);

        if (exclusionDates == null) {
            exclusionDates = Collections.EMPTY_LIST;
        }
//        System.out.println("holidays is  : " + exclusionDates);

        while (numberOfWorkingDays > 0) {
//            System.out.println("running : " + numberOfWorkingDays + "  " + date);
            startCal.add(Calendar.DAY_OF_MONTH, 1);

//                System.out.println(" now is " + startCal.getTime());
            if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
                    && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                if (exclusionDates.contains(startCal.getTime()) == false) {
                    numberOfWorkingDays--;
//                        System.out.println(" heree.....");
                }

            }
        }

        return startCal.getTime();

    }

//    public static Date getWorkingDaysFromDate2(Date date, int numberOfWorkingDays, List<Date> exclusionDates) {
//        boolean forward = true;
//
//        if (numberOfWorkingDays > 0) {
//            forward = true;
//        } else if (numberOfWorkingDays < 0) {
//            forward = false;
//        }
//
//        Calendar startCal = Calendar.getInstance();
//        startCal.setTime(date);
//
//        if (exclusionDates == null) {
//            exclusionDates = Collections.EMPTY_LIST;
//        }
////        System.out.println("holidays is  : " + exclusionDates);
//
//        while (Math.abs(numberOfWorkingDays) != 0) {
//
////            System.out.println("running : " + numberOfWorkingDays + "  " + date);
//            if (forward) {
//                startCal.add(Calendar.DAY_OF_MONTH, 1);
//            } else {
//                startCal.add(Calendar.DAY_OF_MONTH, -1);
//            }
//
////                System.out.println(" now is " + startCal.getTime());
//            if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
//                    && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
//                if (exclusionDates.contains(startCal.getTime()) == false) {
//                    if (forward) {
//                        numberOfWorkingDays--;
//                    } else {
//                        numberOfWorkingDays++;
//                    }
////                        System.out.println(" heree.....");
//                }
//
//            }
//        }
//
//        return startCal.getTime();
//
//    }

    /**
     * @param date Date to parse. The pattern to used .
     *
     * @return formated time as String
     */
    public static Date parseDate(String dateString, String pattern, boolean linent) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

            dateFormat.setLenient(linent);
            return dateFormat.parse(dateString);
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * @param date Date to parse. The pattern to used .
     *
     * @return formated time as String
     */
    public static Date increaseDate(Date date, int numberOfDays) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DAY_OF_MONTH, numberOfDays);
            return cal.getTime();
        } catch (Exception e) {

        }
        return null;
    }

    public static int getCurrentYear() {
        sdf.applyPattern(YEAR);
        return Integer.parseInt(sdf.format(new Date()));
    }

    public static int getYearInDate(Date date) {
        if (date == null) {
            return -1;
        }
        Calendar calender = Calendar.getInstance();
        calender.setTime(date);
        return calender.get(Calendar.YEAR);
    }

    public static Date getBeginOfYearDate(Date date) {
        if (date == null) {
            return null;
        }

        Calendar calender = Calendar.getInstance();
        calender.setTime(date);
        calender.set(Calendar.MONTH, 0);
        calender.set(Calendar.DAY_OF_MONTH, 1);
        return calender.getTime();
    }

    public static int getMonthInDate(Date date) {
        if (date == null) {
            return -1;
        }
        Calendar calender = Calendar.getInstance();
        calender.setTime(date);
        return calender.get(Calendar.MONTH) + 1;
    }

    public static int getDayInDate(Date date) {
        if (date == null) {
            return -1;
        }
        Calendar calender = Calendar.getInstance();
        calender.setTime(date);
        return calender.get(Calendar.DAY_OF_MONTH);
    }

    public static boolean isLeapYear(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        return cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;
    }

    public static boolean isLeapYear(Date date) {
        return isLeapYear(getYearInDate(date));
    }

    public static int getAverageMonthDifference(Date dtStart, Date dtEnd) {
        int difference = 0;

        long differenceInMilisecs = dtEnd.getTime() - dtStart.getTime();

        difference = (int) ((differenceInMilisecs / (60 * 60 * 24 * 1000)) / 29);

        return difference;
    }

    public static int getDayDifference(Date dtStart, Date dtEnd) {
        int difference = 0;

        long differenceInMilisecs = dtEnd.getTime() - dtStart.getTime();

        difference = (int) ((differenceInMilisecs / (60 * 60 * 24 * 1000)));

        return difference;
    }

    public static int getHourDifference(Date dtStart, Date dtEnd) {
        int difference = 0;
        long differenceInMilisecs = dtEnd.getTime() - dtStart.getTime();
        difference = (int) ((differenceInMilisecs / (60 * 60 * 1000)));
        return difference;
    }

    public static Date removeTimeFromDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.HOUR, 0);

        return cal.getTime();
    }

    public static boolean isWeekendDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

        if (dayOfWeek == Calendar.SUNDAY) {
            return true;
        }
        if (dayOfWeek == Calendar.SATURDAY) {
            return true;
        }

        return false;
    }

    public static Date getUpperTimeStamp(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 99);

        return cal.getTime();
    }

    public static Date getLowerTimeStamp(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    public static Date setToBeginingOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    public static Date setToEndOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    public static List<Date> allDatesBetweenRange(Date startDate, Date endDate) {
        startDate = removeTimeFromDate(startDate);
        endDate = removeTimeFromDate(endDate);

        List<Date> listOfDates = new ArrayList<>();

        listOfDates.add(startDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);

        while (cal.getTime().after(endDate) == false) {

            cal.add(Calendar.DAY_OF_MONTH, 1);
            listOfDates.add(cal.getTime());

//            System.out.println(formatDateFully(cal.getTime()));
        }

        return listOfDates;
    }

    public static String[] getMonthNames() {
        DateFormatSymbols symbols = new DateFormatSymbols(new Locale("en", "UK"));

        return symbols.getMonths();
    }

    public static String getCurrentMonth() {
        return getMonthLongName(new Date());
    }

    public static String getMonthLongName(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.UK);
    }

    public static Date subMonthFromDate(Date date, int numberOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -numberOfMonth);

        return cal.getTime();
    }

    public static String shortMonthFromDate(Date date) {
        if (date == null) {
            return "";
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        return cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.ENGLISH);
    }

    public static String formatDateRangeforReport(Date startDate, Date endDate) {
        String result = " Between " + formatDateFully(startDate) + " and " + formatDateFully(endDate);
        return result;
    }

    public static Date addDaysToDate(Date targetDate, int numberOfDays) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(targetDate);
        cal.add(Calendar.DAY_OF_MONTH, numberOfDays);

        return cal.getTime();
    }

    public static Date startMonthDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        return cal.getTime();
    }

    public static Date endMonthDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        cal.set(Calendar.DAY_OF_MONTH, maxDay);

        return cal.getTime();
    }

    public static List<Date> firstSundayOfMonth(int startYear, int endYear) {

        List<Date> datesList = new LinkedList<>();

        Date startDate = parseDate(startYear + "", "yyyy");
        Date endDate = parseDate(endYear + "", "yyyy");

        Date checkDate = startDate;

        Calendar cal = Calendar.getInstance();
        cal.setTime(checkDate);

        while (endDate.after(checkDate)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(checkDate);

            int firstDay = calendar.get(Calendar.DAY_OF_WEEK);

            if (firstDay != Calendar.SUNDAY) {
                calendar.add(Calendar.DAY_OF_MONTH, 8 - firstDay);
                datesList.add(calendar.getTime());
//                System.out.println("may be sunday is " + calendar.getTime());
            }

            cal.add(Calendar.MONTH, 1);
            checkDate = cal.getTime();

//            System.out.println(formatDateShort(checkDate)+ "  " + formatDateShort(endDate));
        }

        return datesList;

    }

    public static int getDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static boolean isEndOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return (calendar.get(Calendar.DAY_OF_MONTH) == numberOfDaysInMonth(date)) ? true : false;
    }

    public static boolean isEndOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        if (calendar.get(Calendar.MONTH) != Calendar.DECEMBER) {
            return false;
        }

        return isEndOfMonth(date);
    }

    public static int numberOfDaysInMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static int getLastDayBeforeEndOfMonth(Date date) {
        return numberOfDaysInMonth(date) - 1;
    }

    public static Date getLastDateOfMonth(Date date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

//        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        int numberOfDays = numberOfDaysInMonth(date);
        calendar.set(Calendar.DAY_OF_MONTH, numberOfDays);
//        String d = calendar.get(Calendar.MONTH) + 1 + "/" + lastDayOfMonth + "/" + calendar.get(Calendar.YEAR);
//        Date lastDateOfMonth = dateFormat.parse(d);
        return calendar.getTime();
    }

    public static Date previousMonth(Date date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        return calendar.getTime();

    }

    public static String datesToDurationExpression(Date d1, Date d2) {
        if (d1 == null) {
            return "";
        }

        if (d2 == null) {
            return "";
        }

        Interval interval = new Interval(d1.getTime(), d2.getTime());
        org.joda.time.Period period = interval.toPeriod();

        PeriodFormatter dhm = new PeriodFormatterBuilder()
                //                .appendMonths()
                //                .appendPrefix(" month", " months")
                //                .appendSeparator(", ")
                .appendDays()
                .appendSuffix(" day", " days")
                .appendSeparator(", ")
                .appendHours()
                .appendSuffix(" hour", " hours")
                .appendSeparator(", ")
                .appendMinutes()
                .appendSuffix(" minute", " minutes")
                .toFormatter();

        return period.toString(dhm);
    }

    public static String datesToYearsMonthDaysShortExp(Date d1, Date d2) {
        if (d1 == null) {
            return "";
        }

        if (d2 == null) {
            return "";
        }

        try
        {
            Interval interval = new Interval(d1.getTime(), d2.getTime());
        org.joda.time.Period period = interval.toPeriod();

        PeriodFormatter dhm = new PeriodFormatterBuilder()
                .appendYears()
                .appendSuffix("yr", "yrs")
                .appendSeparator(", ")
                .appendMonths()
                .appendSuffix("mth", "mths")
                .appendSeparator(", ")
                .appendWeeks()
                .appendSuffix("wk", "wks")
                .appendSeparator(", ")
                .appendDays()
                .appendSuffix("day", "days")
                .appendSeparator(", ")
                .toFormatter();

        return period.toString(dhm);
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        
        return "";
    }

    public static String durationToDaysHoursExpression(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            return "";
        }

        Interval interval = new Interval(d1.getTime(), d2.getTime());
        org.joda.time.Period period = interval.toPeriod();

        PeriodFormatter dhm = new PeriodFormatterBuilder()
                .appendDays()
                .appendSuffix(" day", " days")
                .appendSeparator(", ")
                .appendHours()
                .appendSuffix(" hour", " hours")
                .appendSeparator(", ")
                .toFormatter();

        return period.toString(dhm);
    }

    public static String minsToDurationExpression(int intervalInMinutes) {
        Date date1 = new Date();
        Date date2 = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date2);
        cal.add(Calendar.MINUTE, intervalInMinutes);

        return datesToDurationExpression(date1, cal.getTime());
    }

    public static int getAge(Date dateOfBirth) {

        int years = 0;
        try {
            if (null == dateOfBirth) {
                dateOfBirth = new Date();
            }
            Calendar todayCalender = new GregorianCalendar();
            todayCalender.setTime(new Date());
            Calendar dateOfBirthCalendar = Calendar.getInstance();
            dateOfBirthCalendar.setTime(dateOfBirth);

            years = todayCalender.get(Calendar.YEAR) - dateOfBirthCalendar.get(Calendar.YEAR);
            if ((dateOfBirthCalendar.get(Calendar.MONTH) > todayCalender.get(Calendar.MONTH))
                    || (dateOfBirthCalendar.get(Calendar.MONTH) == todayCalender.get(Calendar.MONTH) && dateOfBirthCalendar.get(Calendar.DAY_OF_MONTH) > todayCalender
                    .get(Calendar.DAY_OF_MONTH))) {
                years--;
            }

            return years;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;

    }
//    public static int getAge(Date dateOfBirth) {
//
//        int years = 0;
//        try {
//            if (null == dateOfBirth) {
//                dateOfBirth = new Date();
//            }
//            Calendar todayCalender = Calendar.getInstance();
//            todayCalender.setTime(new Date());
//            Calendar dateOfBirthCalendar = Calendar.getInstance();
//            dateOfBirthCalendar.setTime(dateOfBirth);
//            years = todayCalender.get(Calendar.YEAR) - dateOfBirthCalendar.get(Calendar.YEAR);
//            
//            
//            
//            
//            return years;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return years;
//
//    }

    public static Date getFirstDateOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));

        return calendar.getTime();
    }

    public static List<Date> getDateBetweenDates(Date startDate, Date endDate) {
        List<Date> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        while (calendar.getTime().before(endDate)) {
            Date result = calendar.getTime();
            dates.add(result);
            calendar.add(Calendar.DATE, 1);
        }

        return dates;
    }

    public static void main(String[] args) {

        try {
           
        } catch (Exception ex) {
            Logger.getLogger(DateTimeUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Agyepong Dateutils">
    public static long msPerDay = 86400000L;
    public static double msPerDayD = 86400000.0D;
    public static final String dateSeparator = "/";
    public static final String jdbcDateSeparator = "-";
    public static final String timeSeparator = ":";
    public static final String dateTimeSeparator = " ";
    private static DateFormat dateformater = DateFormat.getDateInstance(3);

    public static final java.util.Date getDate(int year, int month, int day) {
        Calendar cal = new GregorianCalendar(year, intToCalendarMonth(month), day);

        return cal.getTime();
    }

    public static Date getQuarterLastMonthDate(Quarter tradeQuarter, int tradeYear) {

        Date date = new Date();
        switch (tradeQuarter) {
            case FIRST_QUARTER:
                date = DateTimeUtils.getDate(tradeYear, 03, 31);
                break;
            case SECOND_QUARTER:
                date = DateTimeUtils.getDate(tradeYear, 06, 31);
                break;
            case THIRD_QUARTER:
                date = DateTimeUtils.getDate(tradeYear, 9, 31);
                break;
            case FOURTH_QUARTER:
                date = DateTimeUtils.getDate(tradeYear, 12, 31);
                break;
            default:
                break;
        }

        String dateString = DateTimeUtils.formatDate(date, DateTimeUtils.SIMPLE_PATTERN);
        return DateTimeUtils.parseDate(dateString, DateTimeUtils.SIMPLE_PATTERN);

    }

    public static final java.sql.Date addDays(int diffdays) {
        Calendar cal = new GregorianCalendar();
        cal.add(5, diffdays);

        return new java.sql.Date(cal.getTimeInMillis());
    }

//    public static int dayDiff(java.util.Date first, java.util.Date second) {
//        if ((first == null) || (second == null)) {
//            if ((first == null) && (second == null)) {
//                return 0;
//            }
//            if (first == null) {
//                return -1;
//            }
//            return 1;
//        }
//
//        long diff = (first.getTime() - second.getTime()) / msPerDay;
//
//        Long convertLong = new Long(diff);
//
//        return convertLong.intValue();
//    }
//
//    public static int dayDiff(java.sql.Date first, java.sql.Date second) {
//        if ((first == null) || (second == null)) {
//            if ((first == null) && (second == null)) {
//                return 0;
//            }
//            if (first == null) {
//                return -1;
//            }
//            return 1;
//        }
//
//        long diff = (first.getTime() - second.getTime()) / msPerDay;
//
//        Long convertLong = new Long(diff);
//        return convertLong.intValue();
//    }

    public static boolean equals(java.util.Date first, java.util.Date second) {
        if ((first == null) || (second == null)) {
            return (first == null) && (second == null);
        }

        return getDayDifference(first, second) == 0;
    }

    

    public static int getMonth(java.util.Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        int calendarMonth = cal.get(Calendar.MONTH);
        return calendarMonthToInt(calendarMonth);
    }

    public static int getDay(java.util.Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal.get(5);
    }

    public static int getHour(java.util.Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal.get(11);
    }

    public static int getMinute(java.util.Date date) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal.get(12);
    }

    private static int calendarMonthToInt(int calendarMonth) {
        if (calendarMonth == 0) {
            return 1;
        }
        if (calendarMonth == 1) {
            return 2;
        }
        if (calendarMonth == 2) {
            return 3;
        }
        if (calendarMonth == 3) {
            return 4;
        }
        if (calendarMonth == 4) {
            return 5;
        }
        if (calendarMonth == 5) {
            return 6;
        }
        if (calendarMonth == 6) {
            return 7;
        }
        if (calendarMonth == 7) {
            return 8;
        }
        if (calendarMonth == 8) {
            return 9;
        }
        if (calendarMonth == 9) {
            return 10;
        }
        if (calendarMonth == 10) {
            return 11;
        }
        if (calendarMonth == 11) {
            return 12;
        }
        return 1;
    }

    private static int intToCalendarMonth(int month) {
        if (month == 1) {
            return 0;
        }
        if (month == 2) {
            return 1;
        }
        if (month == 3) {
            return 2;
        }
        if (month == 4) {
            return 3;
        }
        if (month == 5) {
            return 4;
        }
        if (month == 6) {
            return 5;
        }
        if (month == 7) {
            return 6;
        }
        if (month == 8) {
            return 7;
        }
        if (month == 9) {
            return 8;
        }
        if (month == 10) {
            return 9;
        }
        if (month == 11) {
            return 10;
        }
        if (month == 12) {
            return 11;
        }
        return 0;
    }

    // </editor-fold>
}
