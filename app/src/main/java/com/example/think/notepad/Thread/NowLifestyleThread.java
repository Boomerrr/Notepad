package com.example.think.notepad.Thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;

import com.example.think.notepad.Bean.NowLifestyle;
import com.example.think.notepad.Contracts;
import com.example.think.notepad.Location;
import com.example.think.notepad.Util.HttpUtil;
import com.example.think.notepad.Util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class NowLifestyleThread implements  Runnable {
    private String distract;
    private Handler handler;
    public NowLifestyleThread(String distract, Handler handler){
        this.distract = distract;
        this.handler = handler;
    }
    @Override
    public void run() {

        HttpUtil.sendOkHttpRequeest(Contracts.WEATHER_LIFESTYLE +distract + "&key=" + Contracts.WETHER_API_KEY, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //Toast.makeText(getActivity(),"请求失败",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                NowLifestyle nowLifestyle = Utility.handWeatherNowLifestyle(response.body().string());
                Bundle bundle = new Bundle();
                bundle.putString("brf",nowLifestyle.getBrf());

                bundle.putString("txt",nowLifestyle.getTxt());
                Message message = new Message();
                message.setData(bundle);
                handler.sendMessage(message);
                Log.e("Booerr---test","NowLifestyleThread is right");
            }
        });
    }
}
