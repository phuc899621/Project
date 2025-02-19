package com.example.todolist.domain.usecase;

import android.util.Pair;

import com.example.todolist.utils.ValidateHelper;

import java.util.List;

import javax.inject.Inject;

public class ValidateTaskInfoUseCase {
    private ValidateHelper validateHelper;

    @Inject
    public ValidateTaskInfoUseCase(ValidateHelper validateHelper){
        this.validateHelper=validateHelper;
    }
    public Pair<Boolean,String> execute(String title, String description, String priority, List<String> categories){
        return new Pair<>(true,"hi");
    }
}
