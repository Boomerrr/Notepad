package com.example.think.notepad.Fragment;

import android.animation.ObjectAnimator;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.RelativeLayout;
import android.widget.Toolbar;

import com.example.think.notepad.Activity.WorkActivity;
import com.example.think.notepad.Adapter.NotepadeAdapter;
import com.example.think.notepad.Base.BaseFragment;
import com.example.think.notepad.Bean.NotePad;

import com.example.think.notepad.IView;
import com.example.think.notepad.R;
import com.example.think.notepad.SQLite.NotepadDatabaseHelper;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;
import static com.example.think.notepad.Contracts.CREATER;
import static com.example.think.notepad.Contracts.FILE_NAME;
/*
* Create by Boomerr Yi 2018/11/10
*
* */
public class MainFragment extends BaseFragment implements IView {

    private NotepadDatabaseHelper notepadDatabaseHelper;
    private SwipeRefreshLayout swipeRefreshLayout;
    private  NotepadeAdapter notepadeAdapter;
    private ArrayList<NotePad> notePadList;
    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.fragment_main, null);
        notePadList = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycelr_main);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        init();
        notepadeAdapter = new NotepadeAdapter(notePadList);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(notepadeAdapter);
        swipeRefreshLayoutFunction(view);
        CircleImageView headImage = (CircleImageView) view.findViewById(R.id.head_image);
        headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkActivity workActivity = (WorkActivity) getActivity();
                DrawerLayout drawerLayout = workActivity.findViewById(R.id.drawablelayout);
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        return view;
    }

    private void swipeRefreshLayoutFunction(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                init();
                                notepadeAdapter.notifyDataSetChanged();
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    }
                }).start();
            }
        });
    }

    private void init() {
        notePadList.clear();
        notepadDatabaseHelper = new NotepadDatabaseHelper(getActivity(), "notepad.db", null, 1);
        SQLiteDatabase db = notepadDatabaseHelper.getWritableDatabase();
        Cursor cursor = db.query("Notepad", null, null, null, null, null, null);
        if (cursor.moveToNext()) {
            do {
                NotePad notePad = new NotePad();
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                String text = cursor.getString(cursor.getColumnIndex("text"));
                notePad.setTitle(title);
                notePad.setOrderTime(time);
                notePad.setText(text);
                Log.d(CREATER, "title" + title);
                Log.d(CREATER, "time" + time);
                Log.d(CREATER, "text" + text);
                notePadList.add(notePad);
            } while (cursor.moveToNext());
        }
        Collections.reverse(notePadList);
        cursor.close();
    }

}
