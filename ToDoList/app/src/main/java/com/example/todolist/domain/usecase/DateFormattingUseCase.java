package com.example.todolist.domain.usecase;

import static com.example.todolist.utils.DateUtils.getCalendar;

import com.example.todolist.domain.model.DateModel;
import com.example.todolist.domain.model.TimeModel;
import com.example.todolist.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;

public class DateFormattingUseCase {

    private final SimpleDateFormat dayFormat;
    
    @Inject
    public DateFormattingUseCase(){
        Calendar calendar = Calendar.getInstance();
        dayFormat = new SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH);
    }
    public String getDateFormatting(Calendar calendar){
        return dayFormat.format(calendar.getTime()).toUpperCase();
    }

    public String getDateFormatting(
            int day, int month, int year
    ){
        return dayFormat.format(getCalendar(year,month,day).getTime()).toUpperCase();
    }
    public String getDateFormatting(DateModel dateModel){
        return dayFormat.format(getCalendar(dateModel).getTime()).toUpperCase();
    }


}
