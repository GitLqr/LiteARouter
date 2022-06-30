package com.gitlqr.litearouter.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gitlqr.litearouter.facade.annotation.Route;

@Route(path = "/root/test/fragmentv4")
public class TestFragmentV4 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_test, null);
        rootView.setBackgroundColor(getResources().getColor(R.color.purple_200));
        ((TextView) rootView.findViewById(R.id.tv_test)).setText("Test Fragment V4");
        return rootView;
    }
}
