package com.sitp2017.hujiepingtai6;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gameben on 2017/5/8.
 */
public class MyFragment extends Fragment { //决定每个MainPage中的Pager的布局
    public LocationClient mLocationClient;
    private MapView mapView;
    private BaiduMap baiduMap;
    private boolean isFirstLocate = true;
    private int timer = 0;
    BitmapDescriptor bitmap;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_layout, container, false);
        TextView pagerTitle = (TextView) view.findViewById(R.id.pager_title);
        pagerTitle.setText(getArguments().getString("param") + "地图信息");
        mapView = (MapView)view.findViewById(R.id.map_view);

        if (!getArguments().getString("param").equals("充电宝")) {
            pagerTitle.setVisibility(View.VISIBLE);
            mapView.setVisibility(View.INVISIBLE);
            return view;
        }

        bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_mark);

        mLocationClient = new LocationClient(getActivity().getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());

        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String [] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(getActivity(), permissions, 1);
        } else {
            requestLocation();
        }
        return view;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(getActivity(), "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(getActivity(), "发生未知错误", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
                break;
            default:
        }
    }


    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        mLocationClient.setLocOption(option);
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
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
        bitmap.recycle();
    }

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location.getLocType() == BDLocation.TypeGpsLocation || location.getLocType() == BDLocation.TypeNetWorkLocation) {
                if (timer % 16 == 0) {
                    zoomToDefault(location);
                    if (isFirstLocate) {
                        updateMarkers();
                        isFirstLocate = false;
                    }
                    timer = 0;
                }

                updateMyPosition(location);
                MyApplication.getInstance().setMyLocation(location);
                timer++;
            }
        }
    }
    public void zoomToDefault(BDLocation location) {
        LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
        MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
        baiduMap.setMapStatus(update);
        update = MapStatusUpdateFactory.zoomTo(17f);
        baiduMap.setMapStatus(update);
    }

    public void updateMyPosition(BDLocation location) {
        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        locationBuilder.latitude(location.getLatitude());
        locationBuilder.longitude(location.getLongitude());
        MyLocationData locationData = locationBuilder.build();
        baiduMap.setMyLocationData(locationData);
        if (timer % 4 == 0) {
            Toast.makeText(getActivity(), "nav to " + location.getAddrStr(), Toast.LENGTH_SHORT).show();
            if (!SQLService.checkUserLocation(MyApplication.getInstance().customer.getPhoneNumber())) {
                SQLService.insertUserLocation(location.getLatitude(), location.getLongitude());
            } else {
                SQLService.updateUserLocation(location.getLatitude(), location.getLongitude());
            }
        }
    }

    public void updateMarkers() {
        baiduMap.clear();
        List<OverlayOptions> options = new ArrayList<OverlayOptions>();
        for (UserLocation ul : MyApplication.userLocations) {
            boolean flag = false;
            for (Demand d : MyApplication.demands) {
                if (ul.getPhoneNumber().equals(d.getPostPhoneNumber())) {
                    flag = true;
//                    Log.d("Map ", ul.getPhoneNumber());
                }
            }
            if (flag) {
                OverlayOptions option = new MarkerOptions().position(ul.toLatLng()).icon(bitmap);
                options.add(option);
            }
        }
        baiduMap.addOverlays(options);

        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker arg0) {
                Toast.makeText(getActivity().getApplicationContext(), "出借充电宝！", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}

