package com.example.wrestlingmanager;


import java.util.ArrayList;

//This Class is Meant to fill out Roster for testing purposes without flooding main activity with code
public class TestRosterFill {
    public TestRosterFill(){

    }

    public ArrayList<Wrestler> create() {
        ArrayList<Wrestler> roster = new ArrayList<Wrestler>();
        boolean win = true;
        boolean loss = false;

        //Ahmad  1-0
        Wrestler Ahmad = new Wrestler("Ahmad Saleh", 9, true);
        Ahmad.getRecord().addMatch(new Match("Nipp","University",win,"WBF 1:46"));
        roster.add(Ahmad);

        //Charlie 8-4
        Wrestler Charlie = new Wrestler("Charlie Petersen", 10, false);
        Charlie.getRecord().addMatch(new Match("Flores","University",win,"WBF 1:19"));
        Charlie.getRecord().addMatch(new Match("Leoung","Beckman",win,"WBF"));
        Charlie.getRecord().addMatch(new Match("Shen","Rowland",win,"WBF 5:15"));
        Charlie.getRecord().addMatch(new Match("Kraynak","AB Miller", loss, "LBF 0:55"));
        Charlie.getRecord().addMatch(new Match("Hernandez","San Pedro",win,"WBF"));
        Charlie.getRecord().addMatch(new Match("Brent","Brea Olinda",loss,"LBF"));
        Charlie.getRecord().addMatch(new Match("Bhatia","Portola",win,"WBF 1:07"));
        Charlie.getRecord().addMatch(new Match("Flores","Laguna Hills",win,"WBF 3:45"));
        Charlie.getRecord().addMatch(new Match("Tarin","Upland",win,"WBF 5:40"));
        Charlie.getRecord().addMatch(new Match("Zuniga","Sonora",win,"WBF 3:26"));
        Charlie.getRecord().addMatch(new Match("Muniz","Hillcrest",loss,"LBF 0:50"));
        Charlie.getRecord().addMatch(new Match("Laabs","Villa Park",loss,"LBF 4:21"));
        ArrayList<String> acc = new ArrayList<String>();
        acc.add("8th CIF (2022)");
        Charlie.setAccomplishments(acc);
        roster.add(Charlie);
        acc.clear();

        //Rei 7-5
        Wrestler Rei = new Wrestler("Rei Ikeda", 11, false);
        Rei.getRecord().addMatch(new Match("Kennedy","Eastlake",win,"5-3"));
        Rei.getRecord().addMatch(new Match("Felix","El Toro",loss,"LBF"));
        Rei.getRecord().addMatch(new Match("Comia","Long Beach Poly",win,"WBF"));
        Rei.getRecord().addMatch(new Match("Navarro","Shadow Hills",loss,"LBF"));
        Rei.getRecord().addMatch(new Match("Nguyen","Laguna Hills",loss,"LBF"));
        Rei.getRecord().addMatch(new Match("Ochoa","MLK",win,"WBF 0:51"));
        Rei.getRecord().addMatch(new Match("Nguyen","Laguna Hills",win,"6-4"));
        Rei.getRecord().addMatch(new Match("Trevino","Yucca Valley",win,"WBF 1:35"));
        Rei.getRecord().addMatch(new Match("Lopez-Garcia","Vista Murrieta",win,"WBF 0:29"));
        Rei.getRecord().addMatch(new Match("Baca","La Quinta",loss,"LBF 5:21"));
        Rei.getRecord().addMatch(new Match("Morton","Trabuco Hills", win, "7-2"));
        Rei.getRecord().addMatch(new Match("Nguyen","Laguna Hills", loss, "LBF 1:25"));
        acc.add("7th SA LS");
        acc.add("3rd Edison");
        Rei.setAccomplishments(acc);
        acc.clear();
        roster.add(Rei);

        Wrestler Armaan = new Wrestler("Armaan Nikoo", 10, true);
        Wrestler Beni = new Wrestler("Beni Magyar", 12, true);
        Wrestler Joseph = new Wrestler("Joseph Tierney", 11, true);
        Wrestler Milla = new Wrestler("Milla Kulish", 9, false);
        Wrestler Lily = new Wrestler("Lily Mendez", 10, false);


        roster.add(Armaan);
        roster.add(Beni);
        roster.add(Joseph);
        roster.add(Milla);
        roster.add(Lily);
        return roster;
    }
}
