package com.harshit.covidtracker;

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

import com.harshit.covidtracker.databinding.FragmentCountriesBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CountriesFragment extends Fragment {
    private FragmentCountriesBinding binding;
    private NavController navController;
    private ArrayList<Country> countryDataList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_countries, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(requireActivity(), R.id.NavHostFragment);
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_countriesFragment_to_globalFragment);
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
                String confirmed = response.body().getGlobal().getTotalConfirmed();
                String deceased = response.body().getGlobal().getTotalDeaths();
                String recovered = response.body().getGlobal().getTotalRecovered();
                String confirmedDelta = response.body().getGlobal().getTotalConfirmedDelta();
                String deceasedDelta = response.body().getGlobal().getTotalDeathsDelta();
                String recoveredDelta = response.body().getGlobal().getTotalRecoveredDelta();
                Long active = Long.parseLong(String.valueOf(confirmed)) - (Long.parseLong(String.valueOf(deceased)) + Long.parseLong(String.valueOf(recovered)));
                for(int i=0; i<response.body().getCountries().size(); i++){
                    countryDataList.add(new Country(
                            response.body().getCountries().get(i).getCountryName(),
                            response.body().getCountries().get(i).getTotalConfirmed(),
                            response.body().getCountries().get(i).getTotalConfirmedDelta(),
                            response.body().getCountries().get(i).getTotalRecovered(),
                            response.body().getCountries().get(i).getTotalConfirmedDelta(),
                            response.body().getCountries().get(i).getTotalDeaths(),
                            response.body().getCountries().get(i).getTotalDeathsDelta()
                    ));
                }
                initRecyclerView();
            }

            @Override
            public void onFailure(Call<BaseApiClass> call, Throwable t) {
                Utils.logs("network failure -> " + t.getMessage());
            }
        });
    }
    private void initRecyclerView() {
        RecyclerView recyclerView = binding.recyclerView;
        CountriesAdapter countriesAdapter = new CountriesAdapter(countryDataList, requireContext());
        recyclerView.setAdapter(countriesAdapter);
    }
}