package com.example.viewpager2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viewpager2.ViewModel.Repository.LocalDataSource.DataRoom;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.Item;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.VideoStats.ItemList.ItemList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class DetailsFragment extends Fragment {

    private List<ItemList> itemLists;
    private List<DataRoom> roomData;

    SharedViewModel sharedViewModel;

    TextView title, description, channel, likes;
    ImageView thumbnail;

    Button btn;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedViewModel = ((MainActivity) requireActivity()).getSharedViewModel();

        title = view.findViewById(R.id.title);
        description = view.findViewById(R.id.description);
        channel = view.findViewById(R.id.channel);
        likes = view.findViewById(R.id.likes);

        thumbnail = view.findViewById(R.id.thumbnail);

        btn = view.findViewById(R.id.btn_youtube);

        if(isConnected()){
            sharedViewModel.getData().observe(getViewLifecycleOwner(), new Observer<List<ItemList>>() {
                @Override
                public void onChanged(List<ItemList> items) {
                    int position = getArguments().getInt("Position");
                    itemLists = items;
                    assert getArguments() != null;
                    title.setText(itemLists.get(position).getItems()
                            .get(position).getSnippet().getTitle());

                    description.setText(itemLists.get(position).getItems()
                            .get(position).getSnippet().getDescription());
                    description.setMovementMethod(new ScrollingMovementMethod());

                    channel.setText(itemLists.get(position).getItems()
                            .get(position).getSnippet().getChannelTitle());

                    likes.setText(itemLists.get(position).getItems()
                            .get(position).getStatistics().getLikeCount());

                    Picasso.get().load(itemLists
                                    .get(position)
                                    .getItem().get(position)
                                    .getSnippet()
                                    .getThumbnails()
                                    .getMedium()
                                    .getUrl())
                            .into(thumbnail);

                    btn.setOnClickListener(view ->{
                        String url = itemLists.get(position).getItem().get(position).getId().getVideoId();
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + url));
                        startActivity(intent);
                    });
                }
            });
        }
        else{
            sharedViewModel.getDataRoom().observe(getViewLifecycleOwner(), new Observer<List<DataRoom>>() {
                @Override
                public void onChanged(List<DataRoom> dataRooms) {
                    roomData = dataRooms;
                    assert getArguments() != null;
                    int position = getArguments().getInt("Position");

                    Log.d("Room", "Title in room " + roomData.get(getArguments().getInt("Position")).getItem());

                    title.setText(roomData.get(position).getItem()
                            .get(position).getItem()
                            .get(position).getSnippet().getTitle());

                    description.setText(roomData.get(position).getItem()
                            .get(position).getItems()
                            .get(position).getSnippet().getDescription());

                    channel.setText(roomData.get(position).getItem()
                            .get(position).getItem()
                            .get(position).getSnippet().getChannelTitle());

                    Picasso.get().load(roomData
                                    .get(position)
                            .getItem()
                            .get(position)
                                    .getItem()
                                    .get(position)
                            .getSnippet()
                            .getThumbnails()
                            .getMedium()
                            .getUrl())
                            .into(thumbnail);

                    btn.setOnClickListener(view -> {
                        Toast.makeText(getContext(), "No internet connection :(", Toast.LENGTH_LONG).show();
                    });
                }
            });
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    public boolean isConnected(){
        boolean connected = false;
        try{
            ConnectivityManager cm = (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        }
        catch (Exception e){
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }
}