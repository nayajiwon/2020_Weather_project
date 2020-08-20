package com.kokonut.NCNC;

import android.database.Cursor;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.os.Build;
import android.os.Bundle;

import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.google.android.material.tabs.TabLayout;

import com.google.gson.Gson;


import com.kokonut.NCNC.Calendar.CalendarDBHelper;

import com.kokonut.NCNC.Calendar.CalendarFragment;
import com.kokonut.NCNC.Calendar.Calendar_PopupFragment;
//import com.kokonut.NCNC.Cast.CastFragment;
import com.kokonut.NCNC.Home.HomeFragment;
import com.kokonut.NCNC.Home.Tab1Fragment;
import com.kokonut.NCNC.Map.MapFragment;
import com.kokonut.NCNC.MyPage.MypageFragment;
import com.kokonut.NCNC.UsingScoreData;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements Calendar_PopupFragment.uploadDialogInterface{

    private Retrofit retrofit;
    private ScoreInterface scoreInterface;
    private RetrofitClient retrofitClient;
    private Gson mGson;
    FragmentTransaction fragmentTransaction;

    HomeFragment homeFragment;
    CalendarFragment calendarFragment;
    MapFragment mapFragment;
    //CastFragment castFragment;
    MypageFragment mypageFragment;


    UsingScoreData usingScoreData;
    Tab1Fragment tab1Fragment;


    BottomNavigationView bottomNavigationBar;


    TabLayout tabLayout;
    ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView textView;
        //textView = findViewById(R.id.mainText);
        //Bundle bundle = new Bundle();

        //서버 통신
        retrofitClient = new RetrofitClient();
        scoreInterface = retrofitClient.getClient().create(ScoreInterface.class);
        scoreInterface.fetchScore().enqueue(new Callback<ScoreContents>() {
            @Override
            public void onResponse(Call<ScoreContents> call, Response<ScoreContents> response) {
                Log.d("Score_ServerCall", "success");
                List<ScoreContents.Content> mlist = response.body().getContents();
                //textView.setText(mlist.toString());
                /*
                List<ScoreContents.Content> mlist = response.body().getContents();
                usingScoreData = new UsingScoreData(mmlist); //contents list 넘겨줌
                //List<String> scoreList = usingScoreData.getScoreList(); //score만 들어있는 list
                String[] scores = usingScoreData.getScoreArr(); //score 들어있는 array

                for(int i=0; i<scores.length; i++){
                    Log.println(1,"Response_Score",scores[i]);
                }

                 */


                //Tab1Fragment로 scores 전달
                //bundle.putStringArray("scores", scores);
                //tab1Fragment.setArguments(bundle);
                //fragmentTransaction = getSupportFragmentManager().beginTransaction();



            }

            @Override
            public void onFailure(Call<ScoreContents> call, Throwable t) {
                Log.e("Score_ServerCall", "failure");
            }
        });



        viewPager2 = findViewById(R.id.home_viewpager2);
        tabLayout = findViewById(R.id.home_tablayout);
        bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationBar.getChildAt(0);

        for (int i = 0; i < menuView.getChildCount(); i++) {
            final View iconView = menuView.getChildAt(i).findViewById(R.id.icon);
            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            iconView.setLayoutParams(layoutParams);
        }

        homeFragment = new HomeFragment();
        calendarFragment = new CalendarFragment();
        mapFragment = new MapFragment();
        //castFragment = new CastFragment();
        mypageFragment = new MypageFragment();

        //첫 화면 HomeFragment로 설정
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_layout, homeFragment).commit();


        bottomNavigationBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.tab1: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,homeFragment).commitAllowingStateLoss();
                        return true;
                    }
                    case R.id.tab2: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,calendarFragment).commitAllowingStateLoss();
                        return true;
                    }
                    case R.id.tab3: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,mapFragment).commitAllowingStateLoss();
                        return true;
                    }
                    /*
                    case R.id.tab4: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,castFragment).commitAllowingStateLoss();
                        return true;
                    }*/
                    case R.id.tab5: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,mypageFragment).commitAllowingStateLoss();
                        return true;
                    }

                    //default: return false;
                }
                return true;
            }
        });
    }


    @Override
    public void senddatatoCalendarFragment(int popupResult) {
        Log.d("senddatdatoCael", "senddatatoCalendarFragment: "+ popupResult);
        if (popupResult != 4) {//내부세차 외부세차 리스트일 경우
            calendarFragment.devidepopupValue(popupResult);
        }
        else if (popupResult == 4) {
            Log.d("((((((TAG))))))))))))))", "senddatatoCalendarFragment: ");
            calendarFragment.removeCustomDecorator(popupResult);
        }
    }

}
