package com.example.think.notepad.Fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.example.think.notepad.Activity.WorkActivity;
import com.example.think.notepad.AlarmReceiver;
import com.example.think.notepad.Base.BaseFragment;
import com.example.think.notepad.Bean.NotePad;
import com.example.think.notepad.R;
import com.example.think.notepad.SQLite.NotepadDatabaseHelper;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.ALARM_SERVICE;

/*
* Create By Boomerr Yi 2018/11/6
* */
public class AddFragment extends BaseFragment {
    private Calendar d  = Calendar.getInstance();
    private Calendar currentTime  = Calendar.getInstance();
    private NotepadDatabaseHelper notepadDatabaseHelper;
    @Override
    protected View initView() {
        View view=View.inflate(mContext, R.layout.fragment_add,null);
        GetWord(view);
        return view;
    }

    private void GetWord(View view) {
        notepadDatabaseHelper = new NotepadDatabaseHelper(getActivity(),"notepad.db",null,1);
        CircleImageView headImg = (CircleImageView) view.findViewById(R.id.head_image);
        final EditText title = (EditText) view.findViewById(R.id.title);
        final EditText text = (EditText) view.findViewById(R.id.text);
        Button timePicker = (Button) view.findViewById(R.id.time_picker_btn) ;
        Button addBtn = (Button) view.findViewById(R.id.add_button);
        final TextView timePickerText = (TextView) view.findViewById(R.id.time_picker_text);
        //头像点击事件
        headImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkActivity workActivity = (WorkActivity)getActivity();
                DrawerLayout drawerLayout =  workActivity.findViewById(R.id.drawablelayout);
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        //时间选择
        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //d.setTimeInMillis(System.currentTimeMillis());
                new TimePickerDialog(getActivity(), 0, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        d.setTimeInMillis(System.currentTimeMillis());

                        d.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        d.set(Calendar.MINUTE,minute);
                        d.set(Calendar.SECOND,0);
                        d.set(Calendar.MILLISECOND,0);
                        String date =hourOfDay + " : " + minute;
                        timePickerText.setText(date);
                        Log.e("Boomerr---test1--触发事件", String.valueOf(d.getTimeInMillis()));
                        Log.e("Boomerr---test2--当前时间", String.valueOf(currentTime.getTimeInMillis()));
                    }
                },currentTime.get(Calendar.HOUR_OF_DAY),currentTime.get(Calendar.MINUTE),true).show();
            }
        });
        //添加事件确定按钮
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = notepadDatabaseHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                if (title.getText().toString().equals("") ||  text.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "添加失败", Toast.LENGTH_SHORT).show();
                } else {
                    values.put("title", title.getText().toString());
                    values.put("time", timePickerText.getText().toString());
                    values.put("text", text.getText().toString());
                    db.insert("Notepad", null, values);
                    values.clear();
                    NotePad notePad = new NotePad();
                    notePad.setOrderTime(timePickerText.getText().toString());
                    notePad.setText(text.getText().toString());
                    notePad.setTitle(title.getText().toString());

                    Intent intent = new Intent(getActivity(), AlarmReceiver.class);
                    intent.putExtra("NotePad",text.getText().toString());
                    intent.setAction("com.example.think.action.setalarm");
                    PendingIntent sender = PendingIntent.getBroadcast(
                            getActivity(), 0, intent,
                            PendingIntent.FLAG_UPDATE_CURRENT);//指定PendingIntent
                    AlarmManager am = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        am.setExact(AlarmManager.RTC_WAKEUP, d.getTimeInMillis(), sender);
                    }else {
                        am.set(AlarmManager.RTC_WAKEUP, d.getTimeInMillis(), sender);
                    }
                    am.set(AlarmManager.RTC_WAKEUP, d.getTimeInMillis(), sender);
                    //Log.e("Boomerr---test", String.valueOf(d.getTimeInMillis()));
                    Toast.makeText(getActivity(), "闹钟添加成功", Toast.LENGTH_SHORT).show();

                    title.setText("");
                    timePickerText.setText("");
                    text.setText("");
                }
            }
        });
    }


}
