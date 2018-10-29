package com.example.think.notepad.Fragment;

import android.provider.ContactsContract;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.think.notepad.Adapter.NotepadeAdapter;
import com.example.think.notepad.Base.BaseFragment;
import com.example.think.notepad.Bean.NotePad;
import com.example.think.notepad.R;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends BaseFragment{
    private List<NotePad> notePadList = new ArrayList<>();
    @Override
    protected View initView() {
        View view=View.inflate(mContext, R.layout.fragment_main,null);
        init();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycelr_main);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        NotepadeAdapter notepadeAdapter = new NotepadeAdapter(notePadList);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(notepadeAdapter);
        return view;
    }

    private void init() {
        for(int i = 0;i < 20;i++){
            NotePad notePad = new NotePad();
            notePad.setOrderTime("10:10");
            notePad.setText("没啥事没啥事");
            notePad.setTitle("睡觉");
            notePadList.add(notePad);
        }
    }

}
