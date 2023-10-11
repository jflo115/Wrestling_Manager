package com.example.wrestlingmanager;

import android.util.Log;

import androidx.room.TypeConverter;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class WrestlerConverters {
    @TypeConverter
    public static ArrayList<String> fromString(String value) {
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(value,listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
    @TypeConverter
    public Record StringToRecord(String str) {
        Record rec = new Record();
        int ind;
        String name;
        String school;
        Boolean win;
        String score;
        if(str == null){
            Log.d("oops","error");
            return null;
        }
        while(!str.equals("")){
            ind = str.indexOf(",");
            name = str.substring(0,ind);
            str = str.substring(ind+1);
            ind = str.indexOf(",");
            school = str.substring(0,ind);
            str = str.substring(ind+1);
            ind = str.indexOf(",");
            win = Boolean.parseBoolean(str.substring(0,ind));
            str = str.substring(ind+1);
            ind = str.indexOf("\n");
            score = str.substring(0,ind);
            str = str.substring(ind+1);
            rec.addMatch(new Match(name,school,win,score));
        }
        return rec;
    }

    @TypeConverter
    public String RecordToString(Record record) {
        ArrayList<Match> arr = record.getMatches();
        StringBuilder sb = new StringBuilder();
        for(Match m : arr){
            sb.append(m.getOpponent() + ",");
            sb.append(m.getSchool() + ",");
            sb.append(Boolean.toString(m.getWin()) + ",");
            sb.append(m.getScore() + "\n");
        }
        return sb.toString();
    }
}
