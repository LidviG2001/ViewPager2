package com.example.viewpager2.ViewModel.Repository.LocalDataSource;

import androidx.room.TypeConverter;

import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.Item;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.VideoStats.ItemList.ItemList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Converter {
    @TypeConverter
    public String fromItem(List<ItemList> items) {
        if (items == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<ItemList>>() {}.getType();
        String json = gson.toJson(items, type);
        return json;
    }

    @TypeConverter
    public List<ItemList> toItem(String itemString) {
        if (itemString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<ItemList>>() {}.getType();
        List<ItemList> itemList = gson.fromJson(itemString, type);
        return itemList;
    }
}
