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
        TwoActivity$Init activity = (TwoActivity$Init)
                new TwoActivity$Init().initActivity(this);
        String title = activity.title;
        Toast.makeText(this, title,
                Toast.LENGTH_LONG).show();

    }
}
