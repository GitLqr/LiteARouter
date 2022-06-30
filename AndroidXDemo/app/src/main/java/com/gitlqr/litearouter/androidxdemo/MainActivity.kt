package com.gitlqr.litearouter.androidxdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.gitlqr.litearouter.facade.annotation.Route
import com.gitlqr.litearouter.launcher.LiteARouter

@Route(path = "/root/main")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.btn_test).setOnClickListener {
            LiteARouter.getInstance().build("/root/test").navigation()
        }
    }
}