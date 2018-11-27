package com.example.think.notepad.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.think.notepad.Base.BaseFragment;
import com.example.think.notepad.Fragment.AddFragment;
import com.example.think.notepad.Fragment.InfoFragment;
import com.example.think.notepad.Fragment.MainFragment;
import com.example.think.notepad.Fragment.MeaageFragment;
import com.example.think.notepad.Fragment.WeatherFragment;
import com.example.think.notepad.R;

import java.util.ArrayList;
import java.util.List;

/*
* 记事本的主界面
* 由几个Fragment组成以及一个DrawableLayout的侧滑栏
* Create by Boomerr Yi
**/
public class WorkActivity extends AppCompatActivity {
    private List<BaseFragment> mBaseFragment;
    private int position;
    private Fragment mContent;
    private ProgressDialog progressDialog;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageView exit;
    public MainFragment mainFragment;
    public AddFragment addFragment;
    public InfoFragment infoFragment;
    public WeatherFragment weatherFragment;
    public MeaageFragment meaageFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        mainFragment = new MainFragment();
        addFragment = new AddFragment();
        infoFragment = new InfoFragment();
        weatherFragment = new WeatherFragment();
        meaageFragment = new MeaageFragment();
        init();
        initFragment();
    }




    private void initFragment() {
            mBaseFragment=new ArrayList<>();
            mBaseFragment.add(mainFragment);
            mBaseFragment.add(infoFragment);
            mBaseFragment.add(weatherFragment);
            mBaseFragment.add(meaageFragment);
            mBaseFragment.add( addFragment);
            Intent intent = getIntent();//获取传来的intent对象
            String data = intent.getStringExtra("userName");//获取键值对的键名
            Log.e("Boomerr---test",data);
            View header = navigationView.inflateHeaderView(R.layout.nav_header);
            TextView tv = (TextView) header.findViewById(R.id.username);
            tv.setText(data);
            final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            position = 0;
            BaseFragment initFragment=getFragment();
            switchFragment(mContent,initFragment);
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()){
                        case R.id.main:
                            position = 0;
                            drawerLayout.closeDrawers();
                            Toast.makeText(WorkActivity.this,"主页",Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.info:
                            position = 1;
                            drawerLayout.closeDrawers();
                            Toast.makeText(WorkActivity.this,"个人信息",Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.weather:
                            position = 2;
                            drawerLayout.closeDrawers();
                            Toast.makeText(WorkActivity.this,"天气",Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.message:
                            position = 3;
                            drawerLayout.closeDrawers();
                            Toast.makeText(WorkActivity.this,"消息",Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.add:
                            position = 4;
                            drawerLayout.closeDrawers();
                            Toast.makeText(WorkActivity.this,"添加事件",Toast.LENGTH_SHORT).show();
                            break;

                        default:
                            position = 0;
                            break;
                    }
                    BaseFragment to=getFragment();
                    switchFragment(mContent,to);
                    return true;
                }
            });
    }

    private void switchFragment(Fragment from,Fragment to) {
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        if(from!=to){
            mContent=to;
            if(!to.isAdded()){
                if(from!=null){
                    ft.hide(from);
                }
                if(to!=null){
                    ft.add(R.id.work_main,to).commit();
                }
            }else{
                if(from!=null){
                    ft.hide(from);
                }
                if(to!=null){
                    ft.show(to).commit();
                }
            }
        }
    }

    private BaseFragment getFragment() {
        BaseFragment fragment= mBaseFragment.get(position);
        return fragment;
    }

    private void init() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawablelayout);
        navigationView = (NavigationView)findViewById(R.id.nav_view);
        progressDialog = new ProgressDialog(WorkActivity.this);
        exit = (ImageView) findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
