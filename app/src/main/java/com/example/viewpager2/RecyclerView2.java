package com.example.viewpager2;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.viewpager2.ViewModel.Repository.LocalDataSource.AppDatabase;
import com.example.viewpager2.ViewModel.Repository.LocalDataSource.DataRoom;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.Item;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.VideoDetails;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.VideoStats.ItemList.ItemList;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.VideoStats.ItemList.ListConverter;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.VideoStats.Items;
import com.squareup.picasso.Picasso;

import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class RecyclerView2 extends RecyclerView.Adapter<RecyclerView2.NumberViewHolder> {


    private List<DataRoom> roomData;

    private List<ItemList> itemList;

    Context context;

    ClickListener m_listener;

    public RecyclerView2(Context context,
                         List<DataRoom> roomData,
                         List<ItemList> itemList,
                         ClickListener listener){
        this.itemList = itemList;
        this.roomData = roomData;
        this.context = context;
        this.m_listener = listener;

    }


    public void setDataList(List<ItemList> dataList) {
        this.itemList.clear();
        this.itemList.addAll(dataList);
        notifyDataSetChanged();
    }

    @Override
    public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItems = R.layout.list_layout;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItems, parent, false);

        NumberViewHolder viewHolder = new NumberViewHolder(view, m_listener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NumberViewHolder holder, int position) {

        if (isConnected()){

            String date = itemList.get(position).getItems().get(position).getSnippet().getPublishedAt();
            String c_date = date.substring(0, date.length()-10);
            holder.publishedAt.setText(c_date);

            holder.title.setText(itemList.get(position).getItem().get(position).getSnippet().getTitle());
            Picasso.get().load(itemList.get(position).getItem()
                            .get(position)
                            .getSnippet()
                            .getThumbnails()
                            .getMedium()
                            .getUrl())
                    .into(holder.thumbnail);
        }
        else{
            String date = roomData.get(position).getItem().get(position).getItem().get(position).getSnippet().getPublishedAt();
            String c_date = date.substring(0, date.length()-10);

            holder.publishedAt.setText(c_date);
            holder.title.setText(roomData.get(position).getItem().get(position).getItem().get(position).getSnippet().getTitle());
            Picasso.get().load(roomData.get(position)
                            .getItem()
                            .get(position)
                            .getItem()
                            .get(position)
                            .getSnippet()
                            .getThumbnails()
                            .getMedium()
                            .getUrl())
                    .into(holder.thumbnail);
        }
        holder.bind(position);
    }



    @Override
    public int getItemCount() {
        int size = 0;
        if(isConnected()){
            Log.d("TAG", "List size is " + itemList.size());
            return size = itemList.size();
        }
        else {
            return size = roomData.size();
        }
    }

    public boolean isConnected(){
        boolean connected = false;
        try{
            ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        }
        catch (Exception e){
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }

    class NumberViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout root;

        private TextView publishedAt, title, description;
        private ImageView thumbnail;

        ClickListener listener;


        public NumberViewHolder(View itemView, ClickListener listener){
            super(itemView);

            this.listener = listener;

            root = itemView.findViewById(R.id.root);

            publishedAt = itemView.findViewById(R.id.publichedAt);
            title = itemView.findViewById(R.id.title);
            thumbnail = itemView.findViewById(R.id.thumbnail);


        }
        void bind(int listIndex){
            int position = getAdapterPosition();
            root.setOnClickListener( view -> {
                m_listener.onClick(position);
            });
        }
    }
}

interface ClickListener {
    void onClick(int index);
}


