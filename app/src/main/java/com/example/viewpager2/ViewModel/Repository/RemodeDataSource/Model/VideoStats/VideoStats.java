package com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.VideoStats;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoStats {

    @SerializedName("items")
    @Expose
    private List<Items> items;

    public List<Items> getItems(){
        return items;
    }

    public void setItems(List<Items> items){
        this.items = items;
    }
}
