package com.example.think.notepad.Fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;

import com.example.think.notepad.Activity.WorkActivity;
import com.example.think.notepad.Adapter.FragmentAdapter;
import com.example.think.notepad.Adapter.MessageAdapter;
import com.example.think.notepad.Base.BaseFragment;
import com.example.think.notepad.Bean.message;
import com.example.think.notepad.IView;
import com.example.think.notepad.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MeaageFragment extends BaseFragment implements IView {

    private ViewPager viewPager;
    private TabLayout tabs;
    @Override
    protected View initView() {
        View view=View.inflate(mContext, R.layout.fragmentt_message,null);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
       initViewPager(view);
       CircleImageView headImage  = (CircleImageView) view.findViewById(R.id.head_image);
       headImage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               WorkActivity workActivity = (WorkActivity)getActivity();
               DrawerLayout drawerLayout =  workActivity.findViewById(R.id.drawablelayout);
               drawerLayout.openDrawer(Gravity.LEFT);
           }
       });
        return view;
    }

    private void initViewPager(View view) {
        tabs = (TabLayout) view.findViewById(R.id.tabs);
        List<String> SMS = new ArrayList<>();
        SMS.add("已发送消息");
        SMS.add("已收到消息");
        for(int i = 0;i<SMS.size();i++){
            tabs.addTab(tabs.newTab().setText(SMS.get(i)));
        }
        List<BaseFragment> listFragment = new ArrayList<>();
        listFragment.add(new SendedMessageFragment());
        listFragment.add(new ReceivedMessageFragment());
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getFragmentManager(),listFragment,SMS);
        viewPager.setAdapter(fragmentAdapter);
        tabs.setupWithViewPager(viewPager);
        tabs.setTabsFromPagerAdapter(fragmentAdapter);
    }


}
