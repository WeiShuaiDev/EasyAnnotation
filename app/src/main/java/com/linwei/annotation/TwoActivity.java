package com.linwei.annotation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.linwei.annotation.bean.UserInfo;

public class TwoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        initData();
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
}
