package com.example.think.notepad.Thread;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.think.notepad.Bean.NotePad;
import com.example.think.notepad.SQLite.NotepadDatabaseHelper;

import java.util.ArrayList;

import static com.example.think.notepad.Contracts.CREATER;

public class MessageReadThread extends Thread {
    private NotepadDatabaseHelper notepadDatabaseHelper;
    @Override
    public void run() {
        ArrayList<NotePad> notePadList = new ArrayList<>();
        notepadDatabaseHelper = new NotepadDatabaseHelper(null,"notepad.db",null,1);
        SQLiteDatabase db=notepadDatabaseHelper.getWritableDatabase();
        Cursor cursor=db.query("Notepad",null,null,null,null,null,null);
        if(cursor.moveToNext()){
            do{
                NotePad notePad = new NotePad();
                String title=cursor.getString(cursor.getColumnIndex("title"));
                String time=cursor.getString(cursor.getColumnIndex("time"));
                String text=cursor.getString(cursor.getColumnIndex("text"));
                notePad.setTitle(title);
                notePad.setOrderTime(time);
                notePad.setText(text);
                Log.d(CREATER,"title"+title);
                Log.d(CREATER,"time"+time);
                Log.d(CREATER,"text"+text);
                notePadList.add(notePad);
            }while(cursor.moveToNext());
        }
        cursor.close();
    }
}
