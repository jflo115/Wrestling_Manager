package com.example.wrestlingmanager;

import java.io.Serializable;
import java.util.ArrayList;

public class Record implements Serializable {
    private ArrayList<Match> matches;
    private int wins;
    private int losses;

    public Record() {
        this.matches = new ArrayList<Match>();
        wins = 0;
        losses = 0;
    }

    public void addWin(Match match) {
        this.matches.add(match);
        wins++;
    }

    public void addLoss(Match match) {
        this.matches.add(match);
        losses++;
    }
}
