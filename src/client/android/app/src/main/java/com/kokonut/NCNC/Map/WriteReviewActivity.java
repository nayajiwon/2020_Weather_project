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
import com.kokonut.NCNC.Retrofit.RetrofitAPI;
import com.kokonut.NCNC.Retrofit.RetrofitClient;
import com.kokonut.NCNC.Retrofit.ReviewResponse;
import com.kokonut.NCNC.KakaoAdapter;
import com.kokonut.NCNC.Map.CarWashReviewActivity;
import com.kokonut.NCNC.R;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WriteReviewActivity extends AppCompatActivity {
    ImageView ivBack, ivCommitReview;

    public static final int sub = 1001;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);
        initView();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CarWashReviewActivity.class);
                startActivityForResult(intent, sub);
            }
        });

        ivCommitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CarWashReviewActivity.class);
                startActivityForResult(intent, sub);
            }
        });

    }

    protected void onPause() {
        super.onPause();
        finish();
    }


    void initView(){
        ivBack = findViewById(R.id.write_review_back_arrow);
        ivCommitReview = findViewById(R.id.write_review_commit_button);

    }

    public void writeReview(String washerId, String content){
        if(!KakaoAdapter.getInstance().isLogin()){
            Toast.makeText(getApplicationContext(),"로그인이 필요합니다.",Toast.LENGTH_SHORT);
            return;
        }

        RetrofitAPI retrofitAPI = RetrofitClient.getClient().create(RetrofitAPI.class);
        String id = Long.toString(KakaoAdapter.getInstance().getUser().getId());
        HashMap<String,String> param = new HashMap<>();
        param.put("id",id);
        param.put("content",content);

        retrofitAPI.writeReview(washerId, param).enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                Log.d("user_check", "Success: "+new Gson().toJson(response.body().getStatus()));

            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                Log.e("user_check", "failure: "+t.toString());
            }
        });
    }
}