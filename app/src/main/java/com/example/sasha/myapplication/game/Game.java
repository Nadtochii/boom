package com.example.sasha.myapplication.game;

import com.example.sasha.myapplication.events.EventType;
import com.example.sasha.myapplication.events.EventsManager;
import com.example.sasha.myapplication.models.Team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sasha on 03.05.2016.
 */
public class Game {

    public static final int MIN_TEAMS_COUNT = 2;
    public static final int MIN_GAME_LEVEL = 1;
    public static final int ROUND_TIME = 5000;
    public static final int PERSONS_PER_TEAM = 10;

    private static Game sCurrentGame = null;

    private int mLevel;
    private ArrayList<Team> mTeams;
    private HashMap<Team, Integer> mTeamScores;
    private ArrayList<String> mPersons;
    private ArrayList<String> mPersonsIn;

    private int mActiveTeamIndex;
    private GameRound mGameRound;

    public Game(int numTeams, int level, ArrayList<String> persons) {
        mLevel = level;
        mTeams = new ArrayList<>();
        mTeamScores = new HashMap<>();
        for (int i = 0; i < numTeams; ++i) {
            Team team = new Team("Team " + String.valueOf(i + 1));
            mTeams.add(team);
            mTeamScores.put(team, 0);
        }
        mActiveTeamIndex = 0;

        mPersons = persons;
        mPersonsIn = new ArrayList<>(mPersons);

        mGameRound = GameRound.ALIAS;
    }

    public int getNumWords() {
        return mPersons.size();
    }

    public int getLevel() {
        return mLevel;
    }

    public int getNumTeams() {
        return mTeams.size();
    }

    public static Game getCurrentGame() {
        return sCurrentGame;
    }

    public static void setCurrentGame(Game game) {
        sCurrentGame = game;
    }

    public ArrayList<String> getPersons() {
        return mPersons;
    }

    public boolean isRoundFinished() {
        return mPersonsIn.size() < 1;
    }

    public boolean isGameFinished() {
        return isRoundFinished() && mGameRound == GameRound.ONE_WORD_GUESS;
    }

    public void startNextRound() {
        assert mGameRound != GameRound.ONE_WORD_GUESS : "Last round already finished";

        switch (mGameRound) {
            case ALIAS: mGameRound = GameRound.SHOW_OFF;
                break;
            case SHOW_OFF: mGameRound = GameRound.ONE_WORD_GUESS;
                break;
        }

        mPersonsIn = new ArrayList<>(mPersons);
        Collections.shuffle(mPersonsIn);
    }

    public String getCurrentPerson() {
        return mPersonsIn.get(0);
    }

    public void onPersonGuessed() {
        String person = mPersonsIn.remove(0);
        Team activeTeam = getActiveTeam();
        int score = getTeamScore(activeTeam);
        mTeamScores.put(activeTeam, score + 1);

        EventsManager.dispatchEvent(EventType.SCORE_CHANGED, null);
    }

    public void onPersonNotGuessed() {
        String person = mPersonsIn.remove(0);
        mPersonsIn.add(person);
    }

    public Team getActiveTeam() {
        return getTeam(mActiveTeamIndex);
    }

    public void rotateActiveTeam() {
        mActiveTeamIndex = (mActiveTeamIndex + 1) % getNumTeams();
    }

    public Team getTeam(int index) {
        return mTeams.get(index);
    }

    public int getTeamScore(Team team) {
        return mTeamScores.get(team);
    }

    public ArrayList<Team> getWinners() {
        int maxScore = -1;
        ArrayList<Team> winners = new ArrayList<>();
        for (Map.Entry<Team, Integer> entry: mTeamScores.entrySet()) {
            Team team = entry.getKey();
            int score = entry.getValue();

            if (score > maxScore) {
                winners.clear();
                maxScore = score;
            }

            if (score == maxScore) {
                winners.add(team);
            }
        }
        return winners;
    }
}
