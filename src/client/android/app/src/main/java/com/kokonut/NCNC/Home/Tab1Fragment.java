package com.kokonut.NCNC.Home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.kokonut.NCNC.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Tab1Fragment extends Fragment implements ActivityCompat.OnRequestPermissionsResultCallback{
    private static final int PERMISSIONS_REQUEST_CODE = 100;

    // 앱을 실행하기 위해 필요한 퍼미션을 정의합니다.
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };


    //ImageView gpsMark;
    LinearLayout layout;
    TextView tvLocation;
    static int REQUEST_CODE = 1;
    Geocoder geocoder;

    ViewGroup viewGroup;
    ImageButton popupButton;

    ViewPager2 viewPager2;
    FragmentStateAdapter pagerAdapter;
    int VIEW_CNT=3;

    TextView date1, date2, date3, date4, date5, date6, date7;
    TextView todayScore, score1, score2, score3, score4, score5, score6, score7;

    public Tab1Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("11111", "onCreate: 1");

    }


    @Nullable @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_tab1, container, false);
        popupButton = viewGroup.findViewById(R.id.home_popupButton);
        //gpsMark = viewGroup.findViewById(R.id.tab1_mark_gps);
        layout = viewGroup.findViewById(R.id.tab1_layout);
        tvLocation = viewGroup.findViewById(R.id.tab1_tv_location);

        date1 = viewGroup.findViewById(R.id.home_tab1_day1);
        date2 = viewGroup.findViewById(R.id.home_tab1_day2);
        date3 = viewGroup.findViewById(R.id.home_tab1_day3);
        date4 = viewGroup.findViewById(R.id.home_tab1_day4);
        date5 = viewGroup.findViewById(R.id.home_tab1_day5);
        date6 = viewGroup.findViewById(R.id.home_tab1_day6);
        date7 = viewGroup.findViewById(R.id.home_tab1_day7);

        todayScore = viewGroup.findViewById(R.id.home_score);
        score1 = viewGroup.findViewById(R.id.home_tab1_day1_score);
        score2 = viewGroup.findViewById(R.id.home_tab1_day2_score);
        score3 = viewGroup.findViewById(R.id.home_tab1_day3_score);
        score4 = viewGroup.findViewById(R.id.home_tab1_day4_score);
        score5 = viewGroup.findViewById(R.id.home_tab1_day5_score);
        score6 = viewGroup.findViewById(R.id.home_tab1_day6_score);
        score7 = viewGroup.findViewById(R.id.home_tab1_day7_score);

/*
        //mainActivity에서 scores[] 받아옴
        String[] scores = this.getArguments().getStringArray("scores");
        //String[] scores = getArguments().getSerializable(scores);

        todayScore.setText(scores[0]);
        score1.setText(scores[1]);
        score2.setText(scores[2]);
        score3.setText(scores[3]);
        score4.setText(scores[4]);
        score5.setText(scores[5]);
        score6.setText(scores[6]);
        score7.setText(scores[7]);

 */


        int locationPermission = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);

        Log.d("11111", "onCreateView: 1");


        if ( locationPermission == PermissionChecker.PERMISSION_GRANTED) {
            // 이전에 사용자가 위치 접근 권한 허용한 경우
            Log.d("locationPermission", "PERMISSION_ACCEPTED");
            //Toast.makeText(getContext(), "위치 정보 접근 권한 허용 상태", Toast.LENGTH_LONG).show();
            getCurrentLocation();

        }else {
            Log.d("locationPermission", "PERMISSION_DENIED");
            // 이전에 사용자가 위치 접근 권한 거부한 경우
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), PERMISSIONS[0])) {
                //Toast.makeText(getContext(), "위치 정보 접근 권한 거부 상태", Toast.LENGTH_LONG).show();

            } else {
                // 사용자가 퍼미션 거부를 한 적이 없는 경우(앱 최초 접속시), '다시 묻지 않음 - 거부'한 경우
                // 요청 결과는 onRequestPermissionResult에서 수신.
                //Toast.makeText(getContext(), "위치 정보 접근 권한 없음", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions( getActivity(), PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
                getCurrentLocation();
            }
        }


        //'맞춤형 세차점수 설정하기' 버튼 클릭 시
        popupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("11111", "onCreateView: 2");

            }
        });

/*
        // 하얀색 네모 클릭 시 -> getCurrentLocation()에서 위도, 경도 받아옴
        gpsMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLocationPermissionGranted()){
                    Log.d("11111", "isLocationPermissionGranated if");
                    getCurrentLocation();
                }
            }
        });
*/

        //'내주변세차장' viewpager 구현
        viewPager2 = (ViewPager2)viewGroup.findViewById(R.id.tab1_viewpager);
        pagerAdapter = new Tab1CarWashInfo_Viewpager2Adapter(this);
        viewPager2.setAdapter(pagerAdapter);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager2.setOffscreenPageLimit(2);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if(positionOffsetPixels==0){
                    viewPager2.setCurrentItem(position);
                }
            }
            /* indicator 설정시 이용
            @Override
            public void onPageSelected(int position){
                super.onPageSelected(position);
                indicator.animatePageSelected(position%VIEW_CNT);
            }*/
        });

        //오늘로부터 일주일 날짜
        date1.setText(getDate(0)); //오늘
        date2.setText(getDate(1));
        date3.setText(getDate(2));
        date4.setText(getDate(3));
        date5.setText(getDate(4));
        date6.setText(getDate(5));
        date7.setText(getDate(6));

        return viewGroup;
    }

    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("11111", "onRequestPermissionsResult in");
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(getContext(), R.string.no_permission_msg, Toast.LENGTH_SHORT).show();
            }
        }
    }

    //위도,경도 -> 시,구,동 변환
    private void GetAddress(Double latitude, Double longitude){
        List<Address> address = null;
        geocoder = new Geocoder(getActivity(), Locale.getDefault());

        try {
            address = geocoder.getFromLocation(latitude, longitude, 1);
        }
        catch(IOException e){
            e.printStackTrace();
            Log.d("getaddress","input/output error");
        }

        if(!address.isEmpty()){
            String str1 = address.get(0).getAdminArea(); //시
            String str2 = address.get(0).getSubLocality(); //구
            String str3 = address.get(0).getThoroughfare(); //동
            tvLocation.setText(str1+" "+str2+" "+str3);
        }


    }


    public void getCurrentLocation() {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // location.getLatitude()로 위도, locaation.getLongitude()로 경도 받아옴!
                         if (location != null) {
                             //tvLocation.setText("위도 " + location.getLatitude() + ", 경도 " + location.getLongitude());
                             GetAddress(location.getLatitude(), location.getLongitude());
                         }
                         else{
                             tvLocation.setText("위치를 알 수 없습니다.");
                         }
                    }
                });
    }

    private boolean isLocationPermissionGranted() {
        SharedPreferences preference = getActivity().getPreferences(Context.MODE_PRIVATE);
        boolean isFirstCheck = preference.getBoolean("isFirstPermissionCheck", true);
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                // 위치 필요한데 위치 접근 거부했을 때 권한이 필요하다고 TOAST 띄우기
                Snackbar snackBar = Snackbar.make(layout, R.string.suggest_permissison_grant, Snackbar.LENGTH_INDEFINITE);
                snackBar.setAction("권한 승인", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityCompat.requestPermissions(getActivity()
                                , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}
                                , REQUEST_CODE);
                    }
                });
                snackBar.show();
            } else {
                if (isFirstCheck) {
                    // 처음 물었는지 여부를 저장
                    preference.edit().putBoolean("isFirstPermissionCheck", false).apply();
                    // 권한 요청
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
                } else {
                    // 사용자가 권한을 거부하면서 다시 묻지않음 옵션을 선택한 경우
                    // requestPermission을 요청해도 창이 나타나지 않기 때문에 설정창으로 이동한다.
                    Snackbar snackBar = Snackbar.make(layout, R.string.suggest_permissison_grant_in_setting, Snackbar.LENGTH_INDEFINITE);
                    snackBar.setAction("확인", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getContext().getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    });
                    snackBar.show();
                }
            }
            return false;
        } else {
            return true;
        }
    }

    public static String getDate(int weekday){
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("dd"); //yyyyMMdd:20200801
        Calendar calendar = Calendar.getInstance(); //현재 날짜
        //calendar.set(Calendar.DAY_OF_WEEK, weekday); //오늘 포함 일주일일때
        calendar.add(Calendar.DAY_OF_MONTH, weekday); //오늘로부터 일주일일때
        String day = format.format(calendar.getTime());
        return day;
    }

}