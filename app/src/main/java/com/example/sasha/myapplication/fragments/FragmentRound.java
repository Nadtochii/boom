package com.example.sasha.myapplication.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sasha.myapplication.R;
import com.example.sasha.myapplication.game.Game;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Sasha on 08.05.2016.
 */
public class FragmentRound extends Fragment {

    private View mView;

    private Button mGuessed;
    private TextView mPerson;

    private Timer mTimer;
    private TimerTask mTimerTask;

    private ArrayList<String> mPersonsInGame;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle icicle) {
        if (mView != null)
            return mView;

        mView = inflater.inflate(R.layout.fragment_round, container, false);

        mPersonsInGame = Game.getCurrentGame().getPersons();
//        mGuessed = (Button) mView.findViewById(R.id.guessed);
//        mGuessed.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startTimer();
//            }
//        });

        startTimer();
        mPerson = (TextView) mView.findViewById(R.id.person);
        mPerson.setText(mPersonsInGame.get(3));
        return mView;
    }

    public void initTimerTask() {
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentRoundInfo()).addToBackStack(null).commit();
                    }
                });
            }
        };
    }

    public void startTimer() {
        mTimer = new Timer();
        initTimerTask();
        mTimer.schedule(mTimerTask, Game.ROUND_TIME);
    }

}
