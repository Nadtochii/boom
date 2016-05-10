package com.example.sasha.myapplication.fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.sasha.myapplication.R;
import com.example.sasha.myapplication.game.Game;
import com.example.sasha.myapplication.game.PersonsDB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sasha on 09.04.2016.
 */

public class FragmentSettings extends Fragment {

    private View mView;
    private SeekBar mLevel;
    private SeekBar mTeamNumbers;

    private Button mNextButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle icicle) {
        if (mView != null)
            return mView;

        mView = inflater.inflate(R.layout.fragment_settings, container, false);

        final TextView teamsTextView = (TextView) mView.findViewById(R.id.teamsTextView);
        mTeamNumbers = (SeekBar) mView.findViewById(R.id.teamsSeekBar);
        mTeamNumbers.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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

        final TextView levelTextView = (TextView) mView.findViewById(R.id.levelTextView);
        mLevel = (SeekBar) mView.findViewById(R.id.levelSeekBar);
        mLevel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                levelTextView.setText(String.valueOf(Game.MIN_GAME_LEVEL + progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mNextButton = (Button) mView.findViewById(R.id.nextButton);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int level = mLevel.getProgress() + Game.MIN_GAME_LEVEL;
                int teamsCount = mTeamNumbers.getProgress() + Game.MIN_TEAMS_COUNT;
                Game.setCurrentGame(new Game(teamsCount, level,
                        PersonsDB.getPersons(level, teamsCount * 10)));  // TODO: move 10 to consts
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentInfo()).addToBackStack(null).commit();
            }
        });

        return mView;

    }
}
