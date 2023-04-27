package com.example.viewpager2.ViewModel;

import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.VideoStats.Items;

import java.util.List;

public interface RepositoryBridgeStats {
    public void handleData(List<Items> items);
}
