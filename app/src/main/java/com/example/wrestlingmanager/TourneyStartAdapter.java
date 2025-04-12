package com.example.wrestlingmanager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TourneyStartAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    private List<Wrestler> roster = null;
    private ArrayList<Wrestler> alist;
    private List<Integer> selectedIND;
    private List<Wrestler> fullRoster;

    public TourneyStartAdapter(Context context, List<Wrestler> roster, ArrayList<Wrestler> fullRoster, ArrayList<Integer> selectedIND){
        this.context = context;
        this.roster= roster;
        this.selectedIND = selectedIND;
        this.fullRoster = fullRoster;
        inflater = LayoutInflater.from(context);
        this.alist = new ArrayList<Wrestler>();
        this.alist.addAll(roster);
    }

    public class ViewHolder{
        TextView name;
        TextView record;
        Button button;
    }

    public void changeList(List<Wrestler> roster){
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
        if(view == null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.wrestlers_at_tourney_view_row,null);
            holder.name = (TextView) view.findViewById(R.id.TourneyWrestlerName);
            holder.record = (TextView) view.findViewById(R.id.TourneyWrestlerRecord);
            holder.button = (Button) view.findViewById(R.id.TourneyWrestlerAddMatch);
            view.setTag(holder);
        }
        else{
            holder = (ViewHolder)  view.getTag();
        }


        holder.name.setText(roster.get(i).getName());
        holder.name.setTextColor(Color.BLACK);
        holder.record.setText(roster.get(i).getRecord().toString());
        holder.record.setTextColor(Color.BLACK);


        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Utilities.showAlert((Activity) context,"Good job");
                //TextView test = (TextView) ((Activity) context).findViewById(R.id.testStartTourney);
                //test.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(context,AddMatchActivity.class);
                intent.putExtra("AddMatch", (Serializable) fullRoster);
                intent.putExtra("Selected", (Serializable) selectedIND);
                intent.putExtra("position",i);
                intent.putExtra("source",2);
                context.startActivity(intent);
            }
        });

        return view;
    }
}
