package com.example.sasha.myapplication.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sasha.myapplication.R;
import com.example.sasha.myapplication.adapters.TeamScoreAdapter;
import com.example.sasha.myapplication.events.EventType;
import com.example.sasha.myapplication.events.EventsManager;
import com.example.sasha.myapplication.game.Game;

/**
 * Created by Sasha on 03.05.2016.
 */
public class FragmentRoundInfo extends Fragment implements EventsManager.EventHandler{

    private View mView;
    private ListView mScoreboardListView;
    private Button mStartRoundBtn;

    private TextView mCircle;
    private TextView mCurrentTeam;

    private Game mCurrentGame;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle icicle) {
        if (mView != null)
            return mView;

        mCurrentGame = Game.getCurrentGame();
        mView = inflater.inflate(R.layout.fragment_round_info, container, false);
        mScoreboardListView = (ListView) mView.findViewById(R.id.scoreboard);
        mScoreboardListView.setAdapter(new TeamScoreAdapter(getActivity()));

        EventsManager.addHandler(EventType.SCORE_CHANGED, this);

        mCircle = (TextView) mView.findViewById(R.id.id_circle);
        mCurrentTeam = (TextView) mView.findViewById(R.id.id_player_team);

        mCircle.setText(String.valueOf(mCurrentGame.getCurrentRound()));
        mCurrentTeam.setText(String.valueOf(mCurrentGame.getActiveTeam().getName()));

        mStartRoundBtn = (Button) mView.findViewById(R.id.startGameBtn);
        mStartRoundBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentRound()).addToBackStack(null).commit();
            }
        });

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
                ((TeamScoreAdapter) mScoreboardListView.getAdapter()).notifyDataSetChanged();
        }
    }
}
