package com.kokonut.NCNC.Home;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.kokonut.NCNC.R;

public class Tab1_CarWashInfo3 extends Fragment {
    ImageButton moreButton;

    public Tab1_CarWashInfo3() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewgroup = (ViewGroup) inflater.inflate(R.layout.fragment_tab1__car_wash_info3, container, false);
        moreButton = (ImageButton) viewgroup.findViewById(R.id.tab1_moreButton);

        //'더보기' 버튼 클릭시 - Tab1_CarWashList 액티비티로 전환
        moreButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Tab1_CarWashList.class);
                startActivity(intent);
            }
        });



        return viewgroup;
    }
}