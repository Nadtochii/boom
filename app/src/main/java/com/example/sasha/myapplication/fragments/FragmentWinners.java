package com.example.sasha.myapplication.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sasha.myapplication.R;
import com.example.sasha.myapplication.adapters.TeamScoreAdapter;
import com.example.sasha.myapplication.game.Game;
import com.example.sasha.myapplication.models.Team;

import java.util.ArrayList;

/**
 * Created by Sasha on 11.05.2016.
 */
public class FragmentWinners extends Fragment {
    private View mView;
    private ListView mScoreBoard;
    private Game mCurrentGame;
    private ArrayList<Team> mWinners;
    private TextView mWinnersList;
    private Button mNewGame;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle icicle) {
        if (mView != null)
            return mView;

        mView = inflater.inflate(R.layout.fragment_winners, container, false);

        mCurrentGame = Game.getCurrentGame();
        mWinners = mCurrentGame.getWinners();
        mWinnersList = (TextView) mView.findViewById(R.id.winners);
        mScoreBoard = (ListView) mView.findViewById(R.id.end_score_board);
        mScoreBoard.setAdapter(new TeamScoreAdapter(getActivity()));

        String winners = "";
        for (int i = 0; i < mWinners.size(); i++) {
            winners = winners + String.valueOf(mWinners.get(i).getName()) + "\n";
        }
        mWinnersList.setText(winners);

        mNewGame = (Button) mView.findViewById(R.id.startNewGame);
        mNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = getActivity().getSupportFragmentManager().getBackStackEntryAt(0).getName();
                getActivity().getSupportFragmentManager().popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentMain()).addToBackStack(null).commit();
            }
        });

        return mView;
    }
}
