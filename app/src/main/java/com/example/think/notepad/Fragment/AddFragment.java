package com.example.think.notepad.Fragment;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;


import com.example.think.notepad.Activity.WorkActivity;
import com.example.think.notepad.Base.BaseFragment;
import com.example.think.notepad.R;
import com.example.think.notepad.SQLite.NotepadDatabaseHelper;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;
/*
* Create By Boomerr Yi 2018/11/6
* */
public class AddFragment extends BaseFragment {
    private NotepadDatabaseHelper notepadDatabaseHelper;
    @Override
    protected View initView() {
        View view=View.inflate(mContext, R.layout.fragment_add,null);
        GetWord(view);
        return view;
    }

    private void GetWord(View view) {
        CircleImageView headImg = (CircleImageView) view.findViewById(R.id.head_image);
        final EditText title = (EditText) view.findViewById(R.id.title);
        final EditText text = (EditText) view.findViewById(R.id.text);
        Button timePicker = (Button) view.findViewById(R.id.time_picker_btn) ;
        Button addBtn = (Button) view.findViewById(R.id.add_button);
        final TextView timePickerText = (TextView) view.findViewById(R.id.time_picker_text);
        headImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkActivity workActivity = (WorkActivity)getActivity();
                DrawerLayout drawerLayout =  workActivity.findViewById(R.id.drawablelayout);
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        final Calendar d = Calendar.getInstance();
        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(getActivity(), 0, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {

                        d.setTimeInMillis(System.currentTimeMillis());
                        String date = "" + hourOfDay + " : " + minute;
                        timePickerText.setText(date);
                    }
                },Calendar.HOUR,Calendar.MINUTE,true).show();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String time = (String) timePickerText.getText();
                String titleText  = String.valueOf(title.getText());
                Log.e("Boomerr---test",titleText);
                String textText = String.valueOf(text.getText());
                Log.e("Boomerr---test",textText);
                notepadDatabaseHelper  = new NotepadDatabaseHelper(getActivity(),"Notepad.db",null,2);
                SQLiteDatabase db = notepadDatabaseHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("title",titleText);
                values.put("time",time);
                values.put("text",textText);
                //if(titleText!=null&&time!=null&&textText!=null)
                db.insert("Notepad",null,values);
                values.clear();

            }
        });
    }


}
