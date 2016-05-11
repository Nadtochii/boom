package com.example.sasha.myapplication.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.os.Handler;

import com.example.sasha.myapplication.R;
import com.example.sasha.myapplication.events.EventsManager;
import com.example.sasha.myapplication.game.Game;
import com.example.sasha.myapplication.models.Team;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
//import java.util.logging.Handler;

/**
 * Created by Sasha on 08.05.2016.
 */
public class FragmentRound extends Fragment {

    private View mView;

    private Handler timeHandler = new Handler();

    private Button mGuessed;
    private Button mNotGuessed;
    private TextView mPerson;

    private Timer mTimer;
    private TimerTask mTimerTask;
    private TextView mTimerView;
    private CountDownTimer mCountDownTimer;

    long timeInMilliseconds = 0L;
    private long startTime = 0L;

    private ArrayList<String> mPersonsInGame;

    private Game currentGame = Game.getCurrentGame();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle icicle) {
        if (mView != null)
            return mView;

        startTime = SystemClock.uptimeMillis();
        timeHandler.postDelayed(updateTimerThread, 0);

        mView = inflater.inflate(R.layout.fragment_round, container, false);
        mTimerView = (TextView) mView.findViewById(R.id.timer_view);
//        mPersonsInGame = Game.getCurrentGame().getPersons();

        startTimer();
        mPerson = (TextView) mView.findViewById(R.id.person);
        mPerson.setText(currentGame.getCurrentPerson());

        mGuessed = (Button) mView.findViewById(R.id.guessed);
        mNotGuessed = (Button) mView.findViewById(R.id.not_guessed);

        mGuessed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentGame.onPersonGuessed();
                if (!currentGame.isGameFinished() && !currentGame.isRoundFinished()) {
                    mPerson.setText(currentGame.getCurrentPerson());
                }

                if (currentGame.isRoundFinished()) {
                    mTimer.cancel();
                    mCountDownTimer.cancel();
                    if (currentGame.isGameFinished()) {
                        //show winner
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentWinners()).addToBackStack(null).commit();
                    } else {
                        currentGame.rotateActiveTeam();
                        currentGame.startNextRound();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentRoundInfo()).addToBackStack(null).commit();
                    }
                }
            }
        });

        int oneSecond = 1000;
        mCountDownTimer = new CountDownTimer(Game.ROUND_TIME + oneSecond / 2, oneSecond / 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeHandler.postDelayed(updateTimerThread, 0);
            }

            @Override
            public void onFinish() {
                timeHandler.postDelayed(updateTimerThread, 0);
            }
        };
        mCountDownTimer.start();

        return mView;
    }

    public void initTimerTask() {
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timeHandler.postDelayed(updateTimerThread, 0);

                        mNotGuessed.setVisibility(View.VISIBLE);
                        mNotGuessed.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //person was not guessed
                                currentGame.onPersonNotGuessed();
                                currentGame.rotateActiveTeam();
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentRoundInfo()).addToBackStack(null).commit();
                            }
                        });

                        mGuessed.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //person was guessed
                                currentGame.onPersonGuessed();
                                if (!currentGame.isGameFinished() && !currentGame.isRoundFinished()) {
                                    currentGame.rotateActiveTeam();
                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentRoundInfo()).addToBackStack(null).commit();
                                }

                                if (currentGame.isRoundFinished()) {
                                    if (currentGame.isGameFinished()) {
                                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentWinners()).addToBackStack(null).commit();
                                    } else {
                                        currentGame.rotateActiveTeam();
                                        currentGame.startNextRound();
                                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentRoundInfo()).addToBackStack(null).commit();
                                    }
                                }
                            }
                        });
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

    private Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            int seconds = (int) (timeInMilliseconds / 1000);
            seconds = seconds % 60;
            mTimerView.setText(String.format("%02d", seconds));
        }
    };
}
