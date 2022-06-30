package com.gitlqr.litearouter.demo;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gitlqr.litearouter.facade.annotation.Route;

@Route(path = "/root/test/fragment")
public class TestFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_test, null);
        rootView.setBackgroundColor(getResources().getColor(R.color.teal_200));
        ((TextView) rootView.findViewById(R.id.tv_test)).setText("Test Fragment");
        return rootView;
    }
}

