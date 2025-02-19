package com.example.todolist.domain.usecase;

import com.example.todolist.data.local.pref.PrefManager;

import java.util.List;

import javax.inject.Inject;

public class GetCategoriesFromRefUseCase {
    private PrefManager prefManager;
    @Inject
    public GetCategoriesFromRefUseCase(PrefManager prefManager){
        this.prefManager=prefManager;
    }
    public List<String> execute(){
        return prefManager.getCategoryItems();
    }
}
