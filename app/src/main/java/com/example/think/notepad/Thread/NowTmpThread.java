package com.example.think.notepad.Thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;

import com.example.think.notepad.Bean.NowTmp;
import com.example.think.notepad.Contracts;
import com.example.think.notepad.Location;
import com.example.think.notepad.Util.HttpUtil;
import com.example.think.notepad.Util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
/**
 *
 * Create by Boomerr Yi
 * 这个线程主要是对当前天气状况的获取
 * 由于接口的原因，天气状况和生活指数只能在两个线程里面进行分别获取
 */

public class NowTmpThread implements Runnable {
    private String distract;
    private Handler handler;
    public NowTmpThread(String distract, Handler handler){
        this.distract = distract;
       // this.handler = handler;
    }
    @Override
    public void run() {
        HttpUtil.sendOkHttpRequeest(Contracts.WEATHER_NOW_TMP + distract + "&key="+Contracts.WETHER_API_KEY, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //Toast.makeText(mContext,"请求失败",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                NowTmp nowTmp =  Utility.handWeatherNowTmpInfo(response.body().string());
                //Bundle bundle = new Bundle();
                //bundle.putString("cond_txt",nowTmp.getCond_txt());
                Location.cond_txt = nowTmp.getCond_txt();
                //bundle.putString("tmp",nowTmp.getTmp());
                Location.tmp = nowTmp.getTmp();
                //bundle.putString("wind_dir",nowTmp.getWind_dir());
                Location.wind_dir = nowTmp.getWind_dir();
                //bundle.putString("cloud",nowTmp.getCloud());
                Location.cloud = nowTmp.getCloud();
                //bundle.putString("_wind_sc", nowTmp.get_wind_sc());
                Location._wind_sc = nowTmp.get_wind_sc();
                Log.e("Booerr---test","NowTmpThread is right");
            }
        });
    }
}
