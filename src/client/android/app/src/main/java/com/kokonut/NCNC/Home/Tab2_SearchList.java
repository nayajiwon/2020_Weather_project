package com.kokonut.NCNC.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.kokonut.NCNC.R;

public class Tab2_SearchList extends AppCompatActivity {
    ImageButton tab2_prevButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab2__search_list);

        //뒤로버튼 클릭시
        tab2_prevButton = findViewById(R.id.tab2_searchlist_back_arrow);
        tab2_prevButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}