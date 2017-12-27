package com.sitp2017.hujiepingtai6;

import android.app.Application;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;

/**
 * Created by EndlessLethe on 2017/11/7.
 */

public class MyApplication extends Application {
    private static MyApplication instance = null;
    public static Customer customer;
    public static ArrayList<Demand> demands;
    public static ArrayList<UserLocation> userLocations;
    private boolean isLogin;
    private BDLocation myLocation;

    public static MyApplication getInstance(){
        return instance;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        instance = this;
        customer = new Customer();
        demands = new ArrayList<Demand>();
        userLocations = new ArrayList<UserLocation>();
        isLogin = false;
    }
    public void Login() {
        isLogin = true;
        SQLService.findInfoByPhoneNumber(customer.getPhoneNumber(), customer);
        updateLocalInfo();
    }
    public void Login(String phoneNumber, String password) {
        isLogin = true;
        customer.setPhoneNumber(phoneNumber);
        customer.setPassword(password);
        SQLService.findInfoByPhoneNumber(phoneNumber, customer);
        updateLocalInfo();
    }
    public void Logout() {
        isLogin = false;
    }
    public boolean checkLogin() {
        return isLogin;
    }
    //在onCreate方法里确认Login
    public static void updateLocalInfo() {
        demands = SQLService.getDemand();
        userLocations = SQLService.getUserLocation();
    }
    public BDLocation getMyLocation() {
        return myLocation;
    }
    public void setMyLocation(BDLocation myLocation) {
        this.myLocation = myLocation;
    }
}