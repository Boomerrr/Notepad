package com.example.think.notepad.Activity;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

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
        mContext = getApplicationContext();
        mLocationClient = new LocationClient(mContext);//获取定位对象
        mLocationClient.registerLocationListener(new MyLocationListener());
        requestLocation();
        SDKInitializer.initialize(Objects.requireNonNull(getApplication()).getApplicationContext());
        //读取所有已存信息文件 第一行为标题，第二行为时间 第三行为内容
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startMainActivity();
            }
        }, 1500);
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
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Location location = (Location) getApplication();
                    location.notepadArrayList = read();
                }
            });
        }

    }




    public ArrayList<NotePad> read() {
        ArrayList<NotePad> list = new ArrayList<>();
        try {
            FileInputStream fileInputStream = openFileInput(FILE_NAME);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while(true){
                NotePad notePad = new NotePad();
                if((line = bufferedReader.readLine())!= null) {
                    notePad.setTitle(line);
                    Log.e("Boomerr---test",line);
                }else{
                    break;
                }
                if((line = bufferedReader.readLine())!=null) {
                    notePad.setOrderTime(line);
                    Log.e("Boomerr---test",line);
                }else{
                    break;
                }
                if((line = bufferedReader.readLine())!=null) {
                    notePad.setText(line);
                    Log.e("Boomerr---test",line);
                }else{
                    break;
                }
                list.add(notePad);
            }
            return list;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}

