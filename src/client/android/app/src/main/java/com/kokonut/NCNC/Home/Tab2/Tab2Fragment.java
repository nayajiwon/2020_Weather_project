package com.kokonut.NCNC.Home.Tab2;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kokonut.NCNC.Retrofit.Location2Contents;
import com.kokonut.NCNC.Retrofit.RetrofitAPI;
import com.kokonut.NCNC.Retrofit.RetrofitClient;
import com.kokonut.NCNC.Home.SharedViewModel;
import com.kokonut.NCNC.R;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Tab2Fragment extends Fragment {
    private RetrofitAPI retrofitAPI;

    public String selectedLocation1; //시
    public String selectedLocation2; //구
    public String selectedLocation3; //동
    public String selectedTime1; //요일
    public String selectedTime2; //시간

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

        ImageButton searchButton = (ImageButton) viewGroup.findViewById(R.id.home_tab2_searchbutton);

        RadioGroup radioGroup = (RadioGroup) viewGroup.findViewById(R.id.home_tab2_radiogroup);
        RadioButton rb1 = (RadioButton) viewGroup.findViewById(R.id.home_tab2_mylocation);
        RadioButton rb2 = (RadioButton) viewGroup.findViewById(R.id.home_tab2_locationsetting);

        CheckBox time_check = (CheckBox) viewGroup.findViewById(R.id.home_tab2_vistingtime);
        CheckBox kind_check = (CheckBox) viewGroup.findViewById(R.id.home_tab2_kindofnewcar);
        CheckBox carwash1_check = (CheckBox) viewGroup.findViewById(R.id.checkbox_newcar1);
        CheckBox carwash2_check = (CheckBox) viewGroup.findViewById(R.id.checkbox_newcar2);
        CheckBox carwash3_check = (CheckBox) viewGroup.findViewById(R.id.checkbox_newcar3);
        CheckBox carwash4_check = (CheckBox) viewGroup.findViewById(R.id.checkbox_newcar4);

        Spinner location_sp1 = (Spinner) viewGroup.findViewById(R.id.home_tab2_spinner_location1);
        Spinner location_sp2 = (Spinner) viewGroup.findViewById(R.id.home_tab2_spinner_location2);
        Spinner location_sp3 = (Spinner) viewGroup.findViewById(R.id.home_tab2_spinner_location3);
        location_sp1.setPrompt(getResources().getString(R.string.location_spinner1)); location_sp2.setPrompt(getResources().getString(R.string.location_spinner2)); location_sp3.setPrompt(getResources().getString(R.string.location_spinner3));

        ArrayList location1_items = new ArrayList();
        location1_items.add("시"); location1_items.add("서울시");
        ArrayAdapter<String> locationsp1Adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, location1_items);
        locationsp1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        location_sp1.setAdapter(locationsp1Adapter);

        ArrayList location2_items = new ArrayList();
        location2_items.add("동");
        ArrayAdapter<String> locationsp2Adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, location2_items);
        locationsp2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        location_sp2.setAdapter(locationsp2Adapter);

        ArrayList location3_items = new ArrayList();
        location3_items.add("구");
        ArrayAdapter<String> locationsp3Adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, location3_items);
        locationsp3Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        location_sp3.setAdapter(locationsp3Adapter);

        //서버통신 시구동

        // '서울시' 선택 -> 구 -> 동
        location_sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if(position!=0){
                    selectedLocation1 = locationsp1Adapter.getItem(position);

                    location2_items.clear();
                    location2_items.add("동");

                    //서울시 선택 -> 동 받아오기
                    retrofitAPI = RetrofitClient.getInstance().getClient3().create(RetrofitAPI.class);
                    retrofitAPI.fetchLocation2().enqueue(new Callback<Location2Contents>() {
                        @Override
                        public void onResponse(Call<Location2Contents> call, Response<Location2Contents> response) {
                            Location2Contents.SiGunGuList[] ss = response.body().getSiGunGuListResponse().getSiGunGuList();
                            //Log.d("SigunguList", ss[0].getSignguCd());
                            for(int i=0; i<ss.length; i++){
                                location2_items.add(ss[i].getSignguCd());
                                locationsp2Adapter.notifyDataSetChanged();
                            }
                        }
                        @Override
                        public void onFailure(Call<Location2Contents> call, Throwable t) {

                        }
                    });

                    location_sp2.setAdapter(locationsp2Adapter);
                    location_sp2.setSelection(0);
                }
                else{
                    Toast.makeText(getContext(),"주소를 선택해주세요.",Toast.LENGTH_LONG).show();
                    location_sp1.requestFocus();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Spinner time_sp1 = (Spinner) viewGroup.findViewById(R.id.home_tab2_spinner_visitingtime1);
        Spinner time_sp2 = (Spinner) viewGroup.findViewById(R.id.home_tab2_spinner_visitingtime2);
        time_sp1.setPrompt("요일"); time_sp2.setPrompt("시간");


        String sp1_items[]={"평일", "토요일", "일요일"};
        String sp2_items[]={"00:00", "01:00","02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00",
                "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", "24:00"};

        ArrayAdapter<String> timeSp1Adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, sp1_items);
        timeSp1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time_sp1.setAdapter(timeSp1Adapter);

        ArrayAdapter<String> timeSp2Adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, sp2_items);
        timeSp2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time_sp2.setAdapter(timeSp2Adapter);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if(checkedId == R.id.home_tab2_mylocation){
                    location_sp1.setVisibility(View.INVISIBLE);
                    location_sp2.setVisibility(View.INVISIBLE);
                    location_sp3.setVisibility(View.INVISIBLE);

                    //현재 내 위치 정보 받아오기

                }
                else{
                    location_sp1.setVisibility(View.VISIBLE);
                    location_sp2.setVisibility(View.VISIBLE);
                    location_sp3.setVisibility(View.VISIBLE);
                }
            }
        });

        //'방문시각' 체크박스
        time_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(time_check.isChecked()){
                    time_sp1.setVisibility(View.VISIBLE);
                    time_sp2.setVisibility(View.VISIBLE);
                }
                else{
                    time_sp1.setVisibility(View.INVISIBLE);
                    time_sp2.setVisibility(View.INVISIBLE);

                }
            }
        });

        //요일 spinner 선택리스너
        time_sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                //selectedTime1 = time_sp1.getSelectedItem().toString();
                if(position!=0){
                    timeSp1Adapter.notifyDataSetChanged();
                    selectedTime1 = timeSp1Adapter.getItem(position);
                }
                else{
                    Toast.makeText(getContext(),"요일을 선택해주세요.",Toast.LENGTH_LONG).show();
                    time_sp1.requestFocus();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getContext(),"요일을 선택해주세요.",Toast.LENGTH_LONG).show();
                time_sp1.requestFocus();
            }
        });

        //시간 spinner 선택리스너
        time_sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                //selectedTime2 = time_sp2.getSelectedItem().toString();
                if(position!=0){
                    timeSp2Adapter.notifyDataSetChanged();
                    selectedTime2 = timeSp2Adapter.getItem(position);
                }
                else{
                    Toast.makeText(getContext(),"시간을 선택해주세요.",Toast.LENGTH_LONG).show();
                    time_sp2.requestFocus();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getContext(),"시간을 선택해주세요.",Toast.LENGTH_LONG).show();
                time_sp2.requestFocus();
            }
        });

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
    }

}