package com.example.think.notepad.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class NotepadDatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_NOTEPAD = "create table Notepad (" +
            "id integer primary key autoincrement," +
            "title text," +
            "time text," +
            "text text)";
    private Context mContext;
    public NotepadDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context,name,factory,version);
            mContext = context;
        }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_NOTEPAD);
        Toast.makeText(mContext,"Create success",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists Notepad");
    }
}
