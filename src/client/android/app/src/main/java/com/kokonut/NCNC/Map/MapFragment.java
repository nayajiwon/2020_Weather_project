package com.kokonut.NCNC.Map;

import com.kokonut.NCNC.GpsTracker;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.kokonut.NCNC.R;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationSource;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.NaverMapSdk;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.LocationOverlay;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.widget.LocationButtonView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;
import static android.content.Context.LOCATION_SERVICE;

public class MapFragment extends Fragment implements OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
        LocationListener, LocationSource {

    private GpsTracker gpsTracker;

    public static final int sub = 1001;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    LinearLayout llMoreInfo;
    ImageButton ibMarkGPS;
    private MapView mapView;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private NaverMap naverMap;

    Location newLocation = null;
    private FusedLocationSource locationSource;
    private LocationListener locationListener;
    @Nullable
    private LocationManager locationManager;
    @Nullable
    private LocationSource.OnLocationChangedListener listener;


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
        ibMarkGPS = (ImageButton) viewGroup.findViewById(R.id.map_mark_gps);

        Log.d(TAG,"onCreate");
        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);
        locationManager = (LocationManager)getActivity().getSystemService(LOCATION_SERVICE);

        mapView.getMapAsync(this);

        ibMarkGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gpsTracker = new GpsTracker(getContext());

                double latitude = gpsTracker.getLatitude();
                double longitude = gpsTracker.getLongitude();

                Toast.makeText(getContext(), "현재위치 \n위도 " + latitude + "\n경도 " + longitude, Toast.LENGTH_LONG).show();
                CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(latitude, longitude)).animate(CameraAnimation.Easing);;
                naverMap.moveCamera(cameraUpdate);
            }
        });

        llMoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CarWashInfoActivity.class);
                startActivityForResult(intent, sub);
            }
        });

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
        this.naverMap = naverMap;
        // GPS 허용할건지 묻는 팝업창
        naverMap.setContentPadding(0, 0, 0, 350);
        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
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

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,  @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated()) { // Permission denied
                naverMap.setLocationTrackingMode(LocationTrackingMode.None);
            }
            return;
        }
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);
    }

    @Override
    public void activate(
            @NonNull LocationSource.OnLocationChangedListener listener) {
        if (locationManager == null) {
            return;
        }

        if (PermissionChecker.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PermissionChecker.PERMISSION_GRANTED
                && PermissionChecker.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PermissionChecker.PERMISSION_GRANTED) {
            // Permission request logic omitted.
            return;
        }

        this.listener = listener;
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 1000, 10, this);
    }

    @Override
    public void deactivate() {
        if (locationManager == null) {
            return;
        }

        listener = null;
        locationManager.removeUpdates(this);
    }


    @Override
    public void onLocationChanged(Location location) {
        if (listener != null) {
            listener.onLocationChanged(location);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    private boolean hasPermission() {
        return PermissionChecker.checkSelfPermission(getContext(), PERMISSIONS[0])
                == PermissionChecker.PERMISSION_GRANTED
                && PermissionChecker.checkSelfPermission(getContext(), PERMISSIONS[1])
                == PermissionChecker.PERMISSION_GRANTED;
    }
}
