package com.kokonut.NCNC.Home;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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
    private int lastValue1, lastValue2, lastValue3; //마지막으로 설정한 '맞춤형 세차점수 설정하기'

    View view;
    TextView textView_title, textView_subtitle;
    ImageButton buttonX, buttonOK;
    TextView textView1, textView2, textView3, textView1_score, textView2_score, textView3_score;
    ImageView imageView1, imageView2, imageView3;

    public Tap1_PopupFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //lastValue = UserConfig.getConfigInt(getActivity(), "gainvalue", 40);
    }

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
        textView1_score = view.findViewById(R.id.home_popup_score1);
        textView2_score = view.findViewById(R.id.home_popup_score2);
        textView3_score = view.findViewById(R.id.home_popup_score3);

        final SeekBar seekBar1 = view.findViewById(R.id.home_popup_seekBar1);
        //seekBar1.setProgress(lastValue); 마지막으로 설정한 값 기억
        //seekBar1.incrementProgressBy(1); 1씩 증가
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekbar1, int progress, boolean fromUser) {
                String val1 = Integer.toString(progress);
                textView1_score.setText(val1);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekbar1) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekbar1) {}
        });

        final SeekBar seekBar2 = view.findViewById(R.id.home_popup_seekBar2);
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekbar2, int progress, boolean fromUser) {
                String val2 = Integer.toString(progress);
                textView2_score.setText(val2);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekbar2) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekbar2) {}
        });

        final SeekBar seekBar3 = view.findViewById(R.id.home_popup_seekBar3);
        seekBar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekbar3, int progress, boolean fromUser) {
                String val3 = Integer.toString(progress);
                textView3_score.setText(val3);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekbar3) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekbar3) {}
        });

        seekBar1.setMax(100); seekBar2.setMax(100); seekBar3.setMax(100);
        seekBar1.setProgress(0); seekBar2.setProgress(0); seekBar3.setProgress(0); //초기값


        //X버튼 클릭 시 - 팝업창 닫기
        buttonX.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //dismiss();
                FragmentManager manager = getFragmentManager();
                Fragment prev = manager.findFragmentByTag("tab1");
                if(prev!=null){
                    DialogFragment dialogfragment = (DialogFragment) prev;
                    dialogfragment.dismiss();
                }
            }
        });


        setCancelable(false); //popup에서 여백을 만져도 꺼지지 않게 함

        return view;
    }
}