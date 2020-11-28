package com.harshit.covidtracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class IndiaTop5Adapter extends RecyclerView.Adapter<IndiaTop5Adapter.ViewHolder> {
    private ArrayList<States> indiaTop5ArrayList;
    private Context context;

    public IndiaTop5Adapter(ArrayList<States> indiaTop5ArrayList, Context context) {
        this.indiaTop5ArrayList = indiaTop5ArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_five_item_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.state.setText(indiaTop5ArrayList.get(position).getState());
        holder.recovered.setText(indiaTop5ArrayList.get(position).getTotalRecovered());
        holder.deceased.setText(indiaTop5ArrayList.get(position).getTotalDeaths());
        Long active = Long.parseLong(indiaTop5ArrayList.get(position).getTotalConfirmedCases().toString()) - (Long.parseLong(indiaTop5ArrayList.get(position).getTotalRecovered()) + Long.parseLong(indiaTop5ArrayList.get(position).getTotalDeaths()));
        holder.active.setText(active.toString());
    }

    @Override
    public int getItemCount() {
        return indiaTop5ArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView state;
        private TextView active;
        private TextView recovered;
        private TextView deceased;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            state = itemView.findViewById(R.id.country);
            active = itemView.findViewById(R.id.active);
            recovered = itemView.findViewById(R.id.deceased);
            deceased = itemView.findViewById(R.id.recovered);
        }
    }
}
