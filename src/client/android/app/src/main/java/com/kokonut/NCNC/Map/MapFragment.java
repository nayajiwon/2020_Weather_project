package com.kokonut.NCNC.Map;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kokonut.NCNC.CarWashAdapter;
import com.kokonut.NCNC.CarWashInfoData;
import com.kokonut.NCNC.GpsTracker;
import com.kokonut.NCNC.R;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationSource;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static android.content.Context.LOCATION_SERVICE;

public class MapFragment extends Fragment implements OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
        LocationListener, LocationSource {

    private GpsTracker gpsTracker;
    private static String IP_ADDRESS = "52.26.131.225";
    private static String TAG = "phptest";
    public static final int sub = 1001;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    LinearLayout llMoreInfo;
    ImageButton ibMarkGPS;
    private MapView mapView;
    Marker selectedMarker;

    private ArrayList<CarWashInfoData> carWashList;
    private ArrayList<CarWashInfoData> mArrayList;
    private ArrayList<Marker> markerArrayList = new ArrayList();

    private CarWashAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private String mJsonString;

    private NaverMap naverMap;

    private FusedLocationSource locationSource;
    @Nullable
    private LocationManager locationManager;
    @Nullable
    private LocationSource.OnLocationChangedListener listener;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.d(TAG, "----------OnCreate--------");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "----------OnCreateView--------");
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_map, container, false);

        mapView = (MapView)viewGroup.findViewById(R.id.map_view);
        ibMarkGPS = (ImageButton) viewGroup.findViewById(R.id.map_mark_gps);
        carWashList = new ArrayList<>();

        mRecyclerView = (RecyclerView) viewGroup.findViewById(R.id.listView_main_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mArrayList = new ArrayList<>();

        mAdapter = new CarWashAdapter(getActivity(), mArrayList);
        mRecyclerView.setAdapter(mAdapter);

        mArrayList.clear();
        mAdapter.notifyDataSetChanged();

        MapFragment.GetData task = new MapFragment.GetData();
        task.execute( "http://" + IP_ADDRESS + "/getjson.php", "");


        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);
        locationManager = (LocationManager)getActivity().getSystemService(LOCATION_SERVICE);
        selectedMarker = new Marker();

        mapView.getMapAsync(this);


        ibMarkGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gpsTracker = new GpsTracker(getContext());

                double latitude = gpsTracker.getLatitude();
                double longitude = gpsTracker.getLongitude();

//                Toast.makeText(getContext(), "현재위치 \n위도 " + latitude + "\n경도 " + longitude, Toast.LENGTH_LONG).show();
                CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(latitude, longitude)).animate(CameraAnimation.Easing);;
                naverMap.moveCamera(cameraUpdate);
            }
        });

        return viewGroup;

    }


    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
        Log.d(TAG, "----------OnStart--------");
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
        Log.d(TAG, "----------OnActivityCreated - mapView Create --------");
        super.onActivityCreated(savedInstanceState);

        //액티비티가 처음 생성될 때 실행되는 함수
        if(mapView != null)
        {
            mapView.onCreate(savedInstanceState);
        }
    }


    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        Log.d(TAG, "----------OnMapReady--------");
        int totalElements = carWashList.size();
        if(totalElements == 0){
            Log.d(TAG, "----------OnMapReady Init--------");
        }
        else{
            Log.d(TAG, "----------showCarWashList--------");
            for (int index = 0; index < totalElements; index++) {
                System.out.println(carWashList.get(index).getMember_id());
                System.out.println(carWashList.get(index).getMember_name());
                System.out.println(carWashList.get(index).getMember_latitude());
                System.out.println(carWashList.get(index).getMember_longitude());
                System.out.println();
            }
        }
        this.naverMap = naverMap;
        // GPS 허용할건지 묻는 팝업창
        naverMap.setContentPadding(0, 0, 0, 350);
        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);

        OverlayImage markerImage = OverlayImage.fromResource(R.drawable.map_mark_black);
        OverlayImage selectedMarkerImage = OverlayImage.fromResource(R.drawable.map_mark_blue);

        for (int index = 0; index < totalElements; index++) {
            Marker marker = new Marker();
            marker.setPosition(new LatLng(Double.parseDouble(carWashList.get(index).getMember_latitude()),
                    Double.parseDouble(carWashList.get(index).getMember_longitude())));
            marker.setIcon(markerImage);
            marker.setCaptionText(carWashList.get(index).getMember_name());
            marker.setCaptionRequestedWidth(200);
            marker.setMap(naverMap);
            markerArrayList.add(marker);

            marker.setOnClickListener(overlay -> {
                for(int i = 0; i < markerArrayList.size(); i++){
                    markerArrayList.get(i).setIcon(markerImage);
                }
                marker.setIcon(selectedMarkerImage);
                marker.setWidth(80);
                marker.setHeight(112);
                mArrayList.clear();
                mArrayList.add(carWashList.get(markerArrayList.indexOf(marker)));
                mAdapter.setmList(mArrayList);
                mAdapter.setOnItemClickListener(new CarWashAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Intent intent = new Intent(getContext(), CarWashInfoActivity.class);
                        startActivityForResult(intent, sub);
                    }
                }) ;

                mRecyclerView.setAdapter(mAdapter);

                return true;
            });
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,  @NonNull int[] grantResults) {
        Log.d(TAG, "----------OnRequestPermissionsResult--------");
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
        Log.d(TAG, "----------activate--------");

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


    private class GetData extends AsyncTask<String, Void, String>{

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(getActivity(),
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            //mTextViewResult.setText(result);
            Log.d(TAG, "response - " + result);

            if (result == null){

                //mTextViewResult.setText(errorString);
            }
            else {

                mJsonString = result;
                showLocation();
                //showResult();
                onMapReady(naverMap);
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];
            String postParameters = params[1];


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "GetData : Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }


        private void showResult(){

    //        String TAG_JSON="kokonut";
    //        String TAG_ID = "car_wash_id";
    //        String TAG_NAME = "car_wash_name";
    //        String TAG_LATITUDE = "latitude";
    //        String TAG_LONGITUDE = "longitude";

            String TAG_JSON="scsc";
            String TAG_ID="id";
            String TAG_LATITUDE = "lat";
            String TAG_LONGITUDE = "lon";
            String TAG_NAME = "name";
            String TAG_ADDRESS = "address";
            String TAG_PHONE = "phone";


            try {
                JSONObject jsonObject = new JSONObject(mJsonString);
                JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

    //            for(int i=0;i<jsonArray.length();i++){

                for(int i=0;i<1;i++){
                    JSONObject item = jsonArray.getJSONObject(i);

                    String id = item.getString(TAG_ID);
                    String latitude = item.getString(TAG_LATITUDE);
                    String longitude = item.getString(TAG_LONGITUDE);
                    String name = item.getString(TAG_NAME);
                    String address = item.getString(TAG_ADDRESS);
                    String phone = item.getString(TAG_PHONE);

                    CarWashInfoData carWashInfoData = new CarWashInfoData();

                    carWashInfoData.setMember_id(id);
                    carWashInfoData.setMember_longitude(longitude);
                    carWashInfoData.setMember_latitude(latitude);
                    carWashInfoData.setMember_name(name);
                    carWashInfoData.setMember_address(address);
                    carWashInfoData.setMember_phone(phone);

                    mArrayList.add(carWashInfoData);
                    mAdapter.notifyDataSetChanged();
                }



            } catch (JSONException e) {

                Log.d(TAG, "showResult : ", e);
            }

        }



        public void showLocation() {

            Log.d(TAG, "Show LocationTest");

            String TAG_JSON="scsc";
            String TAG_ID="id";
            String TAG_LATITUDE = "lat";
            String TAG_LONGITUDE = "lon";
            String TAG_NAME = "name";
            String TAG_ADDRESS = "address";
            String TAG_PHONE = "phone";


            try {
                JSONObject jsonObject = new JSONObject(mJsonString);
                JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

                for(int i=0;i<jsonArray.length();i++){
                    JSONObject item = jsonArray.getJSONObject(i);

                    String id = item.getString(TAG_ID);
                    String latitude = item.getString(TAG_LATITUDE);
                    String longitude = item.getString(TAG_LONGITUDE);
                    String name = item.getString(TAG_NAME);
                    String address = item.getString(TAG_ADDRESS);
                    String phone = item.getString(TAG_PHONE);

                    CarWashInfoData carWashInfoData = new CarWashInfoData();

                    carWashInfoData.setMember_id(id);
                    carWashInfoData.setMember_longitude(longitude);
                    carWashInfoData.setMember_latitude(latitude);
                    carWashInfoData.setMember_name(name);
                    carWashInfoData.setMember_address(address);
                    carWashInfoData.setMember_phone(phone);

                    carWashList.add(carWashInfoData);
                    mAdapter.notifyDataSetChanged();
                }



            } catch (JSONException e) {

                Log.d(TAG, "showResult : ", e);
            }


        }

}