package com.example.viewpager2;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.viewpager2.ViewModel.MainViewModel;
import com.example.viewpager2.ViewModel.Repository.LocalDataSource.AppDatabase;
import com.example.viewpager2.ViewModel.Repository.LocalDataSource.DataRoom;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.Item;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.VideoStats.ItemList.ItemList;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.VideoStats.ItemList.ListConverter;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.VideoStats.Items;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainFragment extends Fragment {

    private ViewPager2 viewPager2;
    private RecyclerView numbersList;
    private RecyclerView2 recyclerView2;
    private ViewPager2Adapter viewPager2Adapter;

    private List<ItemList> itemList;

    private SearchView searchView;

    private SharedViewModel sharedViewModel;

    private MainViewModel viewModel;

    private String API_KEY = "Your_API_Key"; // set your API KEY
    private String CHANNEL_ID = "top in ukraine";
    private String ORDER = "viewCount";
    private String PART = "snippet";
    private String PARTS = "snippet,statistics";
    private String TYPE = "video";

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchView = view.findViewById(R.id.searchView);

        viewPager2 = view.findViewById(R.id.viewpager);
        numbersList = view.findViewById(R.id.rv_recomended);

        sharedViewModel = ((MainActivity) requireActivity()).getSharedViewModel();

        viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);
        observeViewModel();

        viewModel.getVideoDetails(PART, PARTS, CHANNEL_ID, API_KEY, ORDER, TYPE);

        searchView.setQueryHint("Search");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String CHANNEL_ID = newText;
                if(CHANNEL_ID.length() > 3){
                    viewModel.getVideoDetails(PART, PARTS, CHANNEL_ID, API_KEY, ORDER, TYPE);
                }
                return false;
            }
        });



        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        numbersList.setLayoutManager(layoutManager);

        numbersList.setHasFixedSize(true);

        recyclerView2 = new RecyclerView2(
                getContext(),
                viewModel.getDataRoom(),
                new ArrayList<>(),
                new ClickListener() {
            @Override
            public void onClick(int index) {
                sharedViewModel.setData(itemList);
                sharedViewModel.setDataRoom(viewModel.getDataRoom());
                Bundle position = new Bundle();
                position.putInt("Position", index);
                MainFragmentDirections.ActionMainToDetails action = MainFragmentDirections.actionMainToDetails();
                action.setMyArg(index);
                Navigation.findNavController(view).navigate(R.id.action_Main_to_Details, position);
            }
        });
        numbersList.setAdapter(recyclerView2);

       if (getActivity() != null) {
           viewPager2Adapter = new ViewPager2Adapter(new ArrayList<>(),
                   viewModel.getDataRoom(),
                   getContext(),
                   new ClickListener(){
               @Override
               public void onClick(int index) {
                   sharedViewModel.setData(itemList);
                   sharedViewModel.setDataRoom(viewModel.getDataRoom());
                   Bundle position = new Bundle();
                   position.putInt("Position", index);
                   MainFragmentDirections.ActionMainToDetails action = MainFragmentDirections.actionMainToDetails();
                   action.setMyArg(index);
                   Navigation.findNavController(view).navigate(R.id.action_Main_to_Details, position);
               }
           });

            viewPager2.setAdapter(viewPager2Adapter);

            viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }

                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    super.onPageScrollStateChanged(state);
                }
            });
        }

        else {  Toast.makeText(getContext(),"Activity is null", Toast.LENGTH_LONG).show();}
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    public void observeViewModel(){
        viewModel.showToast.observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
            }
        });

        viewModel.list.observe(getActivity(), new Observer<List<ItemList>>() {
            @Override
            public void onChanged(List<ItemList> itemLists) {
                recyclerView2.setDataList(itemLists);
                itemList = itemLists;
                viewPager2Adapter.setData(itemLists);
            }
        });
    }
}

