package com.example.viewpager2.ViewModel.Repository.LocalDataSource;

import java.util.List;

public interface LocalDataSource {


    void setDataRoom(List<DataRoom> list);
    List<DataRoom> getDataRoom();
    void deleteDataRoom();

}