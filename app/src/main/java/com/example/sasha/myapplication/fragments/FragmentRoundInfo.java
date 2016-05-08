package com.example.sasha.myapplication.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.sasha.myapplication.R;
import com.example.sasha.myapplication.adapters.TeamScoreAdapter;
import com.example.sasha.myapplication.events.EventType;
import com.example.sasha.myapplication.events.EventsManager;
import com.example.sasha.myapplication.game.Game;
import com.example.sasha.myapplication.models.Team;
import com.example.sasha.myapplication.models.TeamScore;

import java.util.ArrayList;

/**
 * Created by Sasha on 03.05.2016.
 */
public class FragmentRoundInfo extends Fragment implements EventsManager.EventHandler{

    private View mView;
    private ArrayList<TeamScore> mScores;
    private ListView mScoreboardListView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle icicle) {
        if (mView != null)
            return mView;

        mView = inflater.inflate(R.layout.fragment_round_info, container, false);
        mScores = new ArrayList<>();
        for (int i = 0; i < Game.getCurrentGame().getNumTeams(); ++i) {
            mScores.add(new TeamScore(new Team("Team " + String.valueOf(i + 1))));
        }
        mScoreboardListView = (ListView) mView.findViewById(R.id.scoreboard);
        mScoreboardListView.setAdapter(new TeamScoreAdapter(getActivity(), mScores));

        EventsManager.addHandler(EventType.SCORE_CHANGED, this);

        return mView;
    }

    @Override
    public void onDestroy() {
        EventsManager.unsubscribeAll(this);
        super.onDestroy();
    }

    @Override
    public void handleEvent(EventType eventType, Object eventData) {
        switch (eventType) {
            case SCORE_CHANGED:
                mScoreboardListView.invalidateViews();
        }
    }
}
