package com.example.tantan.openweather;

/**
 * Created by TanTan on 10/8/17.
 */

public class Forecast_Weather {
    private String dt, icon;
    private String tmin,tmax;

    public Forecast_Weather(String dt, String icon, String tmin, String tmax) {
        this.dt = dt;
        this.icon = icon;
        this.tmin = tmin;
        this.tmax = tmax;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTmin() {
        return tmin;
    }

    public void setTmin(String tmin) {
        this.tmin = tmin;
    }

    public String getTmax() {
        return tmax;
    }

    public void setTmax(String tmax) {
        this.tmax = tmax;
    }
}
