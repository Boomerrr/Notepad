package com.example.think.notepad.Bean;
/*
* 地点的bean类
*
* */
public class LocationBean {
    private String country;
    private String province;
    private String city;
    private String distract;
    private String street;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistract() {
        return distract;
    }

    public void setDistract(String distract) {
        this.distract = distract;
    }
}
