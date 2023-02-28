package com.example.viewpager2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;


public class MainFragment extends Fragment {

    private ViewPager2 viewPager2;
    private RecyclerView numbersList;
    private RecyclerView2 recyclerView2;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager2 = view.findViewById(R.id.viewpager);

        numbersList = view.findViewById(R.id.rv_recomended);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        numbersList.setLayoutManager(layoutManager);

        numbersList.setHasFixedSize(true);

        recyclerView2 = new RecyclerView2(10, new ClickListener() {
            @Override
            public void onClick(int index) {
                com.example.viewpager2.MainFragmentDirections.ActionMainToDetails action = MainFragmentDirections.actionMainToDetails();
                action.setMyArg(index);
                Navigation.findNavController(view).navigate(action);
            }
        });
        numbersList.setAdapter(recyclerView2);


        if (getActivity() != null) {
            ViewPager2Adapter viewPager2Adapter = new ViewPager2Adapter(getActivity());

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
}