package com.example.sasha.myapplication.game;

/**
 * Created by Sasha on 03.05.2016.
 */
public class Game {

    public static final int MIN_TEAMS_COUNT = 2;
    public static final int MIN_GAME_LEVEL = 1;

    private static Game sCurrentGame = null;

    private int mNumTeams;
    private int mLevel;

    public Game(int numTeams, int level) {
        mNumTeams = numTeams;
        mLevel = level;
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

}
