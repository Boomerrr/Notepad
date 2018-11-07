package com.example.think.notepad.Fragment;

import android.animation.ObjectAnimator;
import android.provider.ContactsContract;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.think.notepad.Activity.WorkActivity;
import com.example.think.notepad.Adapter.NotepadeAdapter;
import com.example.think.notepad.Base.BaseFragment;
import com.example.think.notepad.Bean.NotePad;
import com.example.think.notepad.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainFragment extends BaseFragment{
    private List<NotePad> notePadList = new ArrayList<>();

    private float mFirstY;
    private float mCurrentY;
    private int direction;
    private ObjectAnimator mAnimator;
    private boolean mShow = true;
    private Toolbar mToolbar;
    private int mTouchSlop;



    @Override
    protected View initView() {
        View view=View.inflate(mContext, R.layout.fragment_main,null);
        init();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycelr_main);
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.relativelayout);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        NotepadeAdapter notepadeAdapter = new NotepadeAdapter(notePadList);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(notepadeAdapter);
        mTouchSlop = ViewConfiguration.get(getActivity()).getScaledTouchSlop();
        CircleImageView headImage = (CircleImageView) view.findViewById(R.id.head_image);
        headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkActivity workActivity = (WorkActivity)getActivity();
                DrawerLayout drawerLayout =  workActivity.findViewById(R.id.drawablelayout);
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
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



    private void toolbarAnim(int flag) {
        if (mAnimator != null && mAnimator.isRunning()) {
            mAnimator.cancel();
        }
        if (flag == 0) {
            mAnimator = ObjectAnimator.ofFloat(mToolbar,
                    "translationY", mToolbar.getTranslationY(), 0);
        } else {
            mAnimator = ObjectAnimator.ofFloat(mToolbar,
                    "translationY", mToolbar.getTranslationY(),
                    -mToolbar.getHeight());
        }
        mAnimator.start();
    }
}
