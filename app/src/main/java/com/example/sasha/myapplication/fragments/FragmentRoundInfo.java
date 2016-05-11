package com.example.sasha.myapplication.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.example.sasha.myapplication.game.GameRound;

import java.util.HashMap;

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

    private static HashMap<GameRound, String> mRoundsMap;
    static {
        mRoundsMap = new HashMap<>();
        mRoundsMap.put(GameRound.ALIAS, "АЛИАС");
        mRoundsMap.put(GameRound.SHOW_OFF, "ПОКАЗУХА");
        mRoundsMap.put(GameRound.ONE_WORD_GUESS, "СЛОВО");
    }

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
//        mCircle.setText(String.valueOf(mCurrentGame.getCurrentRound()));
        mCircle.setText(mRoundsMap.get(mCurrentGame.getCurrentRound()));

        mCurrentTeam = (TextView) mView.findViewById(R.id.id_player_team);
        mCurrentTeam.setText(String.valueOf(mCurrentGame.getActiveTeam().getName()));

        mStartRoundBtn = (Button) mView.findViewById(R.id.startGameBtn);
        mStartRoundBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = getActivity().getSupportFragmentManager().getBackStackEntryAt(0).getName();
                getActivity().getSupportFragmentManager().popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE);
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
