package com.example.think.notepad.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.think.notepad.R;
/*
* 欢迎界面 以一张图片为背景即可
* 完成权限授予 如果未能给予足够权限，则会退出登录
* 在这个界面还应该完成静默登录功能
*
* Create by Boomerr Yi
* */
public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startMainActivity();
            }
        },0);
    }
    private void startMainActivity(){
        Intent intent=new Intent(WelcomeActivity.this,LogInActivity.class);
        startActivity(intent);
        finish();
    }
}


