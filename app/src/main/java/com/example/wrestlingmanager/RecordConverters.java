package com.example.wrestlingmanager;

import androidx.room.ProvidedTypeConverter;
import androidx.room.TypeConverter;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class RecordConverters {

   /* @TypeConverter
    public static ArrayList<String> fromString(String value) {
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(value,listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }*/

    @TypeConverter
    public ArrayList<Match> StringToMatchList(String str) {
        Type listType = new TypeToken<ArrayList<Match>>() {}.getType();
        return new Gson().fromJson(str,listType);
    }

    @TypeConverter
    public String MatchListToString(ArrayList<Match> matches) {
        Gson gson = new Gson();
        String json = gson.toJson(matches);
        return json;
    }


    /*@TypeConverter
    public Wrestler StringToWrestler(String str){return null;}

    @TypeConverter
    public String WrestlerToString(Wrestler wrestler){return null;}

    @TypeConverter
    public Record StringToRecord(String str) {
        return null;
    }

    @TypeConverter
    public String RecordToString(Record record) {
        return null;
    }

    @TypeConverter
    public Match StringToMatch(String str) {
        return null;
    }

    @TypeConverter
    public String MatchToString(Match match) {
        return null;
    }*/




}
