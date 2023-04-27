package com.example.viewpager2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.window.SplashScreen;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private SharedViewModel sharedViewModel;


    private ViewPager2 viewPager2;
    private RecyclerView numbersList;
    private RecyclerView2 recyclerView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

    }

    public SharedViewModel getSharedViewModel(){
        return sharedViewModel;
    }
}