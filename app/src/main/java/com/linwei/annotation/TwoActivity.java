package com.linwei.annotation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.linwei.annotation.bean.UserInfo;
import com.linwei.annotation.compile.BaseFragment$Factory;
import com.linwei.annotation.compile.HomeFragment;

public class TwoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        initData();
        initView();
    }

    /**
     * 获取数据
     */
    private void initData() {
        TwoActivity$Init activity = (TwoActivity$Init)
                new TwoActivity$Init().initActivity(this);
        String title = activity.title;
        UserInfo userInfo = activity.userInfo;
        Toast.makeText(this, "title=" + title + ";username=" +
                        userInfo.username + ";password" + userInfo.password,
                Toast.LENGTH_LONG).show();
    }

    /**
     * 初始化View
     */
    private void initView() {
        HomeFragment main = (HomeFragment)
                new BaseFragment$Factory().objectFactory("Home");

        if (main != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.mFrameRoot, main, "main");
            transaction.commit();
        }
    }
}
