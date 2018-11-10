package com.example.think.notepad.Fragment;

import android.app.TimePickerDialog;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;


import com.example.think.notepad.Activity.WorkActivity;
import com.example.think.notepad.Base.BaseFragment;
import com.example.think.notepad.Bean.NotePad;
import com.example.think.notepad.Location;
import com.example.think.notepad.R;
import com.example.think.notepad.SQLite.NotepadDatabaseHelper;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_APPEND;
import static com.example.think.notepad.Contracts.FILE_NAME;

/*
* Create By Boomerr Yi 2018/11/6
* */
public class AddFragment extends BaseFragment {

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

        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(getActivity(), 0, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        Calendar d = Calendar.getInstance();
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
                NotePad notePad = new NotePad();
                notePad.setOrderTime(timePickerText.getText().toString());
                notePad.setText(text.getText().toString());
                notePad.setTitle(title.getText().toString());
                Location location = (Location) getActivity().getApplication();
                location.notepadArrayList.add(notePad);
                writeAdd(notePad.getTitle());
                writeAdd(notePad.getOrderTime());
                writeAdd(notePad.getText());
            }
        });
    }

    public  void writeAdd (String content){
        try {
            FileOutputStream fos = getActivity().openFileOutput(FILE_NAME, MODE_APPEND);
            PrintStream ps = new PrintStream(fos);
            ps.println(content);
            ps.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
