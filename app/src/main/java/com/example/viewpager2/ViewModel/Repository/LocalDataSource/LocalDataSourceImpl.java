package com.example.viewpager2.ViewModel.Repository.LocalDataSource;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

public class LocalDataSourceImpl implements LocalDataSource{

    SharedPreferences sPref;
    AppDatabase database;


    @Inject
    public LocalDataSourceImpl(SharedPreferences sharedPreferences, AppDatabase dataRoom){
        this.sPref = sharedPreferences;
        this.database = dataRoom;
    }

    @Override
    public void setDataRoom(List<DataRoom> items) {
        database.videosDao().insertAll(items);
    }

    @Override
    public List<DataRoom> getDataRoom() {
        return database.videosDao().getAll();
    }

    @Override
    public void deleteDataRoom() {
        database.videosDao().deleteAll();
    }

}

