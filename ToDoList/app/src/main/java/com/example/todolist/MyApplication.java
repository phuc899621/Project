package com.example.todolist;

import android.app.Application;

import javax.inject.Inject;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class MyApplication extends Application {

    @Inject
    public MyApplication(){

    }
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
