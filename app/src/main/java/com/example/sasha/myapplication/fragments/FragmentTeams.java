package com.example.sasha.myapplication.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.sasha.myapplication.R;
import com.example.sasha.myapplication.game.Game;

/**
 * Created by Sasha on 09.04.2016.
 */

public class FragmentTeams extends Fragment {

    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle icicle) {
        if (mView != null)
            return mView;

        mView = inflater.inflate(R.layout.fragment_teams, container, false);

        final TextView teamsTextView = (TextView) mView.findViewById(R.id.teamsTextView);
        SeekBar numTeamsSeekBar = (SeekBar) mView.findViewById(R.id.teamsSeekBar);
        numTeamsSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                teamsTextView.setText(String.valueOf(Game.MIN_TEAMS_COUNT + progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return mView;

        //Game.getCurrentGame().setNumTeams(4);
    }
}
