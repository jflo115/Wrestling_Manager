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

        //Armaan 20-12
        Wrestler Armaan = new Wrestler("Armaan Nikoo", 10, true);
        Armaan.getRecord().addMatch(new Match("Manno", "Newport Harbor", loss, "15-5"));
        Armaan.getRecord().addMatch(new Match("Shafer","Glendora",win,"WBF 1:39"));
        Armaan.getRecord().addMatch(new Match("Arras", "MLK", win,"17-0"));
        Armaan.getRecord().addMatch(new Match("Kalajyan","Burbank",win,"16-5"));
        Armaan.getRecord().addMatch(new Match("Singh","Victor Valley",loss,"LBF 2:45"));
        Armaan.getRecord().addMatch(new Match("Obregon","Lancaster",loss,"10-2"));
        Armaan.getRecord().addMatch(new Match("Penele","Aliso Niguel",win,"WBF 4:24"));
        Armaan.getRecord().addMatch(new Match("Josh","Dana Hills",loss,"LBF 3:07"));
        Armaan.getRecord().addMatch(new Match("Jake","Brea Olinda",win,"WBF 3:38"));
        Armaan.getRecord().addMatch(new Match("N/A","N/A",loss,"LBF 0:20"));
        Armaan.getRecord().addMatch(new Match("Chacon","Laguna Hills",win,"6-4"));
        Armaan.getRecord().addMatch(new Match("Stephens","Huntington Beach",loss,"7-5"));
        Armaan.getRecord().addMatch(new Match("Zavala","Montebello",win,"5-0"));
        Armaan.getRecord().addMatch(new Match("Arellano","Porterville",win,"7-1"));
        Armaan.getRecord().addMatch(new Match("Granados","Savanah",loss,"LBF 2:16"));
        Armaan.getRecord().addMatch(new Match("Nofal","CDM",win,"WBF 2:41"));
        Armaan.getRecord().addMatch(new Match("Antoniou","El Toro",win,"6-3"));
        Armaan.getRecord().addMatch(new Match("Yin","Portola",win,"14-2"));
        Armaan.getRecord().addMatch(new Match("Plaza","University",win,"WBF 1:36"));
        Armaan.getRecord().addMatch(new Match("Garcia","Schurr",win,"WBF 3:12"));
        Armaan.getRecord().addMatch(new Match("Hallbrook","Pioneer",win,"WBF 3:30"));
        Armaan.getRecord().addMatch(new Match("Quezada","Lakewood",win,"WBF 1:57"));
        Armaan.getRecord().addMatch(new Match("Lorenzo","Los Alamitos",win,"3-1"));
        Armaan.getRecord().addMatch(new Match("Dif","Beckman",win,"WBF 1:09"));
        Armaan.getRecord().addMatch(new Match("Shahbazyan","Chaminade",loss,"LBF"));
        Armaan.getRecord().addMatch(new Match("Bahadori","Woodbridge",win,"16-1"));
        Armaan.getRecord().addMatch(new Match("Chacon","Laguna Hills",loss,"12-5"));
        Armaan.getRecord().addMatch(new Match("Parry","Newport Harbor",win,"WBF 1:52"));
        Armaan.getRecord().addMatch(new Match("Windrath","Fountain Valley",loss,"19-7"));
        Armaan.getRecord().addMatch(new Match("Gonzalez","Gabrielno",win,"5-0"));
        Armaan.getRecord().addMatch(new Match("Autry","Granite Hills",loss,"7-5"));
        Armaan.getRecord().addMatch(new Match("Collins-Kruger","Sultana",loss,"LBF 2:25"));
        acc.add("1st Andew Pena");
        acc.add("1st Troy");
        acc.add("2nd League Finals");
        acc.add("8th CIF");
        Armaan.setAccomplishments(acc);
        acc.clear();
        roster.add(Armaan);


        Wrestler Beni = new Wrestler("Beni Magyar", 12, true);
        Beni.getRecord().addMatch(new Match("Amor","Newport Harbor",win,"WBF 2:17"));
        Beni.getRecord().addMatch(new Match("Herrera","Glendora",win,"WBF 1:12"));
        Beni.getRecord().addMatch(new Match("Avilar","MLK",win,"WBF 0:56"));
        Beni.getRecord().addMatch(new Match("Vo","Burbank",win,"WBF 0:30"));
        Beni.getRecord().addMatch(new Match("Cardona","Victor Valley",win,"WBF 0:30"));
        Beni.getRecord().addMatch(new Match("Martinez","Santiago",win,"WBF 3:29"));
        Beni.getRecord().addMatch(new Match("Alcanter","Yucaipa",win,"WBF 1:25"));
        Beni.getRecord().addMatch(new Match("Pham","Westminster",win,"6-2"));
        Beni.getRecord().addMatch(new Match("Major","Paloma Valley",win,"WBF 2:37"));
        Beni.getRecord().addMatch(new Match("McWilliams","Calvary Chapel",loss,"9-1"));
        Beni.getRecord().addMatch(new Match("Escobar","Laguna Hills",win,"WBF 1:23"));
        Beni.getRecord().addMatch(new Match("Riley","Edison",win,"WBF 0:21"));
        Beni.getRecord().addMatch(new Match("Alvarez","Mater Dei",win,"WBF 1:02"));
        Beni.getRecord().addMatch(new Match("Schrool","Peninsula",win,"WBF 0:20"));
        Beni.getRecord().addMatch(new Match("Hoodye","San Dimas",win,"WBF 0:42"));
        Beni.getRecord().addMatch(new Match("Pham","Westminster",win,"4-0"));
        Beni.getRecord().addMatch(new Match("Chavez","Sonora",win,"WBF 3:24"));
        Beni.getRecord().addMatch(new Match("Coleman","Vista",win,"WBF 1:05"));
        Beni.getRecord().addMatch(new Match("Finney","Poway",win,"WBF 0:56"));
        Beni.getRecord().addMatch(new Match("Barajas","St John Bosco",win,"6-4"));
        Beni.getRecord().addMatch(new Match("Swan","Buchanan",loss,"13-0"));
        Beni.getRecord().addMatch(new Match("McDonnell","Fountian Valley",loss,"LBF 1:59"));
        Beni.getRecord().addMatch(new Match("Kitchen","Buchanan",loss,"LBF 4:05"));
        Beni.getRecord().addMatch(new Match("Riccardi","portola",win,"WBF 2:30"));
        Beni.getRecord().addMatch(new Match("Chiou","University",win,"WBF 0:27"));
        Beni.getRecord().addMatch(new Match("Kanter","Laguna Beach",win,"WBF 2:22"));
        Beni.getRecord().addMatch(new Match("Boehme","University",win,"WBF 1:11"));
        Beni.getRecord().addMatch(new Match("Folks","Lemoore",win,"WBF 1:22"));
        Beni.getRecord().addMatch(new Match("Grant","Aliso Niguel",win,"WBF 1:49"));
        Beni.getRecord().addMatch(new Match("Ocana","Durham",win,"WBF 1:38"));
        Beni.getRecord().addMatch(new Match("Barajas","St john Bosco",loss,"LBF 5:55"));
        Beni.getRecord().addMatch(new Match("Harkey","Dana Hills",win,"WBF 0:43"));
        Beni.getRecord().addMatch(new Match("Mitrovich","Eastlake",loss,"6-4"));
        Beni.getRecord().addMatch(new Match("Soria","Chino",win,"7-1"));
        Beni.getRecord().addMatch(new Match("Yim","Beckman",win,"WBF 0:58"));
        Beni.getRecord().addMatch(new Match("Catalano","Chaminade",loss,"LBF"));
        Beni.getRecord().addMatch(new Match("Chiou","University",win,"WBF 1:44"));
        Beni.getRecord().addMatch(new Match("Gonthier","Laguna Hills",win,"WBF 1:18"));
        Beni.getRecord().addMatch(new Match("Gomez","La Sierra",win,"WBF 1:24"));
        Beni.getRecord().addMatch(new Match("Mancera","Oak Hills",win,"WBF 1:24"));
        Beni.getRecord().addMatch(new Match("Chaffee","Serrano",loss,"4-3"));
        Beni.getRecord().addMatch(new Match("Green","Burroughs",win,"WBF 1:31"));
        Beni.getRecord().addMatch(new Match("Foster","Hillcrest",win,"WBF 2:16"));
        Beni.getRecord().addMatch(new Match("Pantoja","Savanna",win,"WBF 0:41"));
        Beni.getRecord().addMatch(new Match("Phillips","Temecula Valley",loss,"LBF 3:34"));
        Beni.getRecord().addMatch(new Match("Frailey","Trabuco Hills",win,"WBF 2:23"));
        Beni.getRecord().addMatch(new Match("Toth","Great Oak",win,"WBF 2:21"));
        Beni.getRecord().addMatch(new Match("Haroun","Villa Park",loss,"10-0"));
        Beni.getRecord().addMatch(new Match("Alvarez","Mater Dei",win,"WBF 1:32"));
        Beni.getRecord().addMatch(new Match("Porter","Gilroy",win,"9-5"));
        Beni.getRecord().addMatch(new Match("Mitrovich","Eastlake",win,"4-2"));
        Beni.getRecord().addMatch(new Match("McCown","Palm Desert",loss,"LBF 4:39"));
        Beni.getRecord().addMatch(new Match("McWilliams","Calvary Chapel",loss,"3-0"));
        acc.add("2nd Mann");
        acc.add("1st Beach Bash");
        acc.add("6th TOC");
        acc.add("1st Andrew Pena");
        acc.add("7th Five Counties");
        acc.add("1st League Finals");
        acc.add("3rd CIF");
        acc.add("5th Masters");
        acc.add("Top 10 CA");
        Beni.setAccomplishments(acc);
        acc.clear();
        roster.add(Beni);

        Wrestler Joseph = new Wrestler("Joseph Tierney", 11, true);
        Wrestler Milla = new Wrestler("Milla Kulish", 9, false);
        Wrestler Lily = new Wrestler("Lily Mendez", 10, false);



        roster.add(Joseph);
        roster.add(Milla);
        roster.add(Lily);
        return roster;
    }
}
