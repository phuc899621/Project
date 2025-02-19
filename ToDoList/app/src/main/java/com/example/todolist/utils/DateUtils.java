package com.example.todolist.utils;

import com.example.todolist.domain.model.DateModel;
import com.example.todolist.domain.model.TimeModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateUtils {
    public static final int DATE_FORMAT_KEY =1;
    public static final int TIME_FORMAT_KEY =2;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH);
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);

    public static Calendar getCalendar(DateModel dateModel){
        Calendar calendar = Calendar.getInstance();
        calendar.set(dateModel.getYear(), dateModel.getMonth() - 1, dateModel.getDay());
        return calendar;

    }
    public static Calendar getCalendar(int year,int month,int day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        return calendar;

    }
    public static Calendar getCalendar(String time,int KEY){
        Calendar calendar = Calendar.getInstance();
        try {
            if(KEY== DATE_FORMAT_KEY){
                calendar.setTime(DateUtils.dateFormat.parse(time));
            }else{
                calendar.setTime(DateUtils.timeFormat.parse(time));
            }
            return calendar;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return calendar;

    }
    public static Calendar getCalendar(TimeModel timeModel){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,timeModel.getHour());
        calendar.set(Calendar.MINUTE,timeModel.getMinute());
        return calendar;
    }
    public static Calendar getCalendar(int hourOfDay,int minute){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
        calendar.set(Calendar.MINUTE,minute);
        return calendar;
    }
    public static DateModel getDateModel(Calendar calendar){
        return new DateModel(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH)+1,
                calendar.get(Calendar.DAY_OF_MONTH)
        );
    }
    public static TimeModel getTimeModel(Calendar calendar){
        return new TimeModel(
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE)
        );
    }
    public static Calendar getTodayCalendar(){
        Calendar calendar = Calendar.getInstance();
        return calendar;
    }
    public static Calendar getTomorrowCalendar(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar;
    }
    public static Calendar getNextWeekCalendar(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        return calendar;
    }
}
