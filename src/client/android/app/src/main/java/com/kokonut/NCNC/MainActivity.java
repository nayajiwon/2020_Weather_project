package com.kokonut.NCNC;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;


import com.kokonut.NCNC.Calendar.CalendarFragment;
import com.kokonut.NCNC.Calendar.Calendar_PopupFragment;
import com.kokonut.NCNC.Cast.CastFragment;
import com.kokonut.NCNC.Home.HomeFragment;
import com.kokonut.NCNC.Home.SharedViewModel;
import com.kokonut.NCNC.Map.MapFragment;
import com.kokonut.NCNC.MyPage.MypageFragment;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

public class MainActivity extends AppCompatActivity implements Calendar_PopupFragment.uploadDialogInterface{

    ViewModelProvider.Factory viewModelFactory;
    private SharedViewModel sharedViewModel;

    HomeFragment homeFragment;
    CalendarFragment calendarFragment;
    MapFragment mapFragment;
    CastFragment castFragment;
    MypageFragment mypageFragment;

    BottomNavigationView bottomNavigationBar;


    TabLayout tabLayout;
    ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = new Bundle();

        /*//서버 통신 - 세차점수
        scoreInterface = RetrofitClient.getClient().create(ScoreInterface.class);
        scoreInterface.fetchScore().enqueue(new Callback<ScoreContents>() {
            @Override
            public void onResponse(Call<ScoreContents> call, Response<ScoreContents> response) {
                Log.d("Retrofit_Score", "Success: "+new Gson().toJson(response.body().getContents()));

                List<ScoreContents.Content> mlist = response.body().getContents();
                if(mlist.isEmpty()){ //서버에 해당정보 없을 때
                    Log.e("Retrofit_Score", "NULL");
                }
                else{
                    //usingScoreData = new UsingScoreData(mlist);
                    scoreList= new String[8]; //초기화
                    for(int i=0; i<7; i++){
                        scoreList[i] = makeScoreList(mlist.get(i).getRnLv(), mlist.get(i).getTaLv());
                        //Log.d("scoreList", scoreList[i]);
                    }
                    //dataToTab1Fragment = new DataToTab1Fragment(scoreList);

                    bundle.putString("score","hi");
                    homeFragment.setArguments(bundle);

                    //intent = new Intent(getApplication(), Tab1Fragment.class);
                    //intent.putExtra("DataToTab1Fragment", dataToTab1Fragment);
                    //intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //startActivity(intent);

                    //bundle.putParcelable("DataToTab1Fragment", dataToTab1Fragment);
                    //bundle.putStringArray("score",scoreList);
                    //tab1Fragment.setArguments(bundle);
                }


                /*
                List<ScoreContents.Content> mlist = response.body().getContents();
                usingScoreData = new UsingScoreData(mmlist); //contents list 넘겨줌
                //List<String> scoreList = usingScoreData.getScoreList(); //score만 들어있는 list
                String[] scores = usingScoreData.getScoreArr(); //score 들어있는 array

                for(int i=0; i<scores.length; i++){
                    Log.println(1,"Response_Score",scores[i]);
                }




                //Tab1Fragment로 scores 전달
                //Bundle bundle = new Bundle();
                //bundle.putStringArray("scoreList", scoreList);
                //tab1Fragment.setArguments(bundle);
                //fragmentTransaction = getSupportFragmentManager().beginTransaction();



            }

            @Override
            public void onFailure(Call<ScoreContents> call, Throwable t) {
                Log.e("Retrofit_Score", "failure: "+t.toString());
            }
        });
*/



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
        castFragment = new CastFragment();
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
                    }/*
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
        if (popupResult != 4) //내부세차 외부세차 리스트일 경우
            calendarFragment.devidepopupValue(popupResult);
        else if (popupResult == 4)
            Log.d("((((((TAG))))))))))))))", "senddatatoCalendarFragment: ");
            calendarFragment.removeCustomDecorator(popupResult);
    }
}
