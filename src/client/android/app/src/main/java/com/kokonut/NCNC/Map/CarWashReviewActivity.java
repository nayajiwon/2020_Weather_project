package com.kokonut.NCNC.Map;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import com.kokonut.NCNC.Retrofit.RetrofitAPI;
import com.kokonut.NCNC.Retrofit.RetrofitClient;
import com.kokonut.NCNC.Retrofit.ReviewContents;
import com.kokonut.NCNC.Retrofit.ReviewResponse;
import com.kokonut.NCNC.KakaoAdapter;
import com.kokonut.NCNC.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.kokonut.NCNC.KakaoAdapter.getInstance;

public class CarWashReviewActivity extends AppCompatActivity {
    ImageView ivBack, ivWriteReview;

    //List<ReviewContents.Content> reviewContents; // 리뷰 콘텐츠가 저장되는 리스트
    List<ReviewContents.Content> reviewList;
    private ReviewAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView mRecyclerView;

    String baseUrl = "http://3.131.33.128:8000/";
    Retrofit retrofit;


    public static final int sub = 1001;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_wash_review);
        initView();
        //fetchReview("1");
        // 이 안에 washer의 id를 넣으면 해당 세차장의 리뷰들을 가져옵니다.

        Intent intent = getIntent();
        String wash_id = intent.getExtras().getString("id");
        System.out.println("세차장 id :   " + wash_id);

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI= retrofit.create(RetrofitAPI.class);
        Call<ReviewContents> call = retrofitAPI.fetchReview(wash_id);
        call.enqueue(new Callback<ReviewContents>() {
            @Override
            public void onResponse(Call<ReviewContents> call, Response<ReviewContents> response) {

                ReviewContents reviewContents = response.body();
                reviewList = reviewContents.getReviews();

                mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_review);
                mLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
                mRecyclerView.setLayoutManager(mLinearLayoutManager);
                mAdapter = new ReviewAdapter(reviewList);
                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(Call<ReviewContents> call, Throwable t) {

            }
        });

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
                if(!KakaoAdapter.getInstance().isLogin()){
                    Toast.makeText(getApplicationContext(),"로그인이 필요합니다.",Toast.LENGTH_SHORT).show();
                    return;
                }
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


//    public void fetchReview(String washerId){
//        RetrofitAPI retrofitAPI = RetrofitClient.getClient().create(RetrofitAPI.class);
//
//        retrofitAPI.fetchReview(washerId).enqueue(new Callback<ReviewContents>() {
//            @Override
//            public void onResponse(Call<ReviewContents> call, Response<ReviewContents> response) {
//                Log.d("fetch_review", "Success: "+new Gson().toJson(response.body().getReviews().get(0).getContent()));
//                reviewContents = response.body().getReviews();
//            }
//
//            @Override
//            public void onFailure(Call<ReviewContents> call, Throwable t) {
//                Log.e("fetch_review", "failure: "+t.toString());
//            }
//        });
//    }
}