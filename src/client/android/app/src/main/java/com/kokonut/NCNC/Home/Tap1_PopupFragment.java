package com.kokonut.NCNC.Home;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.kokonut.NCNC.Calendar.Calendar_PopupFragment;
import com.kokonut.NCNC.R;

public class Tap1_PopupFragment extends DialogFragment {
    private Fragment fragment;

    //뷰 변수 선언
    View view;
    TextView textView_title;
    TextView textView_subtitle;
    ImageButton buttonX;
    ImageButton buttonOK;

    ImageView imageView1;
    TextView textView1;
    //SeekBar seekBar1;
    //TextView textView1_score;

    ImageView imageView2;
    TextView textView2;
    //SeekBar seekBar2;
    //TextView textView2_score;

    ImageView imageView3;
    TextView textView3;
    //SeekBar seekBar3;
    //TextView textView3_score;

//    uploadIdalog_Interface interfaceObj;


    public Tap1_PopupFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
/*
    public static Tap1_PopupFragment getInstance(){
        Tap1_PopupFragment e = new Tap1_PopupFragment();
        return e;
    }
 */

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tap1__popup, container, false);
        textView_title = view.findViewById(R.id.home_popup_title);
        textView_subtitle = view.findViewById(R.id.home_popup_subtitle);
        buttonX = view.findViewById(R.id.home_popup_buttonX);
        buttonOK = view.findViewById(R.id.home_popupButton);

        imageView1 = view.findViewById(R.id.home_popup_Image1);
        imageView2 = view.findViewById(R.id.home_popup_Image2);
        imageView3 = view.findViewById(R.id.home_popup_Image3);
        textView1 = view.findViewById(R.id.home_popup_text1);
        textView2 = view.findViewById(R.id.home_popup_text2);
        textView3 = view.findViewById(R.id.home_popup_text3);


        /*
        fragment = getActivity().getSupportFragmentManager().findFragmentByTag("tab1");
        if(fragment!=null){
            DialogFragment dialogFragment = (DialogFragment)fragment;
            dialogFragment.dismiss();
        }
        */
        setCancelable(false); //popup에서 여백을 만져도 꺼지지 않게 함
        //seekBar();
        return view;
    }

/*
    private void seekBar() {
        final SeekBar seekBar1 = view.findViewById(R.id.home_popup_seekBar1);
        final SeekBar seekBar2 = view.findViewById(R.id.home_popup_seekBar2);
        final SeekBar seekBar3 = view.findViewById(R.id.home_popup_seekBar3);
        //final textView textView1_score = view.findViewById(R.id.home_popup_score1);
        //textView2_score = view.findViewById(R.id.home_popup_score2);
        //textView3_score = view.findViewById(R.id.home_popup_score3);

        dismiss();

    }

 */
}