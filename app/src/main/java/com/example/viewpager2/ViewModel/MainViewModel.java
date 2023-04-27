package com.example.viewpager2.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.viewpager2.ViewModel.Repository.DataRepository;
import com.example.viewpager2.ViewModel.Repository.LocalDataSource.DataRoom;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.Item;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.VideoStats.ItemList.ItemList;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.VideoStats.Items;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {

    DataRepository dataRepository;

    private MutableLiveData<String> _showToast = new MutableLiveData<String>();
    public LiveData<String> showToast = _showToast;

    private MutableLiveData<List<Item>> _channels = new MutableLiveData();
    public LiveData<List<Item>> channels = _channels;

    private MutableLiveData<List<Items>> _stats = new MutableLiveData();
    public LiveData<List<Items>> stats = _stats;

    private MutableLiveData<List<DataRoom>> _channels1 = new MutableLiveData();
    public LiveData<List<DataRoom>> channels1 = _channels1;

    private MutableLiveData<List<ItemList>> _list = new MutableLiveData<>();
    public LiveData<List<ItemList>> list = _list;

    @Inject
    public MainViewModel (DataRepository dataRepository){
        this.dataRepository = dataRepository;
    }

    public void getVideoDetails(String part, String partS, String channelId, String key, String order, String type){
        //arrayList.add();
        dataRepository.getVideoDetails(part, partS, channelId, key, order, type,
                new RepositoryBridge() {
                    @Override
                    public void handleData(List<Item> items) {
                        _channels.postValue(items);
                    }
                },
                new RepositoryBridgeStats() {
                    @Override
                    public void handleData(List<Items> items) {
                        _stats.postValue(items);
                    }
                },
                new RepositoryBridgeItemList() {
                    @Override
                    public void handleData(List<ItemList> itemList) {
                        _list.postValue(itemList);
                    }
                });
    }

    public void getVideoDetailsStats(List<Item> item, String partS, String key, String videoId){
        dataRepository.getVideoStats(item, partS, key, videoId, new RepositoryBridgeStats() {
            @Override
            public void handleData(List<Items> items) {
                _stats.postValue(items);
            }
        },
                new RepositoryBridgeItemList() {
                    @Override
                    public void handleData(List<ItemList> itemList) {
                        _list.postValue(itemList);
                    }
                });
    }

    public void getVideoItemList(List<Item> item, List<Items> items){
        dataRepository.setItemList(item, items, new RepositoryBridgeItemList() {
            @Override
            public void handleData(List<ItemList> itemList) {
                _list.postValue(itemList);
            }
        });
    }

    public void setDataRoom(List<DataRoom> listRoom){
        dataRepository.setDataRoom(listRoom);
    }

    public List<DataRoom> getDataRoom(){
        return dataRepository.getDataRoom();
    }

}

