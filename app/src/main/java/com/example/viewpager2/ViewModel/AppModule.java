package com.example.viewpager2.ViewModel;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.room.Room;

import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.MainService;
import com.example.viewpager2.ViewModel.Repository.DataRepository;
import com.example.viewpager2.ViewModel.Repository.DataRepositoryImpl;
import com.example.viewpager2.ViewModel.Repository.LocalDataSource.AppDatabase;
import com.example.viewpager2.ViewModel.Repository.LocalDataSource.LocalDataSource;
import com.example.viewpager2.ViewModel.Repository.LocalDataSource.LocalDataSourceImpl;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.RemoteDataSource;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.RemoteDataSourceImpl;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.RemoteRetrofitDataSource;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.RemoteRetrofitDataSourceImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {


    @Provides
    public static Gson provideGson(){
        Gson gson = new GsonBuilder().create();
        return gson;
    }

    @Provides
    public static OkHttpClient provideOkHttpClient(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        return client;
    }
//https://jsonplaceholder.typicode.com
    //https://www.googleapis.com/youtube/v3/
    @Provides
    public static Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/youtube/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    public static MainService provideMainService(Retrofit retrofit){
        return retrofit.create(MainService.class);
    }

    @Provides
    @Singleton
    public DataRepository provideDataRepository(LocalDataSource localData, RemoteDataSource remoteDataSource){
        return new DataRepositoryImpl(localData, remoteDataSource);
    }

    @Provides
    @Singleton
    public SharedPreferences getSharedPreferences(@ApplicationContext Context appContext){
        return appContext.getSharedPreferences("Cryptocurrency",MODE_PRIVATE);
    }


    @Provides
    public RemoteRetrofitDataSource createRemoteRetrofitDataSource(MainService mainService){
        return new RemoteRetrofitDataSourceImpl(mainService);
    }

    @Provides
    public RemoteDataSource createRemoteDataSource(RemoteRetrofitDataSource remoteRetrofitDataSource){
        return new RemoteDataSourceImpl(remoteRetrofitDataSource);
    }


    @Provides
    @Singleton
    public LocalDataSource getLocalDataSource(SharedPreferences shared, AppDatabase dataRoom){
        return new LocalDataSourceImpl(shared, dataRoom);
    }

    @Provides
    @Singleton
    public AppDatabase getRoomDatabase(@ApplicationContext Context appContext){
        return Room.databaseBuilder(appContext, AppDatabase.class, "user-database")
                .fallbackToDestructiveMigration().allowMainThreadQueries().build();
    }

}
