package com.kokonut.NCNC;

import android.os.Build;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.content.Context;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.google.android.material.tabs.TabLayout;

import com.kokonut.NCNC.Calendar.CalendarFragment;
import com.kokonut.NCNC.Calendar.Calendar_PopupFragment;
import com.kokonut.NCNC.Cast.CastFragment;
import com.kokonut.NCNC.Home.HomeFragment;
import com.kokonut.NCNC.Map.MapFragment;
import com.kokonut.NCNC.MyPage.MypageFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

public class MainActivity extends AppCompatActivity implements Calendar_PopupFragment.uploadDialogInterface{

    int forgithubtest; 
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
                    }
                    case R.id.tab4: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,castFragment).commitAllowingStateLoss();
                        return true;
                    }
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
    public void senddatatoCalendarFragment(String popupResult) {
        calendarFragment.devidepopupValue(popupResult);
    }


}
