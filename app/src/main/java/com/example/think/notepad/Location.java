package com.example.think.notepad;

import android.app.Application;

import com.example.think.notepad.Bean.NotePad;

import java.util.ArrayList;

public class Location extends Application{

    public static String country = "";//"中国"//国家
    public static String province = "";//"重庆市";//省市
    public static String city = "";//"重庆市";//城市
    public static String distract = "";//"南岸区";//区

    public static String brf = "";//"较舒适";//生活指数简介
    public static String txt = "";//"南山搞头 真的是巴适得板";//生活指数详细描述

    public static String cond_txt = "";//"风夹雨";//实况天气状况描述
    public static String tmp = "";//"10";//温度，默认单位：摄氏度
    public static String wind_dir = "";//"西北风噻";//风向
    public static String cloud = "";//"多的口角的云";//云量
    public static String _wind_sc = "";//"大风刮呀刮";//风力
}
