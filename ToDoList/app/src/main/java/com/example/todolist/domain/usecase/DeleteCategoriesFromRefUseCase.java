package com.example.todolist.domain.usecase;

import com.example.todolist.data.local.pref.PrefManager;

import javax.inject.Inject;

public class DeleteCategoriesFromRefUseCase {

    private PrefManager prefManager;
    @Inject
    public DeleteCategoriesFromRefUseCase(PrefManager prefManager){
        this.prefManager=prefManager;
    }
    public void execute(String item){
        prefManager.deleteCategoryItem(item);
    }
}
