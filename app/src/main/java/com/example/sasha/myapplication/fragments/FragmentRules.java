package com.example.sasha.myapplication.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sasha.myapplication.R;

/**
 * Created by Sasha on 09.04.2016.
 */
public class FragmentRules extends Fragment {

    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle icicle) {
        if (mView != null)
            return mView;

        mView = inflater.inflate(R.layout.fragment_rules, container, false);
        return mView;
    }

}
