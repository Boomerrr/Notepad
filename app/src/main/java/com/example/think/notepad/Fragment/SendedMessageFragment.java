package com.example.think.notepad.Fragment;

import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.example.think.notepad.Adapter.MessageAdapter;
import com.example.think.notepad.Base.BaseFragment;
import com.example.think.notepad.Bean.message;
import com.example.think.notepad.IView;
import com.example.think.notepad.R;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SendedMessageFragment extends BaseFragment implements IView {
    final String SMS_URI_SEND = "content://sms/sent";
    SwipeRefreshLayout swipeRefreshLayout;
    private MessageAdapter messageAdapter;
    private RecyclerView recyclerView;
    private List<message> messageList = new ArrayList<>();
    SimpleDateFormat simpleDateFormat;
    private  EditText telephone;
    private  EditText text;
    private CheckBox checkBox;
    @Override
    protected View initView() {
        View view=View.inflate(mContext, R.layout.fragment_sendedmessage,null);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        init();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_send_message);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        messageAdapter = new MessageAdapter(messageList);
        floatButton(view);
        swiperefresh(view);
        return view;
    }

    private void swiperefresh(View view) {
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
                                    messageAdapter.notifyDataSetChanged();
                                    swipeRefreshLayout.setRefreshing(false);
                                }
                            });
                        }
                    }).start();
                }
            });
        }

    private void floatButton(View view) {
        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(getActivity(),R.layout.send_message);
            }
        });
    }

    private void init() {
        messageList.clear();
        ContentResolver cr = getActivity().getContentResolver();
        final Cursor cursor = cr.query(Uri.parse(SMS_URI_SEND),null,null,null,null);
        final StringBuffer stringBuffer = new StringBuffer();
        simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        new Thread(new Runnable() {
            @Override
            public void run() {
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
                    //Log.e("Boomerr---test",address+"   "+body+"  "+type);
                    msg.setUser(address);
                    msg.setTime(date);
                    msg.setText(body);
                    messageList.add(msg);
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setAdapter(messageAdapter);
                    }
                });
            }
        }).start();
    }

    private void   showPopupWindow(final Context context, @LayoutRes int resource) {
        //设置要显示的view
        View view = View.inflate(context, resource, null);
        //此处可按需求为各控件设置属性
        checkBox = (CheckBox) view.findViewById(R.id.checkbox);
        final PopupWindow popupWindow = new PopupWindow(view);
        //设置弹出窗口大小
        popupWindow.setWidth(WindowManager.LayoutParams.FILL_PARENT);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        //必须设置以下两项，否则弹出窗口无法取消
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        telephone = (EditText) view.findViewById(R.id.telephone);
        text = (EditText) view.findViewById(R.id.text);
        Button cancel=(Button)view.findViewById(R.id.btn_cancel);
        Button ok=(Button)view.findViewById(R.id.btn_comfirm);
        View.OnClickListener listener = new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                switch(view.getId()){
                    case R.id.btn_cancel:
                        popupWindow.dismiss();
                        break;
                    case R.id.btn_comfirm:
                        SendMessageFunction();
                        popupWindow.dismiss();
                        break;
                    default:
                        break;
                }
            }};
        cancel.setOnClickListener(listener);
        ok.setOnClickListener(listener);
        //设置动画效果
        //popupWindow.setAnimationStyle(R.style.AnimBottom);
        //设置显示位置,findViewById获取的是包含当前整个页面的view
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    private void SendMessageFunction() {
        String textText = new String();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Info",Context.MODE_PRIVATE);
        String telephoneText = telephone.getText().toString();
        if(checkBox.isChecked()){
            ArrayList<String> personInfo = new ArrayList<>();
            String classcode =  sharedPreferences.getString("classcode",null);
            String studentNum =  sharedPreferences.getString("studentNum",null);
            String college =  sharedPreferences.getString("college",null);
            String QQ = sharedPreferences.getString("QQ",null);
            String telephone = sharedPreferences.getString("telephone",null);
            String email = sharedPreferences.getString("email",null);
            String weiChat = sharedPreferences.getString("weiChat",null);
            String address = sharedPreferences.getString("address",null);
            String thisText = text.getText().toString();
            if(!classcode .equals("") ){
                personInfo.add("姓名："+classcode);
            }
            if(!studentNum.equals("")){
                personInfo.add("学号：" +studentNum);
            }
            if(!college.equals("")){
                personInfo.add("学院：" +college);
            }
            if(!QQ.equals("")){
                personInfo.add( "QQ：" +QQ);
            }
            if(!telephone.equals("")){
                personInfo.add("电话：" + telephone);
            }
            if(!email.equals("")){
                personInfo.add( "邮箱：" +email);
            }
            if(!weiChat.equals("")){
                personInfo.add("微信：" + weiChat);
            }
            if(!address.equals("")){
                personInfo.add( "地址：" +address);
            }
            textText = thisText;
            for(int i = 0; i < personInfo.size(); i++){
                textText = textText +"\n" + personInfo.get(i);
            }
        }else{
            textText = text.getText().toString();
        }
        final SmsManager smsManager = SmsManager.getDefault();
        PendingIntent pi = PendingIntent.getActivity(getActivity(),0,new Intent(),0);
        smsManager.sendTextMessage(telephoneText,null,textText,pi,null);

    }
}
