package com.example.sasha.myapplication.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sasha.myapplication.R;

/**
 * Created by Sasha on 09.04.2016.
 */
public class FragmentMain extends Fragment {

    private View mView;
    private Button mRulesButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle icicle) {
        if (mView != null)
            return mView;

        mView = inflater.inflate(R.layout.fragment_main, container, false);

        mRulesButton = (Button) mView.findViewById(R.id.button2);
        mRulesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentRules()).addToBackStack(null).commit();
            }
        });

        return mView;
    }

}
