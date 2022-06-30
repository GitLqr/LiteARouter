package com.gitlqr.litearouter.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.gitlqr.litearouter.facade.annotation.Route;
import com.gitlqr.litearouter.launcher.LiteARouter;

@Route(path = "/root/test")
public class TestActivity extends AppCompatActivity {

    private FrameLayout flContainer;
    private Fragment testFragmentV4;
    private android.app.Fragment testFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        flContainer = findViewById(R.id.fl_container);
        findViewById(R.id.btn_test_fragment_v4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (testFragmentV4 == null) {
                    testFragmentV4 = ((Fragment) LiteARouter.getInstance().build("/root/test/fragmentv4").navigation());
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fl_container_v4, testFragmentV4);
                transaction.commit();
            }
        });
        findViewById(R.id.btn_test_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (testFragment == null) {
                    testFragment = ((android.app.Fragment) LiteARouter.getInstance().build("/root/test/fragment").navigation());
                }
                android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fl_container, testFragment);
                transaction.commit();
            }
        });
    }

}
