package com.example.think.notepad.Activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.think.notepad.R;
import com.example.think.notepad.SQLite.UserDatabaseHelper;

import org.w3c.dom.Text;

/*
* 注册界面
* Create by Boomerr Yi
* */
public class SignInActivity extends Activity implements View.OnClickListener {
    private Button reset;//重置按钮
    private Button signin;//注册按钮
    private TextInputEditText username;//用户名输入
    private TextInputEditText password;//密码输入
    private TextInputEditText password_again;//密码再次输入
    private TextInputEditText telephone;//联系方式输入

    private UserDatabaseHelper userDatabaseHelper;
    private static final String TAG = SignInActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        inti();
    }

    private void inti() {
        reset = (Button) findViewById(R.id.signin_btn);
        signin = (Button) findViewById(R.id.reset_btn);
        username = (TextInputEditText) findViewById(R.id.et_username);
        password = (TextInputEditText) findViewById(R.id.et_password);
        password_again = (TextInputEditText) findViewById(R.id.et_password_2);
        telephone = (TextInputEditText) findViewById(R.id.et_tel);
        reset.setOnClickListener(this);
        signin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.signin_btn:
                //注册按钮的点击内容
                signin_function();
                break;
            case R.id.reset_btn:
                //重置注册页面的按钮
                reset_function();
                break;
            default:
                break;
        }
    }
    //重置部分 将各个EditText中的内容清空
    private void reset_function() {
        username.getText().clear();
        password.getText().clear();
        password_again.getText().clear();
        telephone.getText().clear();
    }

    //详细注册部分 将注册信息写进SQLite
    private void signin_function() {
        userDatabaseHelper = new UserDatabaseHelper(this,"UserInfo.db",null,2);
        SQLiteDatabase db = userDatabaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        String userName = username.getText().toString();
        String passWord = password.getText().toString();
        String tel = telephone.getText().toString();
        Log.e(TAG,userName);
        Log.e(TAG,passWord);
        Log.e(TAG,tel);
        values.put("username",userName);
        values.put("password",passWord);
        values.put("telephone",tel);
        db.insert("User",null,values);
        values.clear();

    }
}
