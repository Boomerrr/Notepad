package com.example.think.notepad;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.think.notepad.Activity.AlarmActivity;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String notepad = intent.getStringExtra("NotePad");
        Intent intent1 = new Intent(context, AlarmActivity.class);//定义要操作的Intent
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//传递一个新的任务标记
        intent1.putExtra("NotePad1",notepad);
        Log.e("Boomerr---test",notepad);
        context.startActivity(intent1);//
    }
}
