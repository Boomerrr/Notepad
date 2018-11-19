package com.example.think.notepad.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.example.think.notepad.Bean.LocationBean;
import com.example.think.notepad.Bean.NotePad;
import com.example.think.notepad.Location;
import com.example.think.notepad.R;
import com.example.think.notepad.Thread.NowLifestyleThread;
import com.example.think.notepad.Thread.NowTmpThread;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import static com.example.think.notepad.Contracts.FILE_NAME;

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
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestPermission();
        mContext = getApplicationContext();
        mLocationClient = new LocationClient(mContext);//获取定位对象
        mLocationClient.registerLocationListener(new MyLocationListener());
        requestLocation();
        SDKInitializer.initialize(Objects.requireNonNull(getApplication()).getApplicationContext());
        Location location = (Location) getApplication();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
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
            final Location location = (Location) getApplication();
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
            location.country = bdLocation.getCountry();
            locationBean.setProvince(bdLocation.getProvince());
            location.province = bdLocation.getProvince();
            locationBean.setCity(bdLocation.getCity());
            location.city = bdLocation.getCity();
            locationBean.setDistract(bdLocation.getDistrict());
            location.distract = bdLocation.getDistrict();
            String distract = bdLocation.getDistrict();
            Log.e("test----Boomerr", distract);
            Handler handlerLifestyle = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    Bundle bundle = msg.getData();
                    String brf = bundle.getString("brf");
                    String txt = bundle.getString("txt");
                    location.brf = brf;
                    location.txt = txt;
                }
            };
            Handler handlerTmp = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    Bundle bundle = msg.getData();
                    location.cloud = bundle.getString("cloud");
                    location.wind_dir = bundle.getString("wind_dir");
                    location.tmp = bundle.getString("tmp");
                    location.cond_txt = bundle.getString("cond_txt");
                    location._wind_sc = bundle.getString("_wind_sc");
                }
            };
            new NowLifestyleThread(distract,handlerLifestyle).run();
            new NowTmpThread(distract,handlerTmp).run();
            startMainActivity();
        }

    }


    private void requestPermission() {
        List<String> permissionList=new ArrayList<>();
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED);{
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_PHONE_STATE)!=PackageManager.PERMISSION_GRANTED);{
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED);{
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!permissionList.isEmpty()){
            String[] permissions=permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(WelcomeActivity.this,permissions,1);
        }
    }


    public void onRequestPermissionResult(int requestCode,String[] permissions,int[] grantResults){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        }
        switch(requestCode){
            case 1:
                if(grantResults.length > 0){
                    for(int result : grantResults){
                        if(result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意权限才能使用此程序",Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                }else{
                    Toast.makeText(this,"发生未知错误",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }
}

