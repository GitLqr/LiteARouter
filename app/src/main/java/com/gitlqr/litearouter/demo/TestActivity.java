package com.gitlqr.litearouter.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.gitlqr.litearouter.demo.R;
import com.gitlqr.litearouter.facade.annotation.Route;

@Route(path = "/root/test")
public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }
}
