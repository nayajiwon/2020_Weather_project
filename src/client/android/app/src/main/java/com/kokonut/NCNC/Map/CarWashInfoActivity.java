package com.kokonut.NCNC.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kokonut.NCNC.R;

public class CarWashInfoActivity extends AppCompatActivity {
    ImageView ivBack, ivOnButton, ivOnButton2, ivOnButton3, ivReview;

    public static final int sub = 1001;
    MapFragment mapFragment = new MapFragment();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_wash_info);
        initView();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.layout_car_wash_info,mapFragment).commitAllowingStateLoss();
            }
        });

        ivReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CarWashReviewActivity.class);
                startActivityForResult(intent, sub);
            }
        });

    }
    


    void initView(){
        ivBack = findViewById(R.id.car_wash_info_back_arrow);
        ivReview = findViewById(R.id.car_wash_info_review_box);
    }
}
