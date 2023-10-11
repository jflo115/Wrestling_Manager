package com.example.wrestlingmanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class RosterListAdapter extends RecyclerView.Adapter<RosterListAdapter.ViewHolder>{
    private ArrayList<Wrestler> roster = new ArrayList<Wrestler>();

    public void setRosterData(ArrayList<Wrestler> roster){
        //this.roster.clear();
        this.roster = roster;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public RosterListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.roster_list,parent,false);
        return new RosterListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RosterListAdapter.ViewHolder holder, int position) {

        holder.setWrestler(roster.get(position));

    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }


    @Override
    public int getItemCount() {
        return roster.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private Wrestler wrestler;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.rosterView);
        }

        public Wrestler getWrestler() {

            return wrestler;
        }

        public void setWrestler(Wrestler wrestler) {

            this.wrestler = wrestler;
            this.textView.setText(wrestler.getName() + " " + wrestler.getGrade());
        }
    }
}
