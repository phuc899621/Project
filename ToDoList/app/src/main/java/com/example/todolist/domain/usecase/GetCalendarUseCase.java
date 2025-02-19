package com.example.todolist.domain.usecase;

import com.example.todolist.domain.model.DateModel;
import com.example.todolist.domain.model.TimeModel;
import com.example.todolist.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;

public class GetCalendarUseCase {
    @Inject
    public GetCalendarUseCase(){

    }
    public Calendar getTodayCalendar(){
        return DateUtils.getTodayCalendar();
    }
    public Calendar getTomorrowCalendar(){
        return DateUtils.getTomorrowCalendar();
    }
    public Calendar getNextWeekCalendar() {
        return DateUtils.getNextWeekCalendar();
    }
}
