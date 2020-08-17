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

public class Tab2Fragment extends Fragment {
    ImageButton searchButton;

    public Tab2Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_tab2, container, false);
        searchButton = (ImageButton) viewGroup.findViewById(R.id.home_tab2_searchbutton);

        //'세차장 검색' 버튼 클릭 시 - Tab2_searchList 액티비티로 전환
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Tab2_SearchList.class);
                startActivity(intent);
            }
        });
    return viewGroup;
    }
}