package com.harshit.covidtracker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.harshit.covidtracker.databinding.FragmentStatesBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StatesFragment extends Fragment {
    private FragmentStatesBinding binding;
    private ArrayList<States> statesDataList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_states, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireContext(), MainActivity.class));
            }
        });
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
                for(int i=0; i<response.body().getData().getRegional().size(); i++){
                    statesDataList.add(new States(
                            response.body().getData().getRegional().get(i).getState(),
                            response.body().getData().getRegional().get(i).getTotalConfirmedCases(),
                            response.body().getData().getRegional().get(i).getTotalRecovered(),
                            response.body().getData().getRegional().get(i).getTotalDeaths()
                    ));
                }
                initRecyclerView();
            }

            @Override
            public void onFailure(Call<BaseApiClassIndia> call, Throwable t) {
                Utils.logs("network failure -> " + t.getMessage());
            }
        });
    }
    private void initRecyclerView() {
        RecyclerView recyclerView = binding.recyclerView;
        StatesAdapter statesAdapter = new StatesAdapter(statesDataList, requireContext());
        recyclerView.setAdapter(statesAdapter);
    }
}