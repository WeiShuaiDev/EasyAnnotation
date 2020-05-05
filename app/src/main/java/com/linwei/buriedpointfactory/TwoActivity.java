package com.linwei.buriedpointfactory;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

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
//        OneActivity_TwoActivity activity =
//                (OneActivity_TwoActivity) new OneActivity_TwoActivity().jumpOutActivity(this);
//        String title = activity.title;
//        Toast.makeText(this,title,
//                Toast.LENGTH_LONG).show();
    }
}
