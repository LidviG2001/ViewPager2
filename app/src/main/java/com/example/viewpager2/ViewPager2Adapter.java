package com.example.viewpager2;

import android.content.Context;
import android.net.ConnectivityManager;
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

import com.example.viewpager2.ViewModel.Repository.LocalDataSource.DataRoom;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.Item;
import com.example.viewpager2.ViewModel.Repository.RemodeDataSource.Model.VideoStats.ItemList.ItemList;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewPager2Adapter extends RecyclerView.Adapter<ViewPager2Adapter.ViewHolder> {

    private List<ItemList> itemLists;
    private  List<DataRoom> roomData;
    private Context context;

    static ClickListener m_listener;

    ViewPager2Adapter(List<ItemList> itemLists, List<DataRoom> roomData, Context context, ClickListener m_listener){
        this.itemLists = itemLists;
        this.roomData = roomData;
        this.context = context;
        this.m_listener = m_listener;
    }

    public void setData(List<ItemList> data) {
        this.itemLists.clear();
        this.itemLists.addAll(data);
        notifyDataSetChanged();
        Log.d("Pager", "Check 2 " + itemLists);
    }

    @NonNull
    @Override
    public ViewPager2Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.images_holder, parent, false);
        ViewPager2Adapter.ViewHolder viewHolder = new ViewPager2Adapter.ViewHolder(view, m_listener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPager2Adapter.ViewHolder holder, int position) {

        Log.d("PAGER", "Check 3 " + itemLists);

        if(isConnected()){
            String date = itemLists.get(position).getItem().get(position).getSnippet().getPublishedAt();
            String c_date = date.substring(0, date.length()-10);

            holder.publishedAt.setText(c_date);
            holder.title.setText(itemLists.get(position).getItem().get(position).getSnippet().getTitle());

            Picasso.get().load(itemLists.get(position).getItem()
                    .get(position)
                    .getSnippet()
                    .getThumbnails()
                    .getMedium()
                    .getUrl()
            ).into(holder.thumbnail);
        }
        else {
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
        Log.d("PAGER", "Check 4 " + itemLists.size());
        int size = 0;
        if(isConnected()){
            return size = itemLists.size();
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

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout root;

        ImageView thumbnail;
        TextView title, publishedAt;

        ClickListener listener;

        public ViewHolder(@NonNull View itemView, ClickListener listener){
            super(itemView);

            this.listener = listener;
            thumbnail = itemView.findViewById(R.id.thumbnail);

            root = itemView.findViewById(R.id.root);

            title = itemView.findViewById(R.id.title);
            publishedAt = itemView.findViewById(R.id.publichedAt);
        }
        void bind(int listIndex){
            int position = getAdapterPosition();
            root.setOnClickListener( view -> {
                m_listener.onClick(position);
            });
        }
    }
}
