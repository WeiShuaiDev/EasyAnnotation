package com.linwei.buriedpointfactory;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.linwei.annotation.IntentField;
import com.linwei.annotation.IntentMethod;

public class OneActivity extends AppCompatActivity {

    @IntentField("OneActivity_MainActivity")
    String name = "Activity";

    @IntentField("OneActivity_MainActivity")
    int count = 0;

    @IntentField("OneActivity_TwoActivity")
    String title = "OneActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
    }

    public void click(View view){
        int id = view.getId();
        switch (id){
            case R.id.mBtOneMain:
                jumpOneToMainActivity();
                break;
            case R.id.mBtOneTwo:
                jumpOneToTwoActivity();
                break;
        }
    }

    @IntentMethod("OneActivity_MainActivity")
    public void jumpOneToMainActivity() {
        new OneActivity_MainActivity().jumpEnterActivity(
                OneActivity.this,name,count);
    }

    @IntentMethod("OneActivity_TwoActivity")
    public void jumpOneToTwoActivity() {
        new OneActivity_TwoActivity().jumpEnterActivity(
                OneActivity.this,title);
    }
}
