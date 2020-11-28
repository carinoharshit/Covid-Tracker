package com.harshit.covidtracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StatesAdapter extends RecyclerView.Adapter<StatesAdapter.ViewHolder> {
    private ArrayList<States> statesArrayList;
    private Context context;

    public StatesAdapter(ArrayList<States> statesArrayList, Context context) {
        this.statesArrayList = statesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_states_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.states.setText(statesArrayList.get(position).getState());
        holder.confirmed.setText(statesArrayList.get(position).getTotalConfirmedCases().toString());
        holder.recovered.setText(statesArrayList.get(position).getTotalRecovered());
        holder.deceased.setText(statesArrayList.get(position).getTotalDeaths());
        long activeCases = Long.parseLong(statesArrayList.get(position).getTotalConfirmedCases().toString()) - (Long.parseLong(statesArrayList.get(position).getTotalRecovered()) + Long.parseLong(statesArrayList.get(position).getTotalDeaths()));
        holder.active.setText(Long.toString(activeCases));
    }

    @Override
    public int getItemCount() {
        return statesArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView states;
        private TextView confirmed;
        private TextView active;
        private TextView recovered;
        private TextView deceased;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            states = itemView.findViewById(R.id.country);
            confirmed = itemView.findViewById(R.id.confirmed);
            active = itemView.findViewById(R.id.active);
            recovered = itemView.findViewById(R.id.recovered);
            deceased = itemView.findViewById(R.id.deceased);
        }
    }
}
