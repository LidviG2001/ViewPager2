package com.example.viewpager2.ViewModel.Repository.LocalDataSource;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DataRoomDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<DataRoom> items);


    @Query("SELECT * FROM data")
    List<DataRoom> getAll();

    @Query("DELETE FROM data")
    void deleteAll();
}

