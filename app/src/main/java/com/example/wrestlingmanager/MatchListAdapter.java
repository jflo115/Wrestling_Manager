package com.example.wrestlingmanager;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MatchListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    private List<Match> matches = null;
    private ArrayList<Match> alist;

    public MatchListAdapter(Context context, List<Match> matches) {
        this.context = context;
        this.matches = matches;
        inflater = LayoutInflater.from(context);
        this.alist = new ArrayList<Match>();
        this.alist.addAll(matches);
    }

    public class ViewHolder {
        TextView name;
    }

    public void changeList(List<Match> matches) {
        this.matches = matches;
        this.alist.clear();
        this.alist.addAll(matches);
    }

    @Override
    public int getCount() {
        return matches.size();
    }

    @Override
    public Object getItem(int i) {
        return matches.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if(view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.match_list, null);
            //Locate the textview in made up XML
            holder.name = (TextView) view.findViewById(R.id.MatchListView);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        //Set text into Textviews
        String text = "";
        text += matches.get(i).getSchool();
        text += "   ";
        text += matches.get(i).getOpponent();
        text += "   ";
        if(matches.get(i).getWin()) {
            text += "Win   ";
            holder.name.setTextColor(0xFF00BB00);
        }
        else{
            text += "Loss   ";
            holder.name.setTextColor(0xFF8B0000);
        }
        text += matches.get(i).getScore();
        holder.name.setText(text);
        return view;

    }




}
