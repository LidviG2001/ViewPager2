package com.example.viewpager2.ViewModel.Repository.LocalDataSource;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

@Database(entities = {DataRoom.class}, version = 6)
@TypeConverters({Converter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract DataRoomDao videosDao();
}
