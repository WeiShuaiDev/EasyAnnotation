package com.linwei.annotation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.linwei.annotation.bean.UserInfo;

public class OneActivity extends AppCompatActivity {

    //@IntentField("MainActivity")
    String name = "Activity";

    //@IntentField("MainActivity")
    int count = 0;

    @IntentField("TwoActivity")
    String title = "OneActivity";

    @IntentField("TwoActivity")
    UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
    }

    public void click(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.mBtOneMain:
                jumpOneToMainActivity(name, count, "title");
                break;
            case R.id.mBtOneTwo:
                jumpOneToTwoActivity();
                break;
        }
    }

    @IntentMethod("MainActivity")
    public void jumpOneToMainActivity(@IntentParameter("MainActivity") String name,
                                      @IntentParameter("MainActivity") int count,
                                      String title) {
        new MainActivity$Jump().jumpActivity(OneActivity.this, name, count);
    }

    @IntentMethod("TwoActivity")
    public void jumpOneToTwoActivity() {
        userInfo = new UserInfo("username", "password");
        new TwoActivity$Jump().jumpActivity(OneActivity.this, title, userInfo);
    }
}
