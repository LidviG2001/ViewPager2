package com.example.viewpager2.ViewModel;

import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.Item;

import java.util.List;

public interface RepositoryBridge {
    public void handleData(List<Item> items);
}
