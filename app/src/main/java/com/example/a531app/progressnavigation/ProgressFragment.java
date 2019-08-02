package com.example.a531app.progressnavigation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a531app.R;
import com.example.a531app.utilities.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProgressFragment extends BaseFragment {


    public ProgressFragment() {
        // Required empty public constructor
    }

    @Override
    public View providedView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, container, false);
        return view;
    }

    @Override
    public String actionTitle() {
        return "Training max progression";
    }

    @Override
    public boolean setBackButton() {
        return false;
    }

}
