package com.example.think.notepad.Fragment;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.example.think.notepad.Base.BaseFragment;
import com.example.think.notepad.R;

public class AddFragment extends BaseFragment  {
    private TextView themeEdt;

    @Override
    protected View initView() {
        View view=View.inflate(mContext, R.layout.fragment_add,null);
        themeEdt = (TextView) view.findViewById(R.id.theme);
        themeEdt.setText("添加");
        return view;
    }

}
