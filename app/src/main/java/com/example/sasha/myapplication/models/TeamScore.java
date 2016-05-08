package com.example.sasha.myapplication.models;

/**
 * Created by Sasha on 08.05.2016.
 */
public class TeamScore {

    private Team mTeam;
    private int mScore = 0;

    public TeamScore(Team team) {
        mTeam = team;
    }

    public Team getTeam() {
        return mTeam;
    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int newScore) {
        mScore = newScore;
    }
}
