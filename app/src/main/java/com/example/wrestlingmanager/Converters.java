package com.example.wrestlingmanager;

import androidx.room.ProvidedTypeConverter;
import androidx.room.TypeConverter;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

@ProvidedTypeConverter
public class Converters {
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
    }

    @TypeConverter
    public ArrayList<Match> StringToMatchList(String str) {
        return null;
    }

    @TypeConverter
    public String MatchListToString(ArrayList<Match> matches) {
        return null;
    }

}
