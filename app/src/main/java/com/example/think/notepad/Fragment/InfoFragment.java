package com.example.think.notepad.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.think.notepad.Activity.WorkActivity;
import com.example.think.notepad.Base.BaseFragment;
import com.example.think.notepad.IView;
import com.example.think.notepad.R;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class InfoFragment extends BaseFragment implements View.OnClickListener, IView {
    private ImageButton imageButton1;
    private ImageButton imageButton2;
    private ImageButton imageButton3;
    private ImageButton imageButton4;
    private ImageButton imageButton5;
    private ImageButton imageButton6;
    private ImageButton imageButton7;
    private ImageButton imageButton8;

    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;
    private EditText editText5;
    private EditText editText6;
    private EditText editText7;
    private EditText editText8;

   SharedPreferences sharedPreferences;
   SharedPreferences.Editor editor;

    private Button save;

    @Override
    protected View initView() {
        View view=View.inflate(mContext, R.layout.fragment_info,null);
        CircleImageView headIamge = (CircleImageView) view.findViewById(R.id.head_image);
        sharedPreferences = getActivity().getSharedPreferences("Info",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        headIamge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkActivity workActivity = (WorkActivity)getActivity();
                DrawerLayout drawerLayout =  workActivity.findViewById(R.id.drawablelayout);
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        ViewFunction(view);
        new Thread(new Runnable() {
            @Override
            public void run() {
                initViewFunction();
            }
        }).start();
        return view;
    }

    private void initViewFunction() {
        final String classcode = sharedPreferences.getString("classcode",null);
        final String studentNum = sharedPreferences.getString("studentNum",null);
        final String college = sharedPreferences.getString("college",null);
        final String QQ = sharedPreferences.getString("QQ",null);
        final String telephone = sharedPreferences.getString("telephone",null);
        final String email = sharedPreferences.getString("email",null);
        final String weiChat = sharedPreferences.getString("weiChat",null);
        final String address = sharedPreferences.getString("address",null);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                editText1.setText(classcode);
                editText2.setText(studentNum);
                editText3.setText(college);
                editText4.setText(QQ);
                editText5.setText(telephone);
                editText6.setText(email);
                editText7.setText(weiChat);
                editText8.setText(address);
            }
        });
    }


    private void ViewFunction(View view) {
        imageButton1 = (ImageButton) view.findViewById(R.id.button1);
        imageButton2 = (ImageButton) view.findViewById(R.id.button2);
        imageButton3 = (ImageButton) view.findViewById(R.id.button3);
        imageButton4 = (ImageButton) view.findViewById(R.id.button4);
        imageButton5 = (ImageButton) view.findViewById(R.id.button5);
        imageButton6 = (ImageButton) view.findViewById(R.id.button6);
        imageButton7 = (ImageButton) view.findViewById(R.id.button7);
        imageButton8 = (ImageButton) view.findViewById(R.id.button8);
        editText1 = (EditText) view.findViewById(R.id.edit_text1);
        editText2 = (EditText) view.findViewById(R.id.edit_text2);
        editText3 = (EditText) view.findViewById(R.id.edit_text3);
        editText4 = (EditText) view.findViewById(R.id.edit_text4);
        editText5 = (EditText) view.findViewById(R.id.edit_text5);
        editText6 = (EditText) view.findViewById(R.id.edit_text6);
        editText7 = (EditText) view.findViewById(R.id.edit_text7);
        editText8 = (EditText) view.findViewById(R.id.edit_text8);
        save = (Button) view.findViewById(R.id.save);
        imageButton1.setOnClickListener(this);
        imageButton2.setOnClickListener(this);
        imageButton3.setOnClickListener(this);
        imageButton4.setOnClickListener(this);
        imageButton5.setOnClickListener(this);
        imageButton6.setOnClickListener(this);
        imageButton7.setOnClickListener(this);
        imageButton8.setOnClickListener(this);
        save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button1:
                editText1.setEnabled(true);
                break;
            case R.id.button2:
                editText2.setEnabled(true);
                break;
            case R.id.button3:
                editText3.setEnabled(true);
                break;
            case R.id.button4:
                editText4.setEnabled(true);
                break;
            case R.id.button5:
                editText5.setEnabled(true);
                break;
            case R.id.button6:
                editText6.setEnabled(true);
                break;
            case R.id.button7:
                editText7.setEnabled(true);
                break;
            case R.id.button8:
                editText8.setEnabled(true);
                break;
            case R.id.save:
                saveFunction();
                break;
        }
    }

    private void saveFunction() {
        editText1.setEnabled(false);
        editText2.setEnabled(false);
        editText3.setEnabled(false);
        editText4.setEnabled(false);
        editText5.setEnabled(false);
        editText6.setEnabled(false);
        editText7.setEnabled(false);
        editText8.setEnabled(false);
        String classcode = editText1.getText().toString();
        String studentNum = editText2.getText().toString();
        String college = editText3.getText().toString();
        String QQ = editText4.getText().toString();
        String telephone = editText5.getText().toString();
        String email = editText6.getText().toString();
        String weiChat = editText7.getText().toString();
        String address = editText8.getText().toString();
        editor.putString("classcode",classcode);
        editor.putString("studentNum",studentNum);
        editor.putString("college",college);
        editor.putString("QQ",QQ);
        editor.putString("telephone",telephone);
        editor.putString("email",email);
        editor.putString("weiChat",weiChat);
        editor.putString("address",address);
        editor.apply();
    }
}
