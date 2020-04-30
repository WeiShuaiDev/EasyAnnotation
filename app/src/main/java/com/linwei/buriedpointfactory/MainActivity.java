package com.linwei.buriedpointfactory;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.linwei.annotation.Point;
import com.linwei.buriedpointfactory.runtime.RuntimeParser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent();

        apply();
    }

    @Point(key = "00", message = R.string.send_message_point)
    public void apply() {
        //增加编译时埋点
        System.out.println("point");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void click(View v) {
        try {
            //RuntimeParser.parseTypeAnnotation("com.weiyun.buriedpointfactory.runtime.RuntimePoint");
            // RuntimeParser.parseMethodAnnotation();
            RuntimeParser.parseConstructAnnotation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
