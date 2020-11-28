package com.harshit.covidtracker;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.harshit.covidtracker.databinding.FragmentGlobalBinding;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GlobalFragment extends Fragment {
    private FragmentGlobalBinding binding;
    private NavController navController;
    private PieChart pieChart;
    private ArrayList<PieEntry> yValues = new ArrayList<>();
    private ArrayList<Country> countryDataList = new ArrayList<>();
    private ArrayList<Country> globalTop5List = new ArrayList<>();
    private ArrayList<Integer> listColors = new ArrayList<>();
    private String indiaGetTotalConfirmed;
    private String indiaGetTotalConfirmedDelta;
    private String indiaGetTotalRecovered;
    private String indiaGetTotalRecoveredDelta;
    private String indiaGetTotalDeceased;
    private String indiaGetTotalDeceasedDelta;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_global, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(requireActivity(), R.id.NavHostFragment);
        binding.indiaCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                ArrayList<String> indiaData = new ArrayList<>();
                indiaData.add(indiaGetTotalConfirmed);
                indiaData.add(indiaGetTotalConfirmedDelta);
                indiaData.add(indiaGetTotalDeceased);
                indiaData.add(indiaGetTotalDeceasedDelta);
                indiaData.add(indiaGetTotalRecovered);
                indiaData.add(indiaGetTotalRecoveredDelta);
                bundle.putStringArrayList("indiaData", indiaData);
                navController.navigate(R.id.action_globalFragment_to_indiaFragment, bundle);
            }
        });
        binding.viewAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_globalFragment_to_countriesFragment);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.covid19api.com/")
                .build();
        CountriesStats countriesStats = retrofit.create(CountriesStats.class);
        Call<BaseApiClass> call = countriesStats.getCountriesData();
        call.enqueue(new Callback<BaseApiClass>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<BaseApiClass> call, Response<BaseApiClass> response) {
                assert response.body() != null;
                countryDataList.clear();
                globalTop5List.clear();
                listColors.clear();
                yValues.clear();
                String confirmed = response.body().getGlobal().getTotalConfirmed();
                String deceased = response.body().getGlobal().getTotalDeaths();
                String recovered = response.body().getGlobal().getTotalRecovered();
                String confirmedDelta = response.body().getGlobal().getTotalConfirmedDelta();
                String deceasedDelta = response.body().getGlobal().getTotalDeathsDelta();
                String recoveredDelta = response.body().getGlobal().getTotalRecoveredDelta();
                Long active = Long.parseLong(String.valueOf(confirmed)) - (Long.parseLong(String.valueOf(deceased)) + Long.parseLong(String.valueOf(recovered)));
                binding.active.setText(active.toString());
                binding.confirmed.setText(confirmed);
                binding.confirmedDelta.setText(confirmedDelta);
                binding.deceased.setText(deceased);
                binding.deceasedDelta.setText(deceasedDelta);
                binding.recovered.setText(recovered);
                binding.recoveredDelta.setText(recoveredDelta);
                yValues.add(new PieEntry(Float.parseFloat(active.toString()), "Total Active"));
                yValues.add(new PieEntry(Float.parseFloat(recovered), "Total Recovered"));
                yValues.add(new PieEntry(Float.parseFloat(deceased), "Total Deceased"));
                drawPieChart();
                Long deceasedPercent = (Long.parseLong(deceased)*100) / (Long.parseLong(confirmed));
                Long activePercent = (Long.parseLong(active.toString())*100) / Long.parseLong(confirmed);
                Long recoveredPercent = (Long.parseLong(recovered)*100) / Long.parseLong(confirmed);
                binding.deceasedPercent.setText(deceasedPercent.toString() + "%");
                binding.recoveredPercent.setText(recoveredPercent.toString() + "%");
                binding.activePercent.setText(activePercent.toString() + "%");


                for(int i=0; i<response.body().getCountries().size(); i++){
                    countryDataList.add(new Country(
                            response.body().getCountries().get(i).getCountryName(),
                            response.body().getCountries().get(i).getTotalConfirmed(),
                            response.body().getCountries().get(i).getTotalConfirmedDelta(),
                            response.body().getCountries().get(i).getTotalRecovered(),
                            response.body().getCountries().get(i).getTotalRecoveredDelta(),
                            response.body().getCountries().get(i).getTotalDeaths(),
                            response.body().getCountries().get(i).getTotalDeathsDelta()
                    ));
                }
                countryDataList.sort(Comparator.comparingLong(Country::getTotalConfirmed).reversed());

                for(int i=0; i<5; i++){
                    globalTop5List.add(countryDataList.get(i));
                }
                initGlobalTop5RecyclerView();

                indiaGetTotalConfirmed = countryDataList.get(2).getTotalConfirmed().toString();
                indiaGetTotalConfirmedDelta = countryDataList.get(2).getTotalConfirmedDelta().toString();
                indiaGetTotalRecovered = countryDataList.get(2).getTotalRecovered().toString();
                indiaGetTotalRecoveredDelta = countryDataList.get(2).getTotalRecoveredDelta().toString();
                indiaGetTotalDeceased = countryDataList.get(2).getTotalDeaths().toString();
                indiaGetTotalDeceasedDelta = countryDataList.get(2).getTotalDeathsDelta().toString();

            }

            @Override
            public void onFailure(Call<BaseApiClass> call, Throwable t) {
                Utils.logs("network failure -> " + t.getMessage());
            }
        });
    }
    public void drawPieChart() {
        pieChart = binding.pieChart;
        pieChart.setUsePercentValues(true);
        pieChart.setExtraOffsets(5f, 10f, 5f, 5f);

        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.isDrawHoleEnabled();
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);

        pieChart.getLegend().setEnabled(false);
        pieChart.getDescription().setEnabled(false);

        listColors.add(getResources().getColor(R.color.activeCases));
        listColors.add(getResources().getColor(R.color.recoveredCases));
        listColors.add(getResources().getColor(R.color.deceasedCases));

        PieDataSet dataSet = new PieDataSet(yValues, "Global COVID19 Data");
        dataSet.setColors(listColors);
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);
        pieChart.invalidate();

        pieChart.setData(data);
    }

    public void initGlobalTop5RecyclerView() {
        RecyclerView recyclerView = binding.recyclerView;
        GlobalTop5Adapter globalTop5Adapter = new GlobalTop5Adapter(globalTop5List, requireContext());
        recyclerView.setAdapter(globalTop5Adapter);
    }
}