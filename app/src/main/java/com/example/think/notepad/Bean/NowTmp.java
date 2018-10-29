package com.example.think.notepad.Bean;
/*
* 当前天气状况的bean类
* */
public class NowTmp {
    private String cond_txt;//实况天气状况描述
    private String tmp;//温度，默认单位：摄氏度
    private String wind_dir;//风向
    private String cloud;//云量
    private String _wind_sc;//风力

    public String get_wind_sc() {
        return _wind_sc;
    }

    public void set_wind_sc(String _wind_sc) {
        this._wind_sc = _wind_sc;
    }

    public String getCond_txt() {
        return cond_txt;
    }

    public void setCond_txt(String cond_txt) {
        this.cond_txt = cond_txt;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    public String getWind_dir() {
        return wind_dir;
    }

    public void setWind_dir(String wind_dir) {
        this.wind_dir = wind_dir;
    }

    public String getCloud() {
        return cloud;
    }

    public void setCloud(String cloud) {
        this.cloud = cloud;
    }

}
