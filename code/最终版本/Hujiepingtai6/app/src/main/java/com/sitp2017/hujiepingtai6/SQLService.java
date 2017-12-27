package com.sitp2017.hujiepingtai6;

/**
 * Created by EndlessLethe on 2017/10/25.
 */


import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.mysql.jdbc.CommunicationsException;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SQLService {
    private static String driver="com.mysql.jdbc.Driver";
    private static String url="jdbc:mysql://47.95.204.80:3306/test?useUnicode=true&characterEncoding=UTF-8";
    private static String user="test";
    private static String pwd="dachuang2017";
    private static final String TAG = "SQLService";

    //在注册流程结束后，上传注册信息
    public static void insertPersonalInfo(Customer p){
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, user, pwd);
            String sql = "insert into custumor (" +
                    "name, password, id, phoneNumber, createDate, school) values(?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            //设置占位符对应的值
            ps.setString(1, p.getName());
            ps.setString(2, p.getPassword());
            ps.setString(3, p.getId());
            ps.setString(4, p.getPhoneNumber());
            ps.setLong(5, p.getCreateDate());
            ps.setString(6, p.getSchool());

            ps.executeUpdate();

            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //发布需求信息
    public static void insertDemand(String postPhoneNumber, String item, String description){
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, user, pwd);
            String sql = "insert into demand (postPhoneNumber, item, description, status) values (?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            //设置占位符对应的值
            ps.setString(1, postPhoneNumber);
            ps.setString(2, item);
            ps.setString(3, description);
            ps.setInt(4, 0);

            ps.executeUpdate();

            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void insertUserLocation(double latitude, double longitude){
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, user, pwd);
            String sql = "insert into userlocation (PhoneNumber, latitude, longitude) values (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            //设置占位符对应的值
            ps.setString(1, MyApplication.getInstance().customer.getPhoneNumber());
            ps.setString(2, String.valueOf(latitude));
            ps.setString(3, String.valueOf(longitude));

            ps.executeUpdate();

            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateUserLocation(double latitude, double longitude){
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, user, pwd);
            String sql = "update userlocation set latitude = ?, longitude = ? where PhoneNumber = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            //设置占位符对应的值
            ps.setString(1, String.valueOf(latitude));
            ps.setString(2, String.valueOf(longitude));
            ps.setString(3, MyApplication.getInstance().customer.getPhoneNumber());

            ps.executeUpdate();

            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 修改状态码
    public static void updateDemandStutas(int id, int stutasCode){
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, user, pwd);
            String sql = "update demand set status = ? where id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            //设置占位符对应的值
            ps.setInt(1, stutasCode);
            ps.setInt(2, id);

            ps.executeUpdate();

            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //用户在info页面修改个人资料后
    public static void updateSex(Customer p){
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, user, pwd);
            String sql = "update person set sex=? where phoneNumber=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            //设置占位符对应的值
            ps.setInt(1, p.getSex());
            ps.setString(2, p.getPhoneNumber());

            ps.executeUpdate();

            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void updateGrade(Customer p){
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, user, pwd);
            String sql = "update person set grade=? where phoneNumber=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            //设置占位符对应的值
            ps.setInt(1, p.getGrade());
            ps.setString(2, p.getPhoneNumber());

            ps.executeUpdate();

            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 在登录成功后获取用户信息
    public static void findInfoByPhoneNumber(String phoneNumber, Customer p){
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, user, pwd);
            String sql = "select name,sex,createDate," +
                    "school,grade from custumor where phoneNumber = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            //设置占位符对应的值
            ps.setString(1, phoneNumber);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                p.setName(rs.getString(1));
                p.setSex(rs.getInt(2));
                p.setCreateDate(rs.getLong(3));
                p.setSchool(rs.getString(4));
                p.setGrade(rs.getInt(5));
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 获取电话号码对应的位置信息
    public static ArrayList<UserLocation> getUserLocation(){
        ArrayList<UserLocation> uls = new ArrayList<UserLocation>();
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, user, pwd);
            String sql = "select * from userlocation";
            Statement state = conn.createStatement();
            //设置占位符对应的值

            ResultSet rs = state.executeQuery(sql);
            while (rs.next()){
                UserLocation ul = new UserLocation();
                ul.setPhoneNumber(rs.getString(2));
                ul.setLatitude(rs.getString(3));
                ul.setLongitude(rs.getString(4));
                Log.d("userLocation: ", ul.getPhoneNumber() + " " + ul.getLatitude() +
                        ul.getLongitude());
                uls.add(ul);
            }
            rs.close();
            state.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uls;
    }

    // 在登录成功后和每隔两分钟获取需求信息
    public static ArrayList<Demand> getDemand() {
        ArrayList<Demand> demands = new ArrayList<Demand>();
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, user, pwd);
            String sql = "select * from demand";
            Statement state = conn.createStatement();
            //设置占位符对应的值

            Log.d(TAG, "开始执行查询demand");
            ResultSet rs = state.executeQuery(sql);
            while (rs.next()){
                Demand d = new Demand();
                d.setId(rs.getInt(1));
                d.setPostPhoneNumber(rs.getString(2));
                d.setItem(rs.getString(3));
                d.setDescription(rs.getString(4));
                d.setStatus(rs.getInt(5));
                Log.d("add demand: ", d.getPostPhoneNumber() + " " + d.getDescription());
                demands.add(d);
            }
            rs.close();
            state.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return demands;
    }

    //在我的订单页面
    public static ArrayList<Demand> getMyDemand(String phoneNumber){
        ArrayList<Demand> demands = new ArrayList<>();
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, user, pwd);
            String sql = "select * from demand where postPhoneNumber = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            //设置占位符对应的值
            ps.setString(1, phoneNumber);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Demand d = new Demand();
                d.setId(rs.getInt(1));
                d.setPostPhoneNumber(rs.getString(2));
                d.setItem(rs.getString(3));
                d.setDescription(rs.getString(4));
                d.setStatus(rs.getInt(5));
                demands.add(d);
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return demands;
    }

    //在登陆和注册时确认phone是否存在
    public static boolean checkPhoneNumber(String phoneNumber) throws SQLException{
        boolean flag = false;
        try {
            Log.d("SQLService", "checkPhoneNumber: "+phoneNumber);
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, user, pwd);
            String sql = "select * from custumor where phoneNumber = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            //设置占位符对应的值
            ps.setString(1, phoneNumber);

            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                flag = true;
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            Log.d(TAG, "向Login抛出链接异常");
            e.printStackTrace();
            throw e;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    //在登录时确认phoneNumber和密码匹配
    public static boolean isVerified(String phoneNumber, String password) {
        boolean flag = false;
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, user, pwd);
            String sql = "select password from custumor where phoneNumber = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            //设置占位符对应的值
            ps.setString(1, phoneNumber);

            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                if (rs.getString(1).equals(password)) flag = true;
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    //确认userLocation对应手机号已经在数据库中有记录
    public static boolean checkUserLocation(String phoneNumber) {
        boolean flag = false;
        try {
            Log.d("SQLService", "checkUserLocation: "+phoneNumber);
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, user, pwd);
            String sql = "select * from userlocation where phoneNumber = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            //设置占位符对应的值
            ps.setString(1, phoneNumber);

            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                flag = true;
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
