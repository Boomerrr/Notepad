package com.example.think.notepad.Fragment;

import android.content.Context;
import android.os.Bundle;


import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.example.think.notepad.Base.BaseFragment;
import com.example.think.notepad.Bean.LocationBean;
import com.example.think.notepad.Bean.NowLifestyle;
import com.example.think.notepad.Bean.NowTmp;
import com.example.think.notepad.R;
import com.example.think.notepad.Thread.NowLifestyleThread;
import com.example.think.notepad.Thread.NowTmpThread;

import org.w3c.dom.Text;

import java.util.Objects;

/*
*
* Create by Boomerr Yi
* 这个fragment主要是为了对当前的天气状况进行一个表达
* 主要运用了百度地图的定位功能
* 以及和风天气的天气播报功能，由于和风天气的接口限制原因，每天对于天气预测的访问次数有限
*
* */
public class WeatherFragment extends BaseFragment {
    Handler handler1 = new Handler(){
        @Override
        public  void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            TextView loaction = getView().findViewById(R.id.locationText);
            loaction.setText( bundle.getString("tmp"));
        }
    };
    Handler handler2 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
        }
    };

    public LocationClient mLocationClient;
    protected Context mContext;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mContext=getActivity();
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return initView();
    }

    protected View initView() {
        View view = View.inflate(mContext,R.layout.fragment_weather,null);
        mLocationClient=new LocationClient(mContext);//获取定位对象
        mLocationClient.registerLocationListener(new MyLocationListener());
        SDKInitializer.initialize(Objects.requireNonNull(getActivity()).getApplicationContext());
        TextView Location = (TextView) view.findViewById(R.id.locationText);
        requestLocation();
        return view;
    }


    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
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
    public void onDestroy(){
        super.onDestroy();
        mLocationClient.stop();
    }
    public class MyLocationListener implements BDLocationListener{

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
            locationBean.setProvince(bdLocation.getProvince());
            locationBean.setCity(bdLocation.getCity());
            locationBean.setDistract(bdLocation.getDistrict());
            String distract = bdLocation.getDistrict();
            Log.e("test----Boomerr",distract);
            NowTmpThread nowTmpThread = new NowTmpThread(distract,handler1);
            nowTmpThread.run();
            NowLifestyleThread nowLifestyleThread = new NowLifestyleThread(distract,handler2);
            nowLifestyleThread.run();

        }


    }
}



