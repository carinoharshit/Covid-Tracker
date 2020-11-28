package com.harshit.covidtracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.ViewHolder> {
    private ArrayList<Country> countryArrayList;
    private Context context;

    public CountriesAdapter(ArrayList<Country> countryArrayList, Context context) {
        this.countryArrayList = countryArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.country.setText(countryArrayList.get(position).getCountryName());
        holder.confirmed.setText(countryArrayList.get(position).getTotalConfirmed().toString());
        holder.recovered.setText(countryArrayList.get(position).getTotalRecovered().toString());
        holder.deceased.setText(countryArrayList.get(position).getTotalDeaths().toString());
        holder.confirmedDelta.setText(countryArrayList.get(position).getTotalConfirmedDelta().toString());
        holder.recoveredDelta.setText(countryArrayList.get(position).getTotalRecoveredDelta().toString());
        holder.deceasedDelta.setText(countryArrayList.get(position).getTotalDeathsDelta().toString());
        long activeCases = Long.parseLong(countryArrayList.get(position).getTotalConfirmed().toString()) - (Long.parseLong(countryArrayList.get(position).getTotalRecovered()) + Long.parseLong(countryArrayList.get(position).getTotalDeaths()));
        holder.active.setText(Long.toString(activeCases));
    }

    @Override
    public int getItemCount() {
        return countryArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView country;
        private TextView confirmed;
        private TextView confirmedDelta;
        private TextView active;
        private TextView recovered;
        private TextView recoveredDelta;
        private TextView deceased;
        private TextView deceasedDelta;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            country = itemView.findViewById(R.id.country);
            confirmed = itemView.findViewById(R.id.confirmed);
            confirmedDelta = itemView.findViewById(R.id.confirmed_delta);
            active = itemView.findViewById(R.id.active);
            recovered = itemView.findViewById(R.id.recovered);
            recoveredDelta = itemView.findViewById(R.id.recovered_delta);
            deceased = itemView.findViewById(R.id.deceased);
            deceasedDelta = itemView.findViewById(R.id.deceased_delta);
        }
    }
}
