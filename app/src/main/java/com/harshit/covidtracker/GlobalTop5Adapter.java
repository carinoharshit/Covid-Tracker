package com.harshit.covidtracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GlobalTop5Adapter extends RecyclerView.Adapter<GlobalTop5Adapter.ViewHolder> {
    private ArrayList<Country> globalTop5ArrayList;
    private Context context;

    public GlobalTop5Adapter(ArrayList<Country> globalTop5ArrayList, Context context) {
        this.globalTop5ArrayList = globalTop5ArrayList;
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
        holder.country.setText(globalTop5ArrayList.get(position).getCountryName());
        holder.recovered.setText(globalTop5ArrayList.get(position).getTotalRecovered());
        holder.deceased.setText(globalTop5ArrayList.get(position).getTotalDeaths());
        Long active = Long.parseLong(globalTop5ArrayList.get(position).getTotalConfirmed().toString()) - (Long.parseLong(globalTop5ArrayList.get(position).getTotalRecovered()) + Long.parseLong(globalTop5ArrayList.get(position).getTotalDeaths()));
        holder.active.setText(active.toString());
    }

    @Override
    public int getItemCount() {
        return globalTop5ArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView country;
        private TextView active;
        private TextView recovered;
        private TextView deceased;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            country = itemView.findViewById(R.id.country);
            active = itemView.findViewById(R.id.active);
            recovered = itemView.findViewById(R.id.recovered);
            deceased = itemView.findViewById(R.id.deceased);
        }
    }
}
