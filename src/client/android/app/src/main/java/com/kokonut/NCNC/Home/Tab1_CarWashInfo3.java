package com.kokonut.NCNC.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kokonut.NCNC.R;

public class Tab1_CarWashInfo3 extends Fragment {
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
        return viewgroup;
    }
}