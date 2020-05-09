package com.linwei.buriedpointfactory;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.linwei.annotation.IntentField;
import com.linwei.annotation.IntentMethod;

public class OneActivity extends AppCompatActivity {

    @IntentField("MainActivity")
    String name = "Activity";

    @IntentField("MainActivity")
    int count = 0;

    @IntentField("TwoActivity")
    String title = "OneActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
    }

    public void click(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.mBtOneMain:
                jumpOneToMainActivity();
                break;
            case R.id.mBtOneTwo:
                jumpOneToTwoActivity();
                break;
        }
    }

    @IntentMethod("MainActivity")
    public void jumpOneToMainActivity() {
        new MainActivity$Jump().jumpActivity(OneActivity.this, name, count);
    }

    @IntentMethod("TwoActivity")
    public void jumpOneToTwoActivity() {
        new TwoActivity$Jump().jumpActivity(OneActivity.this, title);
    }
}
