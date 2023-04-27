package com.example.viewpager2.ViewModel.Repository.RemodeDataSource;

import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.Item;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.VideoDetails;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.VideoStats.ItemList.ItemList;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.VideoStats.ItemList.ListConverter;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.VideoStats.Items;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.VideoStats.VideoStats;

import java.util.List;

import retrofit2.Callback;

public interface RemoteRetrofitDataSource {
    public void call(String part, String channelId, String key, String order, String type, Callback<VideoDetails> callback);

    public void callStats(String partS, String key, String videoId, Callback<VideoStats> callback);
}
