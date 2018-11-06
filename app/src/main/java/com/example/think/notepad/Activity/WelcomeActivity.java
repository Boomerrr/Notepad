package com.example.think.notepad.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.example.think.notepad.Bean.LocationBean;
import com.example.think.notepad.Bean.NowTmp;
import com.example.think.notepad.Fragment.WeatherFragment;
import com.example.think.notepad.Location;
import com.example.think.notepad.R;
import com.example.think.notepad.Thread.NowLifestyleThread;
import com.example.think.notepad.Thread.NowTmpThread;

import java.util.Objects;

/*
*Create By Boomerr Yi 2018/11/5
* 欢迎界面 以一张图片为背景即可
* 完成权限授予 如果未能给予足够权限，则会退出登录
* 在这个界面还应该完成静默登录功能
*
*
* */
public class WelcomeActivity extends Activity {
    public LocationClient mLocationClient;
    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mLocationClient = new LocationClient(mContext);//获取定位对象
        mLocationClient.registerLocationListener(new MyLocationListener());
        requestLocation();
        SDKInitializer.initialize(Objects.requireNonNull(getApplication()).getApplicationContext());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startMainActivity();
            }
        }, 500);
    }

    private void startMainActivity() {
        Intent intent = new Intent(WelcomeActivity.this, LogInActivity.class);
        startActivity(intent);
        finish();
    }

    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

    public void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            StringBuilder currentPosition = new StringBuilder();
            currentPosition.append("纬度 ").append(bdLocation.getLatitude()).append("\n");
            currentPosition.append("经线 ").append(bdLocation.getLongitude()).append("\n");
            currentPosition.append("国家 ").append(bdLocation.getCountry()).append("\n");
            currentPosition.append("省 ").append(bdLocation.getProvince()).append("\n");
            currentPosition.append("市 ").append(bdLocation.getCityCode()).append("\n");
            currentPosition.append("区 ").append(bdLocation.getDistrict()).append("\n");
            currentPosition.append("街道 ").append(bdLocation.getStreet()).append("\n");
            LocationBean locationBean = new LocationBean();
            locationBean.setCountry(bdLocation.getCountry());
            Location.country = bdLocation.getCountry();
            locationBean.setProvince(bdLocation.getProvince());
            Location.province = bdLocation.getProvince();
            locationBean.setCity(bdLocation.getCity());
            Location.city = bdLocation.getCity();
            locationBean.setDistract(bdLocation.getDistrict());
            Location.distract = bdLocation.getDistrict();
            String distract = bdLocation.getDistrict();
            Log.e("test----Boomerr", distract);
            new NowLifestyleThread(distract,null).run();
            new NowTmpThread(distract,null).run();
        }

    }
}

