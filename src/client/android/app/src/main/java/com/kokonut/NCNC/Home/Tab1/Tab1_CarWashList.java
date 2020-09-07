package com.kokonut.NCNC.Home.Tab1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.gson.Gson;
import com.kokonut.NCNC.Home.CarWashInfoData;
import com.kokonut.NCNC.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tab1_CarWashList extends AppCompatActivity {
    ImageButton tab1_prevButton;

    RecyclerView recyclerView;
    Tab1_RecyclerAdapter tab1_recyclerAdapter;
    private ArrayList<CarWashInfoData> datalist;
    public ArrayList<CarWashInfoData> carWashInfoData;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab1__car_wash_list);

        Intent intent = getIntent();
        carWashInfoData = (ArrayList<CarWashInfoData>) intent.getSerializableExtra("carwashinfodata");
        Collections.sort(carWashInfoData);

        if(carWashInfoData!=null)
            Log.d("!!!!!!!!!!!!!!", carWashInfoData.get(0).getAddress());
        else
            Log.d("!!!!!!!!!!!!!!!", "실패");

        this.InitializeData();

        recyclerView = findViewById(R.id.recycler_view_carwashlist);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(new Tab1_RecyclerAdapter(datalist));


        //뒤로버튼 클릭시
        tab1_prevButton = findViewById(R.id.tab1_list_back_arrow);
        tab1_prevButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });
    }

    public void InitializeData(){
        datalist = new ArrayList<>();

        for(int i=0; i<29; i++){
            //String time = "평일:"+ carWashInfoData.get(i).getOpenWeek()+" 토:"+carWashInfoData.get(i).getOpenSat()+" 일:"+carWashInfoData.get(i).getOpenSun();

            datalist.add(new CarWashInfoData(carWashInfoData.get(i).getName(), carWashInfoData.get(i).getAddress(),
                    carWashInfoData.get(i).getPhone(), carWashInfoData.get(i).getCity(), carWashInfoData.get(i).getDistrict(), carWashInfoData.get(i).getDong(),
                    "토:"+makeOpenTime(carWashInfoData.get(i).getOpenSat()), " 일:"+makeOpenTime(carWashInfoData.get(i).getOpenSun()),
                    "평일:"+makeOpenTime(carWashInfoData.get(i).getOpenWeek()),carWashInfoData.get(i).getDistance(), carWashInfoData.get(i).getWash().toString()));
        }
    }

    private String makeOpenTime(String open_time){
        String result;
        if(open_time == "99:99-99:99") result= "휴무";
        else if(open_time =="00:00-00:00") result= "24시간 운영";
        else result=open_time;

        return result;
    }
}