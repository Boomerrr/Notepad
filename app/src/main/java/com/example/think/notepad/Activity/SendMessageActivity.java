package com.example.think.notepad.Activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.think.notepad.R;

public class SendMessageActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText send_to_phone;
    private EditText send_text;
    private Button send;
    private Button cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        init();
    }

    private void init() {
        send_to_phone = (EditText) findViewById(R.id.send_to_phone);
        send_text = (EditText) findViewById(R.id.send_text);
        send = (Button) findViewById(R.id.send);
        cancel = (Button) findViewById(R.id.cancel);
        send.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.send:
                SendMessage();
                break;
            case R.id.cancel:
                Toast.makeText(SendMessageActivity.this,"取消发送",Toast.LENGTH_SHORT).show();
                finish();
                break;
            default:
                break;
        }
    }

    private void SendMessage() {
        final SmsManager smsManager = SmsManager.getDefault();
        PendingIntent pi = PendingIntent.getActivity(SendMessageActivity.this,0,new Intent(),0);
        smsManager.sendTextMessage(send_to_phone.getText().toString(),null,send_text.getText().toString(),pi,null);
        Toast.makeText(SendMessageActivity.this,"短信发送成功",Toast.LENGTH_SHORT).show();
    }
}
