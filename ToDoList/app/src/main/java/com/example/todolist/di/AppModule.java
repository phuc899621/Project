package com.example.todolist.di;

import android.app.Application;
import android.content.Context;

import com.example.todolist.MyApplication;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {
    @Provides
    public static Context getContext(Application application){
        return application.getApplicationContext();
    }
}
