package com.example.viewpager2.ViewModel.Repository.RemodeDataSource;

import com.example.viewpager2.ViewModel.Repository.DataRepository;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.Item;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.VideoDetails;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.VideoStats.ItemList.ListConverter;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.VideoStats.Items;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.VideoStats.VideoStats;

import org.json.JSONObject;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Callback;

public class RemoteDataSourceImpl implements RemoteDataSource {

    RemoteRetrofitDataSource remoteRetrofitDataSource;

    @Inject
    public RemoteDataSourceImpl (RemoteRetrofitDataSource remoteRetrofitDataSource){
        this.remoteRetrofitDataSource = remoteRetrofitDataSource;
    }

    @Override
    public void call(String part, String channelId, String key, String order, String type, Callback<VideoDetails> callback) {
        remoteRetrofitDataSource.call(part, channelId, key, order, type, callback);
    }

    @Override
    public void callStats(String partS, String key, String videoId, Callback<VideoStats> callback) {
        remoteRetrofitDataSource.callStats(partS, key, videoId, callback);
    }
}
