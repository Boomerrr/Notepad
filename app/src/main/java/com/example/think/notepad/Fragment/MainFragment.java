package com.example.think.notepad.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.example.think.notepad.Activity.WorkActivity;
import com.example.think.notepad.Adapter.NotepadeAdapter;
import com.example.think.notepad.Base.BaseFragment;
import com.example.think.notepad.Bean.NotePad;

import com.example.think.notepad.Contracts;
import com.example.think.notepad.R;
import com.example.think.notepad.SQLite.NotepadDatabaseHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.think.notepad.Contracts.CREATER;

/*
* Create by Boomerr Yi 2018/11/10
*
* */
public class MainFragment extends BaseFragment implements NotepadeAdapter.OnItemClickListener {

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
        notepadeAdapter.setItemClickListener(this);
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


    @Override
    public void onItemClick(int position) {
        String data = notePadList.get(position).getText();
        final MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(Contracts.AUDIO_BAIDU_HEAD + data + Contracts.AUDIO_BAIDU_LAST);
            //3 准备播放
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
        AlertDialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder .setMessage(           //定义显示的文字
                "正在阅读...")
                .setPositiveButton("关闭", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mediaPlayer.stop();
                    }
                });//显示对话框
        dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
    }
}
