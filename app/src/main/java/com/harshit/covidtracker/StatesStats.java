package com.harshit.covidtracker;

import retrofit2.Call;
import retrofit2.http.GET;

public interface StatesStats {
    @GET("covid19-in/stats/latest")
    Call<BaseApiClassIndia> getStatesData();
}
