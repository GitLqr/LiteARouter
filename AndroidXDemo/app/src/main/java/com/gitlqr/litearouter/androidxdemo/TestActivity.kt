package com.gitlqr.litearouter.androidxdemo

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.gitlqr.litearouter.facade.annotation.Route
import com.gitlqr.litearouter.launcher.LiteARouter

@Route(path = "/root/test")
class TestActivity : AppCompatActivity() {

    private val testFragmentX =
        LiteARouter.getInstance().build("/root/test/fragmentx").navigation() as Fragment
    private val testFragment =
        LiteARouter.getInstance().build("/root/test/fragment").navigation() as android.app.Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        findViewById<Button>(R.id.btn_test_fragment_x).setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fl_container_x, testFragmentX)
            transaction.commit()
        }
        findViewById<Button>(R.id.btn_test_fragment).setOnClickListener {
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fl_container, testFragment)
            transaction.commit()
        }
    }
}