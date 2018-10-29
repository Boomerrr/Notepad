package com.example.think.notepad.Fragment;

import android.view.View;

import com.example.think.notepad.Base.BaseFragment;
import com.example.think.notepad.R;

public class InfoFragment extends BaseFragment {
    @Override
    protected View initView() {
        View view=View.inflate(mContext, R.layout.fragment_info,null);
        return view;
    }
}
