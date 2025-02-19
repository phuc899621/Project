package com.example.todolist.domain.usecase;

import static com.example.todolist.utils.DateUtils.getCalendar;

import com.example.todolist.domain.model.TimeModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;

public class TimeFormattingUseCase {
    private final SimpleDateFormat timeFormat;

    @Inject
    public TimeFormattingUseCase(){
        timeFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
    }
    public String getTimeFormatting(Calendar calendar){
        return timeFormat.format(calendar.getTime()).toUpperCase();
    }
    public String getCurrentTime(){
        Calendar calendar=Calendar.getInstance();
        return timeFormat.format(calendar.getTime()).toUpperCase();
    }
    public String getTimeFormatting(int hourOfDay, int minute){
        return timeFormat.format(getCalendar(hourOfDay,minute).getTime()).toUpperCase();
    }
    public String getTimeFormatting(TimeModel timeModel){
        return timeFormat.format(getCalendar(timeModel).getTime()).toUpperCase();
    }

}
