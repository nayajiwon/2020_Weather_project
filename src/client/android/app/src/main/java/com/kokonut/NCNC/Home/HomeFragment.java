package com.kokonut.NCNC.Home;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.kokonut.NCNC.R;

import java.util.Arrays;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class HomeFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewGroup viewGroup;
    List<String> tabLayoutTextArray = Arrays.asList("오늘의 세차", "세차장 검색");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        View view = inflater.inflate(R.layout.fragment_home, container, false);
//
//        tabLayout = (TabLayout)view.findViewById(R.id.home_tablayout);
//        viewPager2 = (ViewPager2) view.findViewById(R.id.home_viewpager2);
//        viewPager2.setAdapter(new Tab_Viewpager2Adapter(this));
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
        tabLayout = viewGroup.findViewById(R.id.home_tablayout);
        viewPager2 = viewGroup.findViewById(R.id.home_viewpager2);
        viewPager2.setAdapter(new Tab_Viewpager2Adapter(this));
        viewPager2.setSaveEnabled(false);

        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> tab.setText(tabLayoutTextArray.get(position))
        ).attach();

//        return view;
        return viewGroup;
    }

    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onResume(){
        super.onResume();

    }

    @Override
    public void onPause(){
        super.onPause();
    }
}