package com.harshit.covidtracker;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class BaseApiClass {
    @SerializedName(value = "Countries")
    private List<Country> countries;
    @SerializedName(value = "Global")
    private Global global;

    public BaseApiClass(List<Country> countries, Global global) {
        this.countries = countries;
        this.global = global;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public Global getGlobal() {
        return global;
    }

    public void setGlobal(Global global) {
        this.global = global;
    }
}

class Country {
    @SerializedName(value = "Country")
    private String countryName;
    @SerializedName(value = "TotalConfirmed")
    private Long totalConfirmed;
    @SerializedName(value = "NewConfirmed")
    private String totalConfirmedDelta;
    @SerializedName(value = "TotalRecovered")
    private String totalRecovered;
    @SerializedName(value = "NewRecovered")
    private String totalRecoveredDelta;
    @SerializedName(value = "TotalDeaths")
    private String totalDeaths;
    @SerializedName(value = "NewDeaths")
    private String totalDeathsDelta;

    public Country(String countryName, Long totalConfirmed, String totalConfirmedDelta, String totalRecovered, String totalRecoveredDelta, String totalDeaths, String totalDeathsDelta) {
        this.countryName = countryName;
        this.totalConfirmed = totalConfirmed;
        this.totalConfirmedDelta = totalConfirmedDelta;
        this.totalRecovered = totalRecovered;
        this.totalRecoveredDelta = totalRecoveredDelta;
        this.totalDeaths = totalDeaths;
        this.totalDeathsDelta = totalDeathsDelta;
    }

    public String getCountryName() {
        return countryName;
    }

    public Long getTotalConfirmed() {
        return totalConfirmed;
    }

    public String getTotalConfirmedDelta() {
        return totalConfirmedDelta;
    }

    public String getTotalRecovered() {
        return totalRecovered;
    }

    public String getTotalRecoveredDelta() {
        return totalRecoveredDelta;
    }

    public String getTotalDeaths() {
        return totalDeaths;
    }

    public String getTotalDeathsDelta() {
        return totalDeathsDelta;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public void setTotalConfirmed(Long totalConfirmed) {
        this.totalConfirmed = totalConfirmed;
    }

    public void setTotalConfirmedDelta(String totalConfirmedDelta) {
        this.totalConfirmedDelta = totalConfirmedDelta;
    }

    public void setTotalRecovered(String totalRecovered) {
        this.totalRecovered = totalRecovered;
    }

    public void setTotalRecoveredDelta(String totalRecoveredDelta) {
        this.totalRecoveredDelta = totalRecoveredDelta;
    }

    public void setTotalDeaths(String totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public void setTotalDeathsDelta(String totalDeathsDelta) {
        this.totalDeathsDelta = totalDeathsDelta;
    }
}

class Global {
    @SerializedName(value = "TotalConfirmed")
    private String totalConfirmed;
    @SerializedName(value = "NewConfirmed")
    private String totalConfirmedDelta;
    @SerializedName(value = "TotalRecovered")
    private String totalRecovered;
    @SerializedName(value = "NewRecovered")
    private String totalRecoveredDelta;
    @SerializedName(value = "TotalDeaths")
    private String totalDeaths;
    @SerializedName(value = "NewDeaths")
    private String totalDeathsDelta;

    public Global(String totalConfirmed, String totalConfirmedDelta, String totalRecovered, String totalRecoveredDelta, String totalDeaths, String totalDeathsDelta) {
        this.totalConfirmed = totalConfirmed;
        this.totalConfirmedDelta = totalConfirmedDelta;
        this.totalRecovered = totalRecovered;
        this.totalRecoveredDelta = totalRecoveredDelta;
        this.totalDeaths = totalDeaths;
        this.totalDeathsDelta = totalDeathsDelta;
    }

    public String getTotalConfirmed() {
        return totalConfirmed;
    }

    public void setTotalConfirmed(String totalConfirmed) {
        this.totalConfirmed = totalConfirmed;
    }

    public String getTotalConfirmedDelta() {
        return totalConfirmedDelta;
    }

    public void setTotalConfirmedDelta(String totalConfirmedDelta) {
        this.totalConfirmedDelta = totalConfirmedDelta;
    }

    public String getTotalRecovered() {
        return totalRecovered;
    }

    public void setTotalRecovered(String totalRecovered) {
        this.totalRecovered = totalRecovered;
    }

    public String getTotalRecoveredDelta() {
        return totalRecoveredDelta;
    }

    public void setTotalRecoveredDelta(String totalRecoveredDelta) {
        this.totalRecoveredDelta = totalRecoveredDelta;
    }

    public String getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(String totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public String getTotalDeathsDelta() {
        return totalDeathsDelta;
    }

    public void setTotalDeathsDelta(String totalDeathsDelta) {
        this.totalDeathsDelta = totalDeathsDelta;
    }
}
