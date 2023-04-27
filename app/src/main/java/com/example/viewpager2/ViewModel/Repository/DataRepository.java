package com.example.viewpager2.ViewModel.Repository;

import com.example.viewpager2.ViewModel.Repository.LocalDataSource.DataRoom;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.Item;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.VideoStats.Items;
import com.example.viewpager2.ViewModel.RepositoryBridge;
import com.example.viewpager2.ViewModel.RepositoryBridgeItemList;
import com.example.viewpager2.ViewModel.RepositoryBridgeStats;

import java.util.List;

public interface DataRepository {

    public void getVideoDetails(String part, String partS, String channelId, String key, String order, String type,
                                RepositoryBridge bridge, RepositoryBridgeStats bridgeStats, RepositoryBridgeItemList bridgeItemList);

    public void getVideoStats(List<Item> item,String partS, String key, String videoId,
                              RepositoryBridgeStats bridgeStats, RepositoryBridgeItemList bridgeItemList);

    void setDataRoom(List<DataRoom> list);
    List<DataRoom> getDataRoom();
    void deleteDataRoom();

    public void setItemList(List<Item> item, List<Items> items, RepositoryBridgeItemList bridgeItemList);
}