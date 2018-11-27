package com.example.think.notepad.Activity;

import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.think.notepad.Contracts;
import com.example.think.notepad.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        Intent intent = getIntent();
        String string = intent.getStringExtra("NotePad1");
        Log.e("Bomerr---test",string);
        String data = "小微提醒" + string;
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(Contracts.AUDIO_BAIDU_HEAD + data + Contracts.AUDIO_BAIDU_LAST);
            //3 准备播放
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
        Vibrator vibrator = (Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
        vibrator.cancel();
        vibrator.vibrate(1000);
        AlertDialog dialog = null;
       AlertDialog.Builder builder =  new AlertDialog.Builder(this)//建立对话框
                .setTitle("闹钟时间已到！")//设置对话框标题
                .setMessage(           //定义显示的文字
                        "闹钟响起，现在时间："
                                + new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒")
                                .format(new Date()) + string)
                .setPositiveButton("关闭", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlarmActivity.this.finish();//关闭对话框后程序结束
                    }
                });
       dialog = builder.create();
       dialog.show();
       dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);

    }
}
