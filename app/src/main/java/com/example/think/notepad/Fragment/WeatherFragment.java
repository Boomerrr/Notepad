package com.example.think.notepad.Fragment;

import android.content.Context;
import android.os.Bundle;


import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.example.think.notepad.Activity.WorkActivity;
import com.example.think.notepad.Base.BaseFragment;
import com.example.think.notepad.Bean.LocationBean;
import com.example.think.notepad.Bean.NowLifestyle;
import com.example.think.notepad.Bean.NowTmp;
import com.example.think.notepad.Location;
import com.example.think.notepad.R;
import com.example.think.notepad.Thread.NowLifestyleThread;
import com.example.think.notepad.Thread.NowTmpThread;

import org.w3c.dom.Text;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

/*
* Create by Boomerr Yi   2018/11/5
*
* */
public class WeatherFragment extends BaseFragment {

    private TextView country ;
    private TextView province ;
    private TextView city ;
    private TextView distract ;
    private TextView cond_txt ;
    private TextView tmp ;
    private TextView wind_dir ;
    private TextView cloud ;
    private TextView _wind_sc;
    private TextView brf ;
    private TextView txt ;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mContext=getActivity();
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return initView();
    }

    protected View initView() {
        View view = View.inflate(mContext,R.layout.fragment_weather,null);
         country = (TextView) view.findViewById(R.id.country);
         province = (TextView) view.findViewById(R.id.province);
         city = (TextView) view.findViewById(R.id.city);
         distract = (TextView) view.findViewById(R.id.distract);
         cond_txt = (TextView) view.findViewById(R.id.cond_txt);
         tmp = (TextView) view.findViewById(R.id.tmp);
         wind_dir = (TextView) view.findViewById(R.id.wind_dir);
         cloud = (TextView) view.findViewById(R.id.cloud);
         _wind_sc= (TextView) view.findViewById(R.id._wind_sc);
         brf = (TextView) view.findViewById(R.id.brf);
         txt = (TextView) view.findViewById(R.id.txt);
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String a = Location.country;
                final String b = Location.province;
                final String c = Location.city;
                final String d = Location.distract;
                final String e = Location.cond_txt;
                final String f = "温度："+ Location.tmp;
                final String g = "风向："+ Location.wind_dir;
                final String h = "云量："+ Location.cloud;
                final String i = "风力："+ Location._wind_sc;
                final String j = Location.brf;
                final String k = Location.txt;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        country.setText(a);
                        province.setText(b);
                        city.setText(c);
                        distract.setText(d);
                        cond_txt.setText(e);
                        tmp.setText(f);
                        wind_dir.setText(g);
                        cloud.setText(h);
                        _wind_sc.setText(i);
                        brf.setText(j);
                        txt.setText(k);
                    }
                });
            }
        }).start();

        CircleImageView headImage = (CircleImageView) view.findViewById(R.id.head_image);
        headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkActivity workActivity = (WorkActivity)getActivity();
                DrawerLayout drawerLayout =  workActivity.findViewById(R.id.drawablelayout);
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        return view;
    }



    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}




