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

import com.harshit.covidtracker.databinding.FragmentIndiaBinding;
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


public class IndiaFragment extends Fragment {
    private FragmentIndiaBinding binding;
    private NavController navController;
    private PieChart pieChart;
    private ArrayList<PieEntry> yValues = new ArrayList<>();
    private ArrayList<States> stateDataList = new ArrayList<>();
    private ArrayList<States> stateTop5List = new ArrayList<>();
    private ArrayList<Integer> listColors = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_india, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(requireActivity(), R.id.NavHostFragment);
        binding.globalCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_indiaFragment_to_globalFragment);
            }
        });
        binding.viewAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_indiaFragment_to_statesFragment);
            }
        });
        assert getArguments() != null;
        String confirmed = getArguments().getStringArrayList("indiaData").get(0);
        String confirmedDelta = getArguments().getStringArrayList("indiaData").get(1);
        String deceased = getArguments().getStringArrayList("indiaData").get(2);
        String deceasedDelta = getArguments().getStringArrayList("indiaData").get(3);
        String recovered = getArguments().getStringArrayList("indiaData").get(4);
        String recoveredDelta = getArguments().getStringArrayList("indiaData").get(5);
        String active = String.format(String.valueOf(Long.parseLong(confirmed) - (Long.parseLong(deceased) + Long.parseLong(recovered))));
        binding.recoveredDelta.setText(recoveredDelta);
        binding.recovered.setText(recovered);
        binding.deceasedDelta.setText(deceasedDelta);
        binding.deceased.setText(deceased);
        binding.confirmedDelta.setText(confirmedDelta);
        binding.confirmed.setText(confirmed);
        binding.active.setText(active);
        yValues.add(new PieEntry(Float.parseFloat(active), "Total Active"));
        yValues.add(new PieEntry(Float.parseFloat(recovered), "Total Recovered"));
        yValues.add(new PieEntry(Float.parseFloat(deceased), "Total Deceased"));
        Long deceasedPercent = (Long.parseLong(deceased)*100) / (Long.parseLong(confirmed));
        Long activePercent = (Long.parseLong(active.toString())*100) / Long.parseLong(confirmed);
        Long recoveredPercent = (Long.parseLong(recovered)*100) / Long.parseLong(confirmed);
        binding.deceasedPercent.setText(deceasedPercent.toString() + "%");
        binding.recoveredPercent.setText(recoveredPercent.toString() + "%");
        binding.activePercent.setText(activePercent.toString() + "%");
        drawPieChart();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.rootnet.in/")
                .build();
        StatesStats statesStats = retrofit.create(StatesStats.class);
        Call<BaseApiClassIndia> call = statesStats.getStatesData();
        call.enqueue(new Callback<BaseApiClassIndia>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<BaseApiClassIndia> call, Response<BaseApiClassIndia> response) {
                assert response.body() != null;
                stateDataList.clear();
                stateTop5List.clear();
                listColors.clear();
                yValues.clear();

                for(int i=0; i<response.body().getData().getRegional().size(); i++){
                    stateDataList.add(new States(
                            response.body().getData().getRegional().get(i).getState(),
                            response.body().getData().getRegional().get(i).getTotalConfirmedCases(),
                            response.body().getData().getRegional().get(i).getTotalRecovered(),
                            response.body().getData().getRegional().get(i).getTotalDeaths()
                    ));
                }
                stateDataList.sort(Comparator.comparingLong(States::getTotalConfirmedCases).reversed());

                for(int i=0; i<5; i++){
                    stateTop5List.add(stateDataList.get(i));
                }
                initIndiaTop5RecyclerView();

            }

            @Override
            public void onFailure(Call<BaseApiClassIndia> call, Throwable t) {
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
    public void initIndiaTop5RecyclerView() {
        RecyclerView recyclerView = binding.recyclerView;
        IndiaTop5Adapter indiaTop5Adapter = new IndiaTop5Adapter(stateTop5List, requireContext());
        recyclerView.setAdapter(indiaTop5Adapter);
    }
}