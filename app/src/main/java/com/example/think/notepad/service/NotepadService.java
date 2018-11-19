package com.example.think.notepad.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Vibrator;

import java.io.IOException;

public class NotepadService extends Service {
    public NotepadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource("https://tsn.baidu.com/text2audio?tex=\"安逸舒服巴适美滋滋\"&lan=zh&cuid=\"Boomerryibochen\"&ctp=1&tok=24.28b824c2df182e95a6c8382e07c35dc0.2592000.1544863298.282335-14822582&per=0");
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
        return super.onStartCommand(intent, flags, startId);
    }
}
