package com.kokonut.NCNC.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.kokonut.NCNC.R;

public class Tab1_CarWashList extends AppCompatActivity {
    ImageButton prevButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab1__car_wash_list);

        //뒤로버튼 클릭시
        prevButton = findViewById(R.id.tab1_list_back_arrow);
        prevButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });
    }
}