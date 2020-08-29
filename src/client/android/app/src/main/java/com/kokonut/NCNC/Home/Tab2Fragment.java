package com.kokonut.NCNC.Home;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.lifecycle.ViewModelStore;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.google.android.gms.common.api.internal.LifecycleFragment;
import com.kokonut.NCNC.R;

import org.w3c.dom.Text;

public class Tab2Fragment extends Fragment {
    ImageButton searchButton;

    public Tab2Fragment() {
        // Required empty public constructor
    }


    private ViewModelProvider.AndroidViewModelFactory viewModelFactory;
    private ViewModelStore viewModelStore = new ViewModelStore();
    //private SharedViewModel sharedViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_tab2, container, false);

        TextView test = viewGroup.findViewById(R.id.test);

        if(viewModelFactory == null){
            viewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication());
        }
        final SharedViewModel sharedViewModel = new ViewModelProvider(this, viewModelFactory).get(SharedViewModel.class);
        sharedViewModel.getMessage().observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                //Log.d("communication using viewModel", o.toString());
                if(o.toString()==null){
                    test.setText("null");
                }
                else
                    test.setText(o.toString());
            }
/*
            @Override
            public void onChanged(@Nullable String s) {
                Log.d("communication using viewModel", s);
                //Toast.makeText(getContext(), s.toString(), Toast.LENGTH_LONG);
            }

 */
        });

        searchButton = (ImageButton) viewGroup.findViewById(R.id.home_tab2_searchbutton);

        RadioGroup radioGroup = (RadioGroup) viewGroup.findViewById(R.id.home_tab2_radiogroup);
        RadioButton rb1 = (RadioButton) viewGroup.findViewById(R.id.home_tab2_mylocation);
        RadioButton rb2 = (RadioButton) viewGroup.findViewById(R.id.home_tab2_locationsetting);

        Spinner location_sp1 = (Spinner) viewGroup.findViewById(R.id.home_tab2_spinner_location1);
        Spinner location_sp2 = (Spinner) viewGroup.findViewById(R.id.home_tab2_spinner_location2);
        Spinner location_sp3 = (Spinner) viewGroup.findViewById(R.id.home_tab2_spinner_location3);
        location_sp1.setPrompt(getResources().getString(R.string.location_spinner1));
        location_sp2.setPrompt(getResources().getString(R.string.location_spinner2));
        location_sp3.setPrompt(getResources().getString(R.string.location_spinner3));

        CheckBox time_check = (CheckBox) viewGroup.findViewById(R.id.home_tab2_vistingtime);
        CheckBox kind_check = (CheckBox) viewGroup.findViewById(R.id.home_tab2_kindofnewcar);
        CheckBox carwash1_check = (CheckBox) viewGroup.findViewById(R.id.checkbox_newcar1);
        CheckBox carwash2_check = (CheckBox) viewGroup.findViewById(R.id.checkbox_newcar2);
        CheckBox carwash3_check = (CheckBox) viewGroup.findViewById(R.id.checkbox_newcar3);
        CheckBox carwash4_check = (CheckBox) viewGroup.findViewById(R.id.checkbox_newcar4);

        Spinner time_sp = (Spinner) viewGroup.findViewById(R.id.home_tab2_spinner_visitingtime);
        time_sp.setPrompt(getResources().getString(R.string.prompt_time));
        ArrayAdapter timeAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.time, android.R.layout.simple_spinner_dropdown_item);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time_sp.setAdapter(timeAdapter);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if(checkedId == R.id.home_tab2_mylocation){
                    location_sp1.setVisibility(View.INVISIBLE);
                    location_sp2.setVisibility(View.INVISIBLE);
                    location_sp3.setVisibility(View.INVISIBLE);

                    //현재 내 위치 정보 받아오기
                    //LiveData<String> s = sharedViewModel.getMessage();
                    //Log.i("communication using viewModel", s.toString());
                    //Toast.makeText(getContext(), s.toString(), Toast.LENGTH_LONG);
                    /*
                    sharedViewModel.getMessage().observe(this, new Observer<String>() {
                        @Override
                        public void onChanged(@Nullable String s) {
                            Log.i("communication using viewModel", s);
                            //Toast.makeText(getContext(), o.toString(), Toast.LENGTH_LONG);
                        }
                    });

                     */
                }
                else{
                    location_sp1.setVisibility(View.VISIBLE);
                    location_sp2.setVisibility(View.VISIBLE);
                    location_sp3.setVisibility(View.VISIBLE);

                    //시,구,동 선택

                    //선택한 위치 정보 -> 검색에 이용

                }
            }
        });

        //'방문시각' 체크박스
        time_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(time_check.isChecked()){
                    time_sp.setVisibility(View.VISIBLE);

                    //선택한 방문시각 정보 -> 검색에 이용
                }
                else{
                    time_sp.setVisibility(View.INVISIBLE);
                }
            }
        });

        /* //선택 안했을 때
        if(time_sp.getSelectedItem().equals("선택해주세요.")){
            Toast.makeText(getContext(),"시간을 선택해주세요.",Toast.LENGTH_LONG).show();
            time_sp.requestFocus();
        }
         */

        //'세차종류' 체크박스
        kind_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(kind_check.isChecked()){
                    carwash1_check.setVisibility(View.VISIBLE);
                    carwash2_check.setVisibility(View.VISIBLE);
                    carwash3_check.setVisibility(View.VISIBLE);
                    carwash4_check.setVisibility(View.VISIBLE);

                    //체크한 세차종류 정보 -> 검색에 이용
                }
                else{
                    carwash1_check.setVisibility(View.INVISIBLE);
                    carwash2_check.setVisibility(View.INVISIBLE);
                    carwash3_check.setVisibility(View.INVISIBLE);
                    carwash4_check.setVisibility(View.INVISIBLE);
                }
            }
        });

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

    @Override
    public void onDestroy(){
        super.onDestroy();
        viewModelStore.clear();
    }

    @NonNull
    @Override
    public ViewModelStore getViewModelStore(){
        return viewModelStore;
    }
}