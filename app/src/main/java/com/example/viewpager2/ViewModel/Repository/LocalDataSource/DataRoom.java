package com.example.viewpager2.ViewModel.Repository.LocalDataSource;

import android.widget.ImageView;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.Id;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.Item;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.Snippet;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.Thumbnails;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.VideoStats.ItemList.ItemList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity (tableName = "data")
public class DataRoom {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "items")
    @TypeConverters(Converter.class)
    public List<ItemList> items;

    public List<ItemList> getItem() {
        return items;
    }

    public void setItem(List<ItemList> items) {
        this.items = items;
    }

    public DataRoom(List<ItemList> items){
        this.items = items;
    }
}
