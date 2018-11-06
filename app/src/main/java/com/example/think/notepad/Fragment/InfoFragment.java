package com.example.think.notepad.Fragment;

import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;

import com.example.think.notepad.Activity.WorkActivity;
import com.example.think.notepad.Base.BaseFragment;
import com.example.think.notepad.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class InfoFragment extends BaseFragment {
    @Override
    protected View initView() {
        View view=View.inflate(mContext, R.layout.fragment_info,null);
        CircleImageView headIamge = (CircleImageView) view.findViewById(R.id.head_image);
        headIamge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkActivity workActivity = (WorkActivity)getActivity();
                DrawerLayout drawerLayout =  workActivity.findViewById(R.id.drawablelayout);
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        return view;
    }
}
