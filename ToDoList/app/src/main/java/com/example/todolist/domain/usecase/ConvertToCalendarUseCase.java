package com.example.todolist.domain.usecase;

import com.example.todolist.domain.model.DateModel;
import com.example.todolist.domain.model.TimeModel;
import com.example.todolist.utils.DateUtils;

import java.util.Calendar;

import javax.inject.Inject;

public class ConvertToCalendarUseCase {
    @Inject
    public ConvertToCalendarUseCase(){

    }
    public Calendar fromDateModel(DateModel dateModel){
        return DateUtils.getCalendar(dateModel);

    }
    public Calendar fromDate(int year,int month,int day){
        return DateUtils.getCalendar(year,month,day);

    }
    public Calendar fromString(String time,int KEY){
        return DateUtils.getCalendar(time,KEY);

    }
    public Calendar fromTimeModel(TimeModel timeModel){
        return DateUtils.getCalendar(timeModel);
    }
    public Calendar fromTime(int hourOfDay,int minute){
        return DateUtils.getCalendar(hourOfDay,minute);
    }
}
