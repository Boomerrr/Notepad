package com.example.think.notepad.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.think.notepad.IView;
import com.example.think.notepad.R;
import com.example.think.notepad.SQLite.UserDatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
/*
* 登录界面
* Create by Boomerr Yi  2018/11/10
* */

public class LogInActivity extends Activity implements View.OnClickListener,IView {

    private EditText username;//用户名输入行
    private EditText password;//密码输入行
    private Button login_btn;//登录按钮
    private Button sigin_btn;//注册按钮
    private String  name;
    private String pass;
    private ImageView imageView;
    private CheckBox  rememberName;
    private static final String TAG = LogInActivity.class.getSimpleName();

    private int checkbox = 0;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private String help = "  本App每个客户端仅支持一个绑定账号，如未绑定，请先注册，再行登录，在主页内容为记事本内容，" +
            "个人信息板块为个人信息记录，天气板块为当前实时天气播报，短信版块为该App的通信板块，添加板块为记事本闹钟事件添加板块。\n   " +
            "                                        祝您使用愉快";

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
        imageView = (ImageView) findViewById(R.id.help);
        rememberName = (CheckBox) findViewById(R.id.remember_pass);
        login_btn.setOnClickListener(this);
        sigin_btn.setOnClickListener(this);
        imageView.setOnClickListener(this);
        userDatabaseHelper = new UserDatabaseHelper(this,"UserInfo.db",null,2);
        sharedPreferences = getSharedPreferences("UserInfo",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        username.setText(sharedPreferences.getString("UserName",null));
        checkbox = sharedPreferences.getInt("checkbox",0);
        if(checkbox == 1){
            rememberName.setChecked(true);
        }else{
            rememberName.setChecked(false);
        }
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
            case R.id.help:
                HelpFunction();
            default:
                break;
        }
    }

    private void HelpFunction() {
        View view = View.inflate(getApplicationContext(),R.layout.dialog_layout,null);
        final AlertDialog dialog = new AlertDialog.Builder(this).setView(view).create();
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView text = (TextView) view.findViewById(R.id.text);
        title.setText("帮助");
        text.setText(help);
        dialog.setCancelable(true);
        dialog.show();
    }

    private void login_function() {
        String userName = username.getText().toString();
        String passWord = password.getText().toString();
        if(rememberName.isChecked()){
            editor.putString("UserName",userName);
            editor.putInt("checkbox",1);
            editor.apply();
        }else{
            editor.putString("UserName","");
            editor.putInt("checkbox",0);
            editor.apply();
        }
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
