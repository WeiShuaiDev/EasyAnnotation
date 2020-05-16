package com.linwei.annotation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.linwei.annotation.utils.AnnotationUtils;

public class ThreeActivity extends AppCompatActivity {

    @BindView(R.id.mTvTitle)
    public TextView mTvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        AnnotationUtils.bind(this);

    }

    @OnClick({R.id.mBtSubmit})
    public void onClick(View view){
        if(view.getId()==R.id.mBtSubmit){
            Toast.makeText(this,"点击了",Toast.LENGTH_SHORT).show();
        }
    }
}
