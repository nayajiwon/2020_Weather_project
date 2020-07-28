package com.kokonut.NCNC.Map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kokonut.NCNC.R;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapSdk;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.widget.LocationButtonView;

import static android.content.ContentValues.TAG;
import static android.content.Context.LOCATION_SERVICE;

public class MapFragment extends Fragment implements OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {
    public static final int sub = 1001;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSION_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSION = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};

    LinearLayout llMoreInfo;
    private MapView mapView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_map, container, false);

        mapView = (MapView)viewGroup.findViewById(R.id.map_view);
        llMoreInfo = (LinearLayout)viewGroup.findViewById(R.id.map_more_info);

        Log.d(TAG,"onCreate");


        mapView.getMapAsync(this);

        return viewGroup;

    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onLowMemory();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //액티비티가 처음 생성될 때 실행되는 함수

        if(mapView != null)
        {
            mapView.onCreate(savedInstanceState);
        }
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
}