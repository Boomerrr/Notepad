package com.example.think.notepad;

import android.widget.TextView;

import java.util.Calendar;

public class GetTime {
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    //月
    int month = calendar.get(Calendar.MONTH)+1;
    //日
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    //获取系统时间

}
