package com.linwei.buriedpointfactory;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.linwei.annotation.IntentField;
import com.linwei.annotation.IntentMethod;

public class SecodsActivity extends AppCompatActivity {

    @IntentField("SecodsActivity_MainActivity")
    String name = "Activity";

    @IntentField("SecodsActivity_MainActivity")
    int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secods);
    }

    @IntentMethod("SecodsActivity_MainActivity")
    public void jumpActivity() {


    }
}
