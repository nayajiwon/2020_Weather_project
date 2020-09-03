package com.kokonut.NCNC.Home.Tab1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.kokonut.NCNC.R;

public class Tab1_CarWashList extends AppCompatActivity {
    ImageButton tab1_prevButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab1__car_wash_list);

        //뒤로버튼 클릭시
        tab1_prevButton = findViewById(R.id.tab1_list_back_arrow);
        tab1_prevButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });
    }
}