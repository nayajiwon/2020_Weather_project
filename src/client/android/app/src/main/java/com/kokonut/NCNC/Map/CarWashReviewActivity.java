package com.kokonut.NCNC.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kokonut.NCNC.R;

public class CarWashReviewActivity extends AppCompatActivity {
    ImageView ivBack, ivWriteReview;

    public static final int sub = 1001;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_wash_review);
        initView();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CarWashInfoActivity.class);
                startActivityForResult(intent, sub);
            }
        });

        ivWriteReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WriteReviewActivity.class);
                startActivityForResult(intent, sub);
            }
        });

    }

    protected void onPause() {
        super.onPause();
        finish();
    }


    void initView(){
        ivBack = findViewById(R.id.car_wash_review_back_arrow);
        ivWriteReview = findViewById(R.id.car_wash_review_write_button);

    }
}
