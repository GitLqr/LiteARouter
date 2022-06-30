package com.gitlqr.litearouter.androidxdemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.gitlqr.litearouter.facade.annotation.Route

@Route(path = "/root/test/fragmentx")
class TestFragmentX : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_test, null)
        rootView.setBackgroundColor(resources.getColor(R.color.purple_200))
        rootView.findViewById<TextView>(R.id.tv_test).text = "Test Fragment X"
        return rootView
    }
}