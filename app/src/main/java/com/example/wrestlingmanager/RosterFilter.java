package com.example.wrestlingmanager;

import java.util.ArrayList;

/*
 * Class RosterFilter contains two methods, filters for Rosters to filter out any gender of wrestlers.
 */
public class RosterFilter {
    public RosterFilter(){

    }

    public static ArrayList<Wrestler> filterGirls(ArrayList<Wrestler> roster) {
        ArrayList<Wrestler> filtered = new ArrayList<Wrestler>();
        for(Wrestler wrestler : roster) {
            if(!wrestler.getGender()) {
                filtered.add(wrestler);
            }
        }
        return filtered;
    }

    public static ArrayList<Wrestler> filterBoys(ArrayList<Wrestler> roster) {
        ArrayList<Wrestler> filtered = new ArrayList<Wrestler>();
        for(Wrestler wrestler : roster) {
            if(wrestler.getGender()) {
                filtered.add(wrestler);
            }
        }
        return filtered;
    }


}
