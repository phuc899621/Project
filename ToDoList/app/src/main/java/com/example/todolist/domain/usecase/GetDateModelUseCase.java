package com.example.todolist.domain.usecase;

import com.example.todolist.domain.model.DateModel;
import com.example.todolist.domain.model.TimeModel;
import com.example.todolist.utils.DateUtils;

import java.util.Calendar;

import javax.inject.Inject;

public class GetDateModelUseCase {
    @Inject
    public GetDateModelUseCase(){

    }
    public DateModel getDateModel(Calendar calendar){
        return DateUtils.getDateModel(calendar);
    }
    public TimeModel getTimeModel(Calendar calendar){
        return DateUtils.getTimeModel(calendar);
    }
}
