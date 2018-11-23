package com.example.think.notepad.Fragment;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.think.notepad.Activity.WorkActivity;
import com.example.think.notepad.Base.BaseFragment;
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

public class InfoFragment extends BaseFragment implements View.OnClickListener{
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

    private String classode = " ";
    private String studentNum = " ";
    private String college = " ";
    private String QQ = " ";
    private String telephone = " ";
    private String email = " ";
    private String weiChat = " ";
    private String address = " ";
    private final String FILE_NAME = "info.txt";
    private File file = new File(FILE_NAME);

    private Button save;

    @Override
    protected View initView() {
        View view=View.inflate(mContext, R.layout.fragment_info,null);
        initFile();
        CircleImageView headIamge = (CircleImageView) view.findViewById(R.id.head_image);
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
                readFileFunction();
            }
        }).start();
        return view;
    }

    private void readFileFunction() {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            FileInputStream fileInputStream = getActivity().openFileInput(FILE_NAME);
            byte[] buff = new byte[1024];
            int hasRead = 0;
            DataInputStream dataIO = new DataInputStream(fileInputStream);
            String sb = "";
            int i = 0;
            while ((sb = dataIO.readLine()) != null){
                Log.e("Boomerr--test",sb + " "+ i++);
                arrayList.add(sb);
            }
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        classode = arrayList.get(0);
        studentNum = arrayList.get(1);
        college = arrayList.get(2);
        QQ = arrayList.get(3);
        telephone = arrayList.get(4);
        email = arrayList.get(5);
        weiChat = arrayList.get(6);
        address = arrayList.get(7);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                editText1.setText(classode);
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

    private void initFile() {
        if(file.exists()){
            Log.e("Boomerr---test","file create");
            file.mkdir();
            try {
                FileOutputStream fileOutputStream = getActivity().openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                PrintStream ps = new PrintStream(fileOutputStream);
                ps.println(classode);
                Log.e("Boomerr---test1",classode);
                ps.println(studentNum);
                Log.e("Boomerr---test2",studentNum);
                ps.println(college);
                Log.e("Boomerr---test3",college);
                ps.println(QQ);
                Log.e("Boomerr---test4",QQ);
                ps.println(telephone);
                Log.e("Boomerr---test5",telephone);
                ps.println(email);
                Log.e("Boomerr---test6",email);
                ps.println(weiChat);
                Log.e("Boomerr---test7",weiChat);
                ps.println(address);
                Log.e("Boomerr---test8",address);
                ps.close();
                Log.e("Boomerr---test","write successfully");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }else{
            Log.e("Boomerr---test","file exis");
        }
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
        classode = editText1.getText().toString();
        studentNum = editText2.getText().toString();
        college = editText3.getText().toString();
        QQ = editText4.getText().toString();
        telephone = editText5.getText().toString();
        email = editText6.getText().toString();
        weiChat = editText7.getText().toString();
        address = editText8.getText().toString();
            try {
                FileOutputStream fileOutputStream = getActivity().openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                PrintStream ps = new PrintStream(fileOutputStream);
                ps.println(classode);
                ps.println(studentNum);
                ps.println(college);
                ps.println(QQ);
                ps.println(telephone);
                ps.println(email);
                ps.println(weiChat);
                ps.println(address);
                Log.e("Boomerr---test1",classode);
                Log.e("Boomerr---test2",studentNum);
                Log.e("Boomerr---test3",college);
                Log.e("Boomerr---test4",QQ);
                Log.e("Boomerr---test5",telephone);
                Log.e("Boomerr---test6",email);
                Log.e("Boomerr---test7",weiChat);
                Log.e("Boomerr---test8",address);
                ps.close();
                Log.e("Boomerr---test","write successfully");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

    }
}
