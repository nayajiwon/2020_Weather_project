package com.kokonut.NCNC.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kokonut.NCNC.Map.CarWashInfoActivity;
import com.kokonut.NCNC.R;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private MapView mapView;
    LinearLayout llMoreInfo;
    BottomNavigationView bottomNavigation;


    public static final int sub = 1001;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        bottomNavigation = findViewById(R.id.bottom_navigation_bar);
        initView();

        mapView = findViewById(R.id.map_view);
        mapView.getMapAsync(this);

        llMoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CarWashInfoActivity.class);
                startActivityForResult(intent, sub);
            }
        });

    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        Marker marker1 = new Marker();
        Marker marker2 = new Marker();
        Marker selectedMarker = new Marker();

        OverlayImage markerImage = OverlayImage.fromResource(R.drawable.map_mark_black);
        OverlayImage selectedMarkerImage = OverlayImage.fromResource(R.drawable.map_mark_blue);
        OverlayImage image = OverlayImage.fromResource(R.drawable.map_box_white);

        marker1.setPosition(new LatLng(37.5670135, 126.9783740));
        marker1.setIcon(markerImage);
        marker1.setCaptionText("쓱싹쓱싹 숭실대입구점");
        marker1.setCaptionRequestedWidth(200);
        marker1.setMap(naverMap);

        marker2.setPosition(new LatLng(37.5570135, 126.9683740));
        marker2.setIcon(markerImage);
        marker2.setCaptionText("세차장닷컴");
        marker2.setCaptionRequestedWidth(200);
        marker2.setMap(naverMap);

        selectedMarker.setPosition(new LatLng(37.5870135, 126.9753740));
        selectedMarker.setWidth(80);
        selectedMarker.setHeight(112);
        selectedMarker.setIcon(selectedMarkerImage);
        selectedMarker.setCaptionText("세차하기 좋은 날");
        selectedMarker.setCaptionRequestedWidth(200);
        selectedMarker.setMap(naverMap);

    }

    protected void onPause() {
        super.onPause();
        finish();
    }

    void initView(){
        llMoreInfo = findViewById(R.id.map_more_info);
    }

}
