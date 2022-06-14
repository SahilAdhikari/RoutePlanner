package com.example.mymaplearning;

public class TravelId {

    String mainAddress= "";
    String subAddress="";
    double lat;
    double longi;
    public TravelId(String mainAddress,String subAddress,double lat,double longi) {
        this.mainAddress = mainAddress;
        this.subAddress = subAddress;
        this.lat = lat;
        this.longi = longi;
    }
}
