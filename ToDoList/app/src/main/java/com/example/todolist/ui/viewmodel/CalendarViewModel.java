package com.example.todolist.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.todolist.domain.model.DateModel;
import com.example.todolist.domain.model.TimeModel;
import com.example.todolist.domain.usecase.ConvertToCalendarUseCase;
import com.example.todolist.domain.usecase.DateFormattingUseCase;
import com.example.todolist.domain.usecase.GetCalendarUseCase;
import com.example.todolist.domain.usecase.GetDateModelUseCase;
import com.example.todolist.domain.usecase.TimeFormattingUseCase;

import java.util.Calendar;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CalendarViewModel extends ViewModel {
    private final MutableLiveData<String> todayDate = new MutableLiveData<>();
    private final MutableLiveData<String> tomorrowDate = new MutableLiveData<>();
    private final MutableLiveData<String> nextWeekDate = new MutableLiveData<>();
    private final MutableLiveData<String> customDate = new MutableLiveData<>();
    private final MutableLiveData<String> chosenTimeOption = new MutableLiveData<>();

    private final DateFormattingUseCase dateFormattingUseCase;
    private final TimeFormattingUseCase timeFormattingUseCase;
    private final GetDateModelUseCase getDateModelUseCase;
    private final ConvertToCalendarUseCase convertToCalendarUseCase;

    private final GetCalendarUseCase getCalendarUseCase;

    @Inject
    public CalendarViewModel(
            DateFormattingUseCase dateFormattingUseCase,
            GetCalendarUseCase getCalendarUseCase,
            GetDateModelUseCase getDateModelUseCase,
            TimeFormattingUseCase timeFormattingUseCase,
            ConvertToCalendarUseCase convertToCalendarUseCase
    ){
        this.dateFormattingUseCase=dateFormattingUseCase;
        this.getCalendarUseCase=getCalendarUseCase;
        this.getDateModelUseCase=getDateModelUseCase;
        this.timeFormattingUseCase=timeFormattingUseCase;
        this.convertToCalendarUseCase=convertToCalendarUseCase;

        setTodayDate(dateFormattingUseCase.getDateFormatting(getCalendarUseCase.getTodayCalendar()));
        setTomorrowDate(dateFormattingUseCase.getDateFormatting(getCalendarUseCase.getTomorrowCalendar()));
        setNextWeekDate(dateFormattingUseCase.getDateFormatting(getCalendarUseCase.getNextWeekCalendar()));
        setCustomDate(dateFormattingUseCase.getDateFormatting(getCalendarUseCase.getTodayCalendar()));
        setChosenTimeOption("None");
    }
    public LiveData<String> getTodayDate() {
        return todayDate;
    }
    public LiveData<String> getTomorrowDate() {
        return tomorrowDate;
    }
    public LiveData<String> getNextWeekDate() {
        return nextWeekDate;
    }
    public LiveData<String> getCustomDate() {
        return customDate;
    }
    public LiveData<String> getChosenTimeOption() {
        return chosenTimeOption;
    }
    public void setChosenTimeOption(String option) {
        chosenTimeOption.setValue(option);
    }

    public void setTodayDate(String date){
        todayDate.setValue(date);
    }
    public void setTomorrowDate(String date) {
        tomorrowDate.setValue(date);
    }
    public void setNextWeekDate(String date) {
        nextWeekDate.setValue(date);
    }
    public void setCustomDate(String date) {
        customDate.setValue(date);
    }
    //-------------------get calendar-------------------
    public Calendar getCalendar(DateModel dateModel){
        return convertToCalendarUseCase.fromDateModel(dateModel);

    }
    public Calendar getCalendar(int year,int month,int day){
        return convertToCalendarUseCase.fromDate(year,month,day);

    }
    public Calendar getCalendar(String time,int KEY){
        return convertToCalendarUseCase.fromString(time,KEY);

    }
    public Calendar getCalendar(TimeModel timeModel){
        return convertToCalendarUseCase.fromTimeModel(timeModel);
    }
    public Calendar getCalendar(int hourOfDay,int minute){
        return convertToCalendarUseCase.fromTime(hourOfDay,minute);
    }
    public DateModel getDateModel(Calendar calendar){
        return getDateModelUseCase.getDateModel(calendar);
    }
    public TimeModel getTimeModel(Calendar calendar){
        return getDateModelUseCase.getTimeModel(calendar);
    }
    public Calendar getTodayCalendar(){
        return getCalendarUseCase.getTodayCalendar();
    }
    public String getDateFormatting(int year,int month,int day){
        return dateFormattingUseCase.getDateFormatting(day,month,year);
    }
    public String getTimeFormatting(int hourOfDay,int minute){
        return timeFormattingUseCase.getTimeFormatting(hourOfDay,minute);
    }

}
