package com.example.viewpager2;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.viewpager2.ViewModel.Repository.LocalDataSource.DataRoom;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.Item;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.VideoStats.ItemList.ItemList;
import com.squareup.picasso.Picasso;

import java.util.List;


public class SharedViewModel extends ViewModel {

    private MutableLiveData<List<ItemList>> data = new MutableLiveData<>();
    private MutableLiveData<List<DataRoom>> dataRoom = new MutableLiveData<>();

    public void setData(List<ItemList> items){
        data.setValue(items);
    }

    public LiveData<List<ItemList>> getData(){
        return data;
    }

    public void setDataRoom(List<DataRoom> room){
        dataRoom.setValue(room);
    }

    public LiveData<List<DataRoom>> getDataRoom(){
        return dataRoom;
    }
}
