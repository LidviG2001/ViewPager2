package com.example.viewpager2.ViewModel.Repository.RemodeDataSource;

import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.Item;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.VideoDetails;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.VideoStats.ItemList.ItemList;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.VideoStats.ItemList.ListConverter;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.VideoStats.Items;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.VideoStats.VideoStats;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Callback;

public class RemoteRetrofitDataSourceImpl implements RemoteRetrofitDataSource{


    MainService mainService;

    @Inject
    public RemoteRetrofitDataSourceImpl (MainService mainService){
        this.mainService = mainService;
    }

    @Override
    public void call(String part, String channelId, String key, String order, String type,Callback<VideoDetails> callback) {
        mainService.getVideo(part, channelId, key, order, type).enqueue(callback);

    }

    @Override
    public void callStats(String partS, String key, String videoId, Callback<VideoStats> callback) {
        mainService.getVideoStats(partS, key, videoId).enqueue(callback);
    }
}
