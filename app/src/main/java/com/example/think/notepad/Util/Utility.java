package com.example.think.notepad.Util;

import android.util.Log;

import com.baidu.mapsdkplatform.comapi.e;
import com.example.think.notepad.Bean.NowLifestyle;
import com.example.think.notepad.Bean.NowTmp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utility {
    public static NowTmp handWeatherNowTmpInfo(String response) {
        try {
            Log.e("Boomerr---test",response);
            JSONObject object = new JSONObject(response);
            JSONObject object1 = object.getJSONArray("HeWeather6").getJSONObject(0);
            JSONObject now = object1.getJSONObject("now");
            String clound = now.getString("cloud");
            String cond_txt = now.getString("cond_txt");
            String tmp = now.getString("tmp");
            String wind_dir = now.getString("wind_dir");
            String _wind_sc = now.getString("wind_sc");
            NowTmp nowTmp = new NowTmp();
            nowTmp.setCond_txt(cond_txt);
            nowTmp.setCloud(clound);
            nowTmp.setTmp(tmp);
            nowTmp.setWind_dir(wind_dir);
            nowTmp.set_wind_sc(_wind_sc);
            return nowTmp;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static NowLifestyle handWeatherNowLifestyle(String response){
        try{
            JSONObject object = new JSONObject(response);
            //Log.e("-----",response.toString());
            JSONObject object1 = object.getJSONArray("HeWeather6").getJSONObject(0);
            JSONObject lifestyle =object1.getJSONArray("lifestyle").getJSONObject(0);
            String brf = lifestyle.getString("brf");
            String txt = lifestyle.getString("txt");
            NowLifestyle nowLifestyle = new NowLifestyle();
            nowLifestyle.setBrf(brf);
            //Log.e("lifestyle",brf);
            nowLifestyle.setTxt(txt);
            //Log.e("lifestyle",txt);
            return nowLifestyle;
        }catch(JSONException e){
            e.printStackTrace();
        }
        return null;
    }
}