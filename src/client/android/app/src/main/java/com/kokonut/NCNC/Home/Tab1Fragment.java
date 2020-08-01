package com.kokonut.NCNC.Home;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.kokonut.NCNC.R;

public class Tab1Fragment extends Fragment {
    ViewGroup viewGroup;
    ImageButton popupButton;
    FragmentTransaction ft;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Nullable @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_tab1, container, false);
        popupButton = (ImageButton) viewGroup.findViewById(R.id.home_popupButton);

        popupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()) {
                    case R.id.home_popupButton:
                        Tap1_PopupFragment dialog = new Tap1_PopupFragment();
                        dialog.show(getActivity().getSupportFragmentManager(), "tab1");
                        break;
                }
            }
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