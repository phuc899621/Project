package com.example.todolist.data.local.pref;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PrefManager {
    private static final String PREF_NAME = "TODOLIST_PREF";
    private static final String CATEGORY_KEY = "CATEGORY_ITEMS";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson;
    private static final Type typeList=new TypeToken<List<String>>(){}.getType();
    private static final List<String> defaultCategoryItems= Arrays.asList(
            "Work","Personal","Study","Shopping","Event"
            );
    @Inject
    public PrefManager(Context context){
        sharedPreferences=context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        gson=new Gson();
    }
    //check item empty
    private boolean isItemEmpty(String ITEM_KEY){
        if (sharedPreferences.getString(ITEM_KEY,null)==null){
            return true;
        }
        return false;
    }
    //get category items
    public List<String> getCategoryItems(){
        if(isItemEmpty(CATEGORY_KEY)){
            String defaultItem=gson.toJson(defaultCategoryItems);
            editor.putString(CATEGORY_KEY,defaultItem);
            editor.apply();
            return defaultCategoryItems;
        }
        return gson.fromJson(sharedPreferences.getString(CATEGORY_KEY,null),typeList);
    }
    //add category item
    public void addCategoryItem(String item){
        List<String> items=gson.fromJson(sharedPreferences.getString(CATEGORY_KEY,""),typeList);
        items.removeIf(String::isEmpty);
        if (!items.contains(item)&&!item.isEmpty()) {
            items.add(item);
            setItems(CATEGORY_KEY, items); // Lưu danh sách vào SharedPreferences
        }
        setItems(CATEGORY_KEY,items);
    }
    public void deleteCategoryItem(String item){
        List<String> items=gson.fromJson(sharedPreferences.getString(CATEGORY_KEY,""),typeList);
        items.removeIf(itemList->itemList.contains(item));
        setItems(CATEGORY_KEY,items);
    }
    public void setCategoryItems(List<String> items){
        setItems(CATEGORY_KEY,items);
    }
    //set item
    public void setItems(String ITEM_KEY, String data){
        editor.putString(ITEM_KEY,gson.toJson(data));
        editor.apply();
    }
    public void setItems(String ITEM_KEY, List<String> data){
        editor.putString(ITEM_KEY,gson.toJson(data));
        editor.apply();
    }
}
