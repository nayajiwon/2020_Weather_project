package com.kokonut.NCNC.Home.Tab1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.kokonut.NCNC.Home.CarWashInfoData;
import com.kokonut.NCNC.R;

import java.util.ArrayList;
import java.util.List;

public class Tab1_CarWashList extends AppCompatActivity {
    ImageButton tab1_prevButton;

    private ArrayList<CarWashInfoData_using4> datalist;
    public ArrayList<CarWashInfoData> carWashInfoData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab1__car_wash_list);

        Intent intent = getIntent();
        carWashInfoData = (ArrayList<CarWashInfoData>) intent.getSerializableExtra("carwashinfodata1");
        if(carWashInfoData!=null)
            Log.d("!!!!!!!!!!!!!!", carWashInfoData.get(0).getAddress());
        else
            Log.d("!!!!!!!!!!!!!!!", "실패");

        //this.InitializeData();

        RecyclerView recyclerView = findViewById(R.id.recycler_view_carwashlist);
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
            String time = "평일: "+ carWashInfoData.get(i).getOpenWeek()+" 토: "+carWashInfoData.get(i).getOpenSat()+" 일: "+carWashInfoData.get(i).getOpenSun();
            datalist.add(new CarWashInfoData_using4(carWashInfoData.get(i).getName(), carWashInfoData.get(i).getAddress(),
                    time, carWashInfoData.get(i).getWash()));
        }
    }

}