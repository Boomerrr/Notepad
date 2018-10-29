package com.example.think.notepad.Fragment;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.think.notepad.Adapter.MessageAdapter;
import com.example.think.notepad.Base.BaseFragment;
import com.example.think.notepad.Bean.message;
import com.example.think.notepad.R;

import java.util.ArrayList;
import java.util.List;

public class MeaageFragment extends BaseFragment {
    private List<message> messageList = new ArrayList<>();
    @Override
    protected View initView() {
        View view=View.inflate(mContext, R.layout.fragmentt_message,null);
        init();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyceler_message);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        MessageAdapter messageAdapter = new MessageAdapter(messageList);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(messageAdapter);
        return view;
    }

    private void init() {
        for(int i = 0; i <20;i++){
            message msg = new message();
            msg.setSender("ybc");
            msg.setText("哈哈哈哈哈");
            msg.setTime("10:10");
            messageList.add(msg);
        }
    }
}
