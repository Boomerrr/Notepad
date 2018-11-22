package com.example.think.notepad.Activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.think.notepad.R;
import com.example.think.notepad.SQLite.UserDatabaseHelper;
/*
* 登录界面
* Create by Boomerr Yi  2018/11/10
* */

public class LogInActivity extends Activity implements View.OnClickListener{

    private EditText username;//用户名输入行
    private EditText password;//密码输入行
    private Button login_btn;//登录按钮
    private Button sigin_btn;//注册按钮
    private String  name;
    private String pass;
    private static final String TAG = LogInActivity.class.getSimpleName();

    private UserDatabaseHelper userDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        inti();

    }


    private void inti() {
        username = (EditText) findViewById(R.id.et_username);
        password = (EditText) findViewById(R.id.et_password);
        login_btn = (Button) findViewById(R.id.login_btn);
        sigin_btn = (Button) findViewById(R.id.sigin_btn);
        login_btn.setOnClickListener(this);
        sigin_btn.setOnClickListener(this);
        userDatabaseHelper = new UserDatabaseHelper(this,"UserInfo.db",null,2);
        setSignInButton();
    }

    private void setSignInButton() {
        SQLiteDatabase db = userDatabaseHelper.getWritableDatabase();
        Cursor cursor = db.query("User",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
                 name = cursor.getString(cursor.getColumnIndex("username"));
                Log.e("Boomerr---test-db",name);
                 pass = cursor.getString(cursor.getColumnIndex("password"));
                 Log.e("Boomerr--test-db",pass);
                if(name != null && pass !=null){
                    sigin_btn.setEnabled(false);
                }
        }

    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.login_btn:
                //此处添加登录按钮功能
                login_function();
                break;
            case R.id.sigin_btn:
                //此处添加初测按钮功能 页面跳转即可
                startActivity(new Intent(LogInActivity.this,SignInActivity.class));
                break;
            default:
                break;
        }
    }

    private void login_function() {
        String userName = username.getText().toString();
        String passWord = password.getText().toString();
       if(userName.equals(name) && passWord.equals(pass)){
           Intent intent = new Intent(LogInActivity.this,WorkActivity.class);
           intent.putExtra("userName",userName);
           startActivity(intent);
           finish();
       }else{
           Toast.makeText(this,"密码输入错误",Toast.LENGTH_SHORT).show();
       }
    }


}
