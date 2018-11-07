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


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mContext=getActivity();
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return initView();
    }

    protected View initView() {
        View view = View.inflate(mContext,R.layout.fragment_weather,null);
        TextView country = (TextView) view.findViewById(R.id.country);
        TextView province = (TextView) view.findViewById(R.id.province);
        TextView city = (TextView) view.findViewById(R.id.city);
        TextView distract = (TextView) view.findViewById(R.id.distract);
        TextView cond_txt = (TextView) view.findViewById(R.id.cond_txt);
        TextView tmp = (TextView) view.findViewById(R.id.tmp);
        TextView wind_dir = (TextView) view.findViewById(R.id.wind_dir);
        TextView cloud = (TextView) view.findViewById(R.id.cloud);
        TextView _wind_sc= (TextView) view.findViewById(R.id._wind_sc);
        TextView brf = (TextView) view.findViewById(R.id.brf);
        TextView txt = (TextView) view.findViewById(R.id.txt);
        country.setText(Location.country);
        province.setText(Location.province);
        city.setText(Location.city);
        distract.setText(Location.distract);
        cond_txt.setText(Location.cond_txt);
        tmp.setText(Location.tmp);
        wind_dir.setText(Location.wind_dir);
        cloud.setText(Location.cloud);
        _wind_sc.setText(Location._wind_sc);
        brf.setText(Location.brf);
        txt.setText(Location.txt);
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

}




