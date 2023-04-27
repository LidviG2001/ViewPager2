package com.example.viewpager2.ViewModel.Repository.RemodeDataSource;

import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.Item;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.VideoDetails;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.VideoStats.ItemList.ItemList;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.VideoStats.ItemList.ListConverter;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.VideoStats.Items;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.VideoStats.VideoStats;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MainService {

    @GET("search")
    Call<VideoDetails> getVideo(
            @Query("part") String part,
            @Query("q") String query,
            @Query("key") String key,                 // API key
            @Query("order") String order,
            @Query("type") String type
    );

    @GET("videos")
    Call<VideoStats> getVideoStats(
            @Query("part") String partS,
            @Query("key") String key,
            @Query("id") String id
    );
}

