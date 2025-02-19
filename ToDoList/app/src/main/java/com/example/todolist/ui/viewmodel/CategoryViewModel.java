package com.example.todolist.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.todolist.domain.usecase.AddCategoriesToRefUseCase;
import com.example.todolist.domain.usecase.DeleteCategoriesFromRefUseCase;
import com.example.todolist.domain.usecase.GetCategoriesFromRefUseCase;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CategoryViewModel extends ViewModel {

    private final GetCategoriesFromRefUseCase getCategoriesFromRefUseCase;
    private final AddCategoriesToRefUseCase addCategoriesToRefUseCase;
    private final DeleteCategoriesFromRefUseCase deleteCategoriesFromRefUseCase;
    private final MutableLiveData<List<String>> categoryItems = new MutableLiveData<>();
    private final MutableLiveData<String> categoryName = new MutableLiveData<>();

    @Inject
    public CategoryViewModel(
            GetCategoriesFromRefUseCase getCategoriesFromRefUseCase,
            AddCategoriesToRefUseCase addCategoriesToRefUseCase,
            DeleteCategoriesFromRefUseCase deleteCategoriesFromRefUseCase
    ) {
        this.getCategoriesFromRefUseCase = getCategoriesFromRefUseCase;
        this.addCategoriesToRefUseCase = addCategoriesToRefUseCase;
        this.deleteCategoriesFromRefUseCase = deleteCategoriesFromRefUseCase;
        updateCategoryItems();

    }
    public void updateCategoryItems(){
        categoryItems.setValue(getCategoriesFromRefUseCase.execute());
    }
    public LiveData<List<String>> getCategoryItems(){
        return categoryItems;
    }
    public LiveData<String> getCategoryName(){
        return categoryName;
    }
    public void setCategoryName(String name){
        categoryName.setValue(name);
    }
    public void setCategoryItems(List<String> items){
        categoryItems.setValue(items);
    }
    public void addCategoryItem(String item){
        addCategoriesToRefUseCase.execute(item);
        updateCategoryItems();
    }
    public void deleteCategoryItem(String item){
        deleteCategoriesFromRefUseCase.execute(item);
        updateCategoryItems();
    }


}
