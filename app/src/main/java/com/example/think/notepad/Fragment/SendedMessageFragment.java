package com.example.think.notepad.Fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.think.notepad.Activity.SendMessageActivity;
import com.example.think.notepad.Adapter.MessageAdapter;
import com.example.think.notepad.Base.BaseFragment;
import com.example.think.notepad.Bean.message;
import com.example.think.notepad.R;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SendedMessageFragment extends BaseFragment {
    final String SMS_URI_SEND = "content://sms/sent";
    @Override
    protected View initView() {
        View view=View.inflate(mContext, R.layout.fragment_sendedmessage,null);
        List<message> msg = init();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_send_message);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        MessageAdapter messageAdapter = new MessageAdapter(msg);
        recyclerView.setAdapter(messageAdapter);
        floatButton(view);
        return view;
    }

    private void floatButton(View view) {
        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SendMessageActivity.class);
                startActivity(intent);
            }
        });
    }

    private List<message> init() {
        List<message>  messageList = new ArrayList<>();
        ContentResolver cr = getActivity().getContentResolver();
        Cursor cursor = cr.query(Uri.parse(SMS_URI_SEND),null,null,null,null);
        StringBuffer stringBuffer = new StringBuffer();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        while(cursor.moveToNext()){
            message msg = new message();
            String address = cursor.getString(cursor.getColumnIndex("address"));
            String date=simpleDateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex("date"))));//获取短信的日期
            String body= cursor.getString(cursor.getColumnIndex("body"));//获取短信内容
            int type= cursor.getInt(cursor.getColumnIndex("type"));//获取类型，看是否是接收还是发送
            String typeStr="";
            if(type==1){
                typeStr="接收";
            }else if(type==2){
                typeStr="发送";
            }else{
                typeStr=null;
            }
            stringBuffer.append(address+":\t\t"+date+":\t\t"+typeStr+":\t\t"+body+"\r\n\n");//拼接短信内容
            Log.e("Boomerr---test",address+"   "+body+"  "+type);
            msg.setUser(address);
            msg.setTime(date);
            msg.setText(body);
            messageList.add(msg);
        }
        return messageList;
    }
}
