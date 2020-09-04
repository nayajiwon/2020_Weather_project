package com.kokonut.NCNC.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kokonut.NCNC.R;

public class CarWashInfoActivity extends AppCompatActivity {
    ImageView ivBack, ivOnButton, ivOnButton2, ivOnButton3, ivReview;
    TextView tvReviewType, tvReviewName, tvReviewAddress, tvReviewTime1, tvReviewTime2,
            tvReviewTime3, tvReviewPrice1, tvReviewPrice2, tvReviewEvent, tvReviewFacilities;

    public static final int sub = 1001;
    MapFragment mapFragment = new MapFragment();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_wash_info);
        initView();

        Intent intent = getIntent();

        String id = intent.getExtras().getString("id");
        String name = intent.getExtras().getString("name");
        String latitude = intent.getExtras().getString("latitude");
        String longitude = intent.getExtras().getString("longitude");
        String address = intent.getExtras().getString("address");
        String phone = intent.getExtras().getString("phone");
        String city = intent.getExtras().getString("city");
        String district = intent.getExtras().getString("district");
        String dong = intent.getExtras().getString("dong");
        String type = intent.getExtras().getString("type");
        String open_week = intent.getExtras().getString("open_week");
        String open_sat = intent.getExtras().getString("open_sat");
        String open_sun = intent.getExtras().getString("open_sun");

        System.out.println("CarWashInfoActivity + " + id);

        tvReviewType.setText(type);
        tvReviewName.setText(name);
        tvReviewAddress.setText(address);
        tvReviewTime1.setText(open_week);
        tvReviewTime2.setText(open_sat);
        tvReviewTime3.setText(open_sun);
        tvReviewPrice1.setText(open_sun);
        tvReviewPrice2.setText(longitude);
        tvReviewEvent.setText(phone);
        tvReviewFacilities.setText(city);


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getSupportFragmentManager().beginTransaction().replace(R.id.layout_car_wash_info, MainActivity).commitAllowingStateLoss();
                finish();
            }
        });

        ivReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CarWashReviewActivity.class);
                intent.putExtra("id", id);
                startActivityForResult(intent, sub);
            }
        });

    }
    

    void initView(){
        ivBack = findViewById(R.id.car_wash_info_back_arrow);
        ivReview = findViewById(R.id.car_wash_info_review_box);

        tvReviewType = findViewById(R.id.car_wash_info_type);
        tvReviewName = findViewById(R.id.car_wash_info_name);
        tvReviewAddress = findViewById(R.id.car_wash_info_address);
        tvReviewTime1 = findViewById(R.id.car_wash_info_time_1);
        tvReviewTime2 = findViewById(R.id.car_wash_info_time_2);
        tvReviewTime3 = findViewById(R.id.car_wash_info_time_3);
        tvReviewPrice1 = findViewById(R.id.car_wash_info_price_1);
        tvReviewPrice2 = findViewById(R.id.car_wash_info_price_2);
        tvReviewEvent = findViewById(R.id.car_wash_info_event);
        tvReviewFacilities = findViewById(R.id.car_wash_info_facilities);

    }
}
