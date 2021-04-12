package com.charylin.litearouter.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.charylin.litearouter.demo.R;
import com.charylin.litearouter.facade.annotation.Route;
import com.charylin.litearouter.launcher.LiteARouter;

@Route(path = "/root/main")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LiteARouter.getInstance().build("/root/test").navigation();
            }
        });
    }
}