package com.example.sasha.myapplication.game;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by Sasha on 03.05.2016.
 */
public class Game {

    public static final int MIN_TEAMS_COUNT = 2;
    public static final int MIN_GAME_LEVEL = 1;
    public static final int ROUND_TIME = 5000;

    private static Game sCurrentGame = null;

    private int mNumTeams;
    private int mLevel;
    private ArrayList<String> mPersons;

    public ArrayList<String> mPersonsIn;
    public ArrayList<String> mPersonsOut;


    public Game(int numTeams, int level, ArrayList<String> persons) {
        mNumTeams = numTeams;
        mLevel = level;

        Collections.shuffle(persons);
        mPersons = new ArrayList<>(persons.subList(0, 5));
    }

    public int getNumWords() {
        return mNumTeams*10;
    }

    public int getLevel() {
        return mLevel;
    }

    public int getNumTeams() {
        return mNumTeams;
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
}
