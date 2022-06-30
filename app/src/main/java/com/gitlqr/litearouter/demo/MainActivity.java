package com.gitlqr.litearouter.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.gitlqr.litearouter.demo.R;
import com.gitlqr.litearouter.facade.annotation.Route;
import com.gitlqr.litearouter.launcher.LiteARouter;

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