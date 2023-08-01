package com.example.wrestlingmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ViewRosterDetailsAdapter extends BaseAdapter {

    //Declare Variables
    Context context;
    LayoutInflater inflater;
    private List<Wrestler> roster = null;
    private ArrayList<Wrestler> alist;


    public ViewRosterDetailsAdapter(Context context,List<Wrestler> roster) {
        this.context = context;
        this.roster = roster;
        inflater = LayoutInflater.from(context);
        this.alist = new ArrayList<Wrestler>();
        this.alist.addAll(roster);
    }

    public class ViewHolder {
        TextView name;
    }

    public void changeList(List<Wrestler> roster) {
        this.roster = roster;
        this.alist.clear();
        this.alist.addAll(roster);
    }

    @Override
    public int getCount() {
        return roster.size();
    }

    @Override
    public Object getItem(int i) {
        return roster.get(i);
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
            view = inflater.inflate(R.layout.viewdetailslist,null);
            //Locate the textview in viewdetailslist.xml
            holder.name = (TextView) view.findViewById(R.id.wrestler);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        //Set text into TextViews
        String setTxt = roster.get(i).getName() + "   Grade:" + String.valueOf(roster.get(i).getGrade());
        String record = roster.get(i).toStringRecord();
        setTxt = setTxt + "    " + record;
        holder.name.setText(setTxt);
        return view;
    }
}
