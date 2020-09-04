package com.kokonut.NCNC.Map;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.kokonut.NCNC.Home.Retrofit.RetrofitAPI;
import com.kokonut.NCNC.Home.Retrofit.RetrofitClient;
import com.kokonut.NCNC.Home.Retrofit.ReviewContents;
import com.kokonut.NCNC.Home.Retrofit.ReviewResponse;
import com.kokonut.NCNC.KakaoAdapter;
import com.kokonut.NCNC.R;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kokonut.NCNC.KakaoAdapter.getInstance;

public class CarWashReviewActivity extends AppCompatActivity {
    ImageView ivBack, ivWriteReview;
    List<ReviewContents.Content> reviewContents; // 리뷰 콘텐츠가 저장되는 리스트

    public static final int sub = 1001;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_wash_review);
        initView();
        fetchReview("1"); // 이 안에 washer의 id를 넣으면 해당 세차장의 리뷰들을 가져옵니다.

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

    public void fetchReview(String washerId){
        RetrofitAPI retrofitAPI = RetrofitClient.getInstance().getClient1().create(RetrofitAPI.class);

        retrofitAPI.fetchReview(washerId).enqueue(new Callback<ReviewContents>() {
            @Override
            public void onResponse(Call<ReviewContents> call, Response<ReviewContents> response) {
                Log.d("fetch_review", "Success: "+new Gson().toJson(response.body().getId()));
                reviewContents = response.body().getReviews();
            }

            @Override
            public void onFailure(Call<ReviewContents> call, Throwable t) {
                Log.e("fetch_review", "failure: "+t.toString());
            }
        });
    }
}
