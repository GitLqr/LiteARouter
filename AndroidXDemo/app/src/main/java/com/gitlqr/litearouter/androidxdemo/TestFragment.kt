package com.gitlqr.litearouter.androidxdemo

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.gitlqr.litearouter.facade.annotation.Route

@Route(path = "/root/test/fragment")
class TestFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_test, null)
        rootView.setBackgroundColor(resources.getColor(R.color.teal_200))
        rootView.findViewById<TextView>(R.id.tv_test).text = "Test Fragment"
        return rootView
    }
}