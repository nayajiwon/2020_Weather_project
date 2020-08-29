package com.kokonut.NCNC.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kokonut.NCNC.Map.CarWashReviewActivity;
import com.kokonut.NCNC.R;

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
}
