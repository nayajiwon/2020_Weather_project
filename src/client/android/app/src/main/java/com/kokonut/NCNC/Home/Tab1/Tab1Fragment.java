package com.kokonut.NCNC.Home.Tab1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kokonut.NCNC.Home.HomeFragment;
import com.kokonut.NCNC.Home.Retrofit.RealTimeWeatherContents;
import com.kokonut.NCNC.Home.Retrofit.RetrofitAPI;
import com.kokonut.NCNC.Home.Retrofit.RetrofitClient;
import com.kokonut.NCNC.Home.Retrofit.ScoreContents;
import com.kokonut.NCNC.Home.Retrofit.WeatherContents;
import com.kokonut.NCNC.Home.SharedViewModel;
import com.kokonut.NCNC.R;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Tab1Fragment extends Fragment implements ActivityCompat.OnRequestPermissionsResultCallback{

    private RetrofitAPI retrofitAPI;
    public String[] scoreList = new String[8];

    public TextView tvLocation;

    ViewGroup viewGroup;
    ImageButton popupButton;

    ViewPager2 viewPager2;
    FragmentStateAdapter pagerAdapter;

    TextView date1, date2, date3, date4, date5, date6, date7;
    TextView todayScore, score1, score2, score3, score4, score5, score6, score7, goodDay;
    TextView thermometer, rain, mask;

    public Tab1Fragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Home_Tab1", "onCreate: 1");

    }


    @Nullable @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_tab1, container, false);
        popupButton = viewGroup.findViewById(R.id.home_popupButton);

        tvLocation = viewGroup.findViewById(R.id.tab1_tv_location);

        date1 = viewGroup.findViewById(R.id.home_tab1_day1);
        date2 = viewGroup.findViewById(R.id.home_tab1_day2);
        date3 = viewGroup.findViewById(R.id.home_tab1_day3);
        date4 = viewGroup.findViewById(R.id.home_tab1_day4);
        date5 = viewGroup.findViewById(R.id.home_tab1_day5);
        date6 = viewGroup.findViewById(R.id.home_tab1_day6);
        date7 = viewGroup.findViewById(R.id.home_tab1_day7);

        todayScore = viewGroup.findViewById(R.id.home_score);
        score1 = viewGroup.findViewById(R.id.home_tab1_day1_score);
        score2 = viewGroup.findViewById(R.id.home_tab1_day2_score);
        score3 = viewGroup.findViewById(R.id.home_tab1_day3_score);
        score4 = viewGroup.findViewById(R.id.home_tab1_day4_score);
        score5 = viewGroup.findViewById(R.id.home_tab1_day5_score);
        score6 = viewGroup.findViewById(R.id.home_tab1_day6_score);
        score7 = viewGroup.findViewById(R.id.home_tab1_day7_score);
        goodDay = viewGroup.findViewById(R.id.home_tab1_goodday_text);

        thermometer = viewGroup.findViewById(R.id.thermometer);
        rain = viewGroup.findViewById(R.id.rain);
        mask = viewGroup.findViewById(R.id.mask);


        //서버 통신 - 현재 날씨
        retrofitAPI = RetrofitClient.getInstance().getClient2().create(RetrofitAPI.class);
        retrofitAPI.fetchRealtimeWeather().enqueue(new Callback<RealTimeWeatherContents>() {
            @Override
            public void onResponse(Call<RealTimeWeatherContents> call, Response<RealTimeWeatherContents> response) {
                //Log.d("Retrofit_Realtime", "Success: "+new Gson().toJson(response.body().getData()));
                RealTimeWeatherContents.Data result = response.body().getData();
                if(result==null){
                    Log.e("Retrofit_Realtime", "Success: NULL");
                }
                else{
                    thermometer.setText(result.getIaqi().getT().getV()+"℃");
                    rain.setText((result.getIaqi().getH().getV()).intValue()+"%");
                    mask.setText(makeAQI((result.getAqi()).intValue()));
                }
            }

            @Override
            public void onFailure(Call<RealTimeWeatherContents> call, Throwable t) {
                Log.e("Retrofit_Realtime", "failure: "+t.toString());
            }
        });



        //서버 통신 - 세차 점수
        retrofitAPI = RetrofitClient.getInstance().getClient1().create(RetrofitAPI.class);
        retrofitAPI.fetchScore().enqueue(new Callback<ScoreContents>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(Call<ScoreContents> call, Response<ScoreContents> response) {
                //Log.d("Retrofit_Score", "Success: "+new Gson().toJson(response.body().getContents()));

                List<ScoreContents.Content> mlist = response.body().getContents();
                if(mlist==null){ //서버에 해당정보 없을 때
                    Log.e("Retrofit_Score", "Success: NULL");
                }
                else{
                    scoreList= new String[8]; //초기화
                    int maxScore = 0, maxScoreDay = 0;
                    for(int i=0; i<7; i++){
                        scoreList[i] = makeScoreList(mlist.get(i).getRnLv(), mlist.get(i).getTaLv(), mlist.get(i).getPm10Lv());
                        //Log.d("scoreList", scoreList[i]);
                        if(maxScore < Integer.parseInt(scoreList[i])){
                            maxScore = Integer.parseInt(scoreList[i]);
                            maxScoreDay = i;
                        }
                    }

                    todayScore.setText(scoreList[0]+"점");
                    score1.setText(scoreList[0]+"점");
                    score2.setText(scoreList[1]+"점");
                    score3.setText(scoreList[2]+"점");
                    score4.setText(scoreList[3]+"점");
                    score5.setText(scoreList[4]+"점");
                    score6.setText(scoreList[5]+"점");
                    score7.setText(scoreList[6]+"점");
                    goodDay.setText("이번 주 세차하기 좋은 날은 "+getDate(maxScoreDay)+"일 입니다");

                    switch (maxScoreDay){
                        case 0:
                            date1.setBackgroundResource(R.drawable.home_daybox_color);
                            date1.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.color_white));
                            score1.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.color_main));
                            break;
                        case 1:
                            date2.setBackgroundResource(R.drawable.home_daybox_color);
                            date2.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.color_white));
                            score2.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.color_main));
                            break;
                        case 2:
                            date3.setBackgroundResource(R.drawable.home_daybox_color);
                            date3.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.color_white));
                            score3.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.color_main));
                            break;
                        case 3:
                            date4.setBackgroundResource(R.drawable.home_daybox_color);
                            date4.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.color_white));
                            score4.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.color_main));
                            break;
                        case 4:
                            date5.setBackgroundResource(R.drawable.home_daybox_color);
                            date5.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.color_white));
                            score5.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.color_main));
                            break;
                        case 5:
                            date6.setBackgroundResource(R.drawable.home_daybox_color);
                            date6.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.color_white));
                            score6.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.color_main));
                            break;
                        case 6:
                            date7.setBackgroundResource(R.drawable.home_daybox_color);
                            date7.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.color_white));
                            score7.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.color_main));
                            break;
                    }

                }
            }

            @Override
            public void onFailure(Call<ScoreContents> call, Throwable t) {
                Log.e("Retrofit_Score", "failure: "+t.toString());
            }
        });


        //'맞춤형 세차점수 설정하기' 버튼 클릭 시
        popupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("11111", "onCreateView: 2");
                Tab1_PopupFragment dialog = new Tab1_PopupFragment();
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

        //오늘로부터 일주일 날짜
        date1.setText(getDate(0)); //오늘
        date2.setText(getDate(1));
        date3.setText(getDate(2));
        date4.setText(getDate(3));
        date5.setText(getDate(4));
        date6.setText(getDate(5));
        date7.setText(getDate(6));

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


    public static String getDate(int weekday){
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("d");
        Calendar calendar = Calendar.getInstance(); //현재 날짜
        calendar.add(Calendar.DAY_OF_MONTH, weekday); //오늘로부터 일주일일때
        String day = format.format(calendar.getTime());
        return day;
    }

    public String makeScoreList(int rn_lv, int ta_lv, int pm10_lv){
        String str = String.valueOf(rn_lv*9 + ta_lv*2);
        return str;
    }

    public String makeAQI(int aqi){
        String aqiResult;
        if(aqi>=0 && aqi<=30)
            aqiResult="미세먼지 좋음";
        else if(aqi>=31 && aqi<=80)
            aqiResult="미세먼지 보통";
        else if(aqi>=81 && aqi<=150)
            aqiResult="미세먼지 나쁨";
        else
            aqiResult="미세먼지 매우나쁨";
        return aqiResult;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
