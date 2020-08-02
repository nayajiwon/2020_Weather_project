package com.kokonut.NCNC.Home;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.kokonut.NCNC.R;

public class Tab1Fragment extends Fragment {
    ViewGroup viewGroup;
    ImageButton popupButton;

    ViewPager2 viewPager2;
    FragmentStateAdapter pagerAdapter;
    int VIEW_CNT=3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Nullable @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_tab1, container, false);
        popupButton = (ImageButton)viewGroup.findViewById(R.id.home_popupButton);

        Log.d("11111", "onCreateView: 1");

        //'맞춤형 세차점수 설정하기' 버튼 클릭 시
        popupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("11111", "onCreateView: 2");

                Tap1_PopupFragment dialog = new Tap1_PopupFragment();
                dialog.show(getActivity().getSupportFragmentManager(), "tab1");

            }
        });

        //'내주변세차장' viewpager 구현
        viewPager2 = (ViewPager2)viewGroup.findViewById(R.id.tab1_viewpager);
        pagerAdapter = new Tab1CarWashInfo_Viewpager2Adapter(this);
        viewPager2.setAdapter(pagerAdapter);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager2.setOffscreenPageLimit(2);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if(positionOffsetPixels==0){
                    viewPager2.setCurrentItem(position);
                }
            }
            /* indicator 설정시 이용
            @Override
            public void onPageSelected(int position){
                super.onPageSelected(position);
                indicator.animatePageSelected(position%VIEW_CNT);
            }*/
        });

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