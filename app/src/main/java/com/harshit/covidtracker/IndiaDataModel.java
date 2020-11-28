package com.harshit.covidtracker;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class BaseApiClassIndia {
    private SubBaseClassIndia data;

    public BaseApiClassIndia(SubBaseClassIndia data) {
        this.data = data;
    }

    public SubBaseClassIndia getData() {
        return data;
    }

    public void setData(SubBaseClassIndia data) {
        this.data = data;
    }
}

class SubBaseClassIndia {
    private List<States> regional;

    public SubBaseClassIndia(List<States> regional) {
        this.regional = regional;
    }

    public List<States> getRegional() {
        return regional;
    }

    public void setRegional(List<States> regional) {
        this.regional = regional;
    }
}

class States {
    @SerializedName(value = "loc")
    private String state;
    @SerializedName(value = "totalConfirmed")
    private Long totalConfirmedCases;
    @SerializedName(value = "deaths")
    private String totalDeaths;
    @SerializedName(value = "discharged")
    private String totalRecovered;

    public States(String state, Long totalConfirmedCases, String totalDeaths, String totalRecovered) {
        this.state = state;
        this.totalConfirmedCases = totalConfirmedCases;
        this.totalDeaths = totalDeaths;
        this.totalRecovered = totalRecovered;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getTotalConfirmedCases() {
        return totalConfirmedCases;
    }

    public void setTotalConfirmedCases(Long totalConfirmedCases) {
        this.totalConfirmedCases = totalConfirmedCases;
    }

    public String getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(String totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public String getTotalRecovered() {
        return totalRecovered;
    }

    public void setTotalRecovered(String totalRecovered) {
        this.totalRecovered = totalRecovered;
    }
}
