package com.example.viewpager2.ViewModel.Repository;

import android.util.Log;

import com.example.viewpager2.ViewModel.Repository.LocalDataSource.DataRoom;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.Item;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.VideoDetails;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.VideoStats.ItemList.ItemList;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.VideoStats.ItemList.ListConverter;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.VideoStats.Items;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.VideoStats.Test;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.VideoStats.VideoStats;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.RemoteDataSource;
import com.example.viewpager2.ViewModel.Repository.LocalDataSource.LocalDataSource;
import com.example.viewpager2.ViewModel.RepositoryBridge;
import com.example.viewpager2.ViewModel.RepositoryBridgeItemList;
import com.example.viewpager2.ViewModel.RepositoryBridgeStats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepositoryImpl implements DataRepository{
    LocalDataSource lds;
    RemoteDataSource rds;

    @Inject
    public DataRepositoryImpl (LocalDataSource localDataSource, RemoteDataSource remoteDataSource){
        this.lds = localDataSource;
        this.rds = remoteDataSource;
    }

    @Override
    public void getVideoDetails(String part, String partS, String channelId, String key, String order, String type,
                                RepositoryBridge bridge, RepositoryBridgeStats bridgeStats, RepositoryBridgeItemList bridgeItemList){
        rds.call(part, channelId, key, order, type, new Callback<VideoDetails>() {
                    @Override
                    public void onResponse(Call<VideoDetails> call, Response<VideoDetails> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {

                                Log.d("TAG", "Response sucsessfull list of chsnnels" + response.body().getItems());
                                bridge.handleData(response.body().getItems());

                                deleteDataRoom();

                                String _id = null;
                                ArrayList<String> array = new ArrayList<>();

                                for(int i = 0; i < response.body().getItems().size(); i++){
                                    String request_id = String.valueOf(response.body().getItems().get(i).getId().getVideoId());
                                   _id = request_id + ",";
                                    array.add(_id);
                                }
                                _id = String.join("", array);
                                String id = _id.substring(0, _id.length()-1);

                                getVideoStats(response.body().getItems(), partS, key, id, bridgeStats, bridgeItemList);


                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<VideoDetails> call, Throwable t) {
                        Log.d("TAG", "API request is failed");
                    }
                }
        );
    }

    @Override
    public void getVideoStats(List<Item> item,String partS, String key, String videoId,
                              RepositoryBridgeStats bridgeStats,
                              RepositoryBridgeItemList bridgeItemList) {
        rds.callStats(partS, key, videoId, new Callback<VideoStats>() {
            @Override
            public void onResponse(Call<VideoStats> call, Response<VideoStats> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        Log.d("TAG", "API request for statics is succsess " + response.body().getItems());
                       bridgeStats.handleData(response.body().getItems());

                        Test test1 = new Test();
                        test1.setList(response.body().getItems());

                       setItemList(item ,response.body().getItems(), bridgeItemList);
                    }
                }
            }

            @Override
            public void onFailure(Call<VideoStats> call, Throwable t) {
                Log.d("TAG", "API request for statics is failed");
            }
        });
    }

    @Override
    public void setItemList(List<Item> item, List<Items> items, RepositoryBridgeItemList bridgeItemList) {

        List<ItemList> itemList = new ArrayList<>();

        for (int i = 0; i < item.size() && i < items.size(); i++) {

            ItemList itemlist = new ItemList();

            itemlist.setItem(item);
            itemlist.setItems(items);

            itemList.add(itemlist);
        }

        itemList.size();
        Log.d("TAG", "Test 12234 is " + itemList.size());

        ListConverter listConverter = new ListConverter();
        listConverter.setList(itemList);
        listConverter.getList();
        Log.d("TAG", "test 1234567 is " + listConverter.getList().size());

        bridgeItemList.handleData(listConverter.getList());
        for(int i = 0; i < listConverter.getList().size(); i++){
            DataRoom data = new DataRoom(listConverter.getList());
            setDataRoom(Collections.singletonList(data));
        }

    }


    @Override
    public void setDataRoom(List<DataRoom> list) {
        lds.setDataRoom(list);
    }

    @Override
    public List<DataRoom> getDataRoom() {
        return lds.getDataRoom();
    }

    @Override
    public void deleteDataRoom() {
        lds.deleteDataRoom();
    }
}
