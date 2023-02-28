package com.example.viewpager2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerView2 extends RecyclerView.Adapter<RecyclerView2.NumberViewHolder> {

    TextView viewHolderIndex;

    ClickListener m_listener;

    private static int viewHolderCount;
    private int numberItems;

    public RecyclerView2(int numberOfItems, ClickListener listener){
        numberItems = numberOfItems;
        viewHolderCount = 0;
        m_listener = listener;
    }


    @Override
    public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItems = R.layout.list_layout;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItems, parent, false);

        NumberViewHolder viewHolder = new NumberViewHolder(view, m_listener);
        viewHolder.viewHolderIndex.setText("ViewHolder index: " + viewHolderCount);

        viewHolderCount++;

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NumberViewHolder holder, int position) {

        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return numberItems;
    }

    class NumberViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout root;

        public TextView viewHolderIndex;
        TextView listItemNumberView;

        public NumberViewHolder(View itemView, ClickListener m_listener){
            super(itemView);

            root = itemView.findViewById(R.id.root);
            listItemNumberView = itemView.findViewById(R.id.tv_number_item);
            viewHolderIndex = itemView.findViewById(R.id.tv_view_holder_number);
        }
        void bind(int listIndex){
            root.setOnClickListener( view -> {
                m_listener.onClick(listIndex);
            });
            listItemNumberView.setText(String.valueOf(listIndex));
        }
    }
}

interface ClickListener {
    void onClick(int index);
}

