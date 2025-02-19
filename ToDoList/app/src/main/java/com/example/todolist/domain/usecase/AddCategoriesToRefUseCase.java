package com.example.todolist.domain.usecase;

import com.example.todolist.data.local.pref.PrefManager;

import javax.inject.Inject;

public class AddCategoriesToRefUseCase {
    private PrefManager prefManager;

    @Inject
    public AddCategoriesToRefUseCase(PrefManager prefManager){
        this.prefManager=prefManager;
    }
    public void execute(String item){
        prefManager.addCategoryItem(item);
    }

}
