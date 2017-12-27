package com.sitp2017.hujiepingtai6;

import com.baidu.mapapi.model.LatLng;

/**
 * Created by EndlessLethe on 2017/11/15.
 */

public class UserLocation {
    private String PhoneNumber;
    private String latitude;
    private String longitude;
    public UserLocation() {}
    public UserLocation(String pn, String la, String lo) {
        PhoneNumber = pn;
        latitude = la;
        longitude = lo;
    }
    public String getPhoneNumber() {
        return PhoneNumber;
    }
    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }
    public double getLatitude() {
        return Double.valueOf(latitude);
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return Double.valueOf(longitude);
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    public LatLng toLatLng() {
        return new LatLng(getLatitude(), getLongitude());
    }

}

//use test;
//CREATE TABLE `userlocation` (
//    `autoid` int(20) DEFAULT NULL auto_increment,
//    `PhoneNumber` varchar(255) DEFAULT NULL,
//    `latitude` varchar(255) DEFAULT NULL,
//    `longitude` varchar(255) DEFAULT NULL,
//    PRIMARY KEY (`autoid`))
//    ENGINE=InnoDB DEFAULT CHARSET=utf8;

//select * from userlocation;

//insert into userlocation (PhoneNumber, latitude, longitude) values (?, ?, ?)

//update userlocation set latitude = 30.559475, longitude = 104.000828 where PhoneNumber = ?