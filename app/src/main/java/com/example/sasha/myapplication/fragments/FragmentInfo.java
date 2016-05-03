package com.example.sasha.myapplication.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sasha.myapplication.R;
import com.example.sasha.myapplication.game.Game;

/**
 * Created by Sasha on 03.05.2016.
 */
public class FragmentInfo extends Fragment {
    private View mView;

    public int mNumWords;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle icicle) {
        if (mView != null)
            return mView;

        mView = inflater.inflate(R.layout.fragment_info, container, false);

        mNumWords = Game.getCurrentGame().getNumWords();

        final TextView wordsNumberView = (TextView) mView.findViewById(R.id.wordsNumber);
        wordsNumberView.setText(String.valueOf(mNumWords));

        return mView;
    }
}
