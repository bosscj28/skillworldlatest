package com.shanks.myapp.adapter;

/**
 * Created by lenovo on 02-07-2017.
 */

public class Team {
    private String teamName;
    private int teamWins;
    public  Team(String name, int wins)
    {
        teamName = name;
        teamWins = wins;
    }

    public String getTeamName() {
        return teamName;
    }

    public int getTeamWins() {
        return teamWins;
    }
}

