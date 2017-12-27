package com.sitp2017.hujiepingtai6;

import com.baidu.mapapi.model.LatLng;

/**
 * Created by EndlessLethe on 2017/11/12.
 */

public class Demand {
    private int id;
    private String postPhoneNumber;
    private String item;
    private String description;
    private int status;//0为进行中，1为已完成，2为已取消

    protected Demand() {};

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getPostPhoneNumber() {
        return postPhoneNumber;
    }
    public void setPostPhoneNumber(String postPhoneNumber) {
        this.postPhoneNumber = postPhoneNumber;
    }
    public String getItem() {
        return item;
    }
    public void setItem(String item) {
        this.item = item;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
}
//use test;
//CREATE TABLE `demand` (
//    `id` int(20) DEFAULT NULL auto_increment,
//    `postPhoneNumber` varchar(255) DEFAULT NULL,
//    `item` varchar(255) DEFAULT NULL,
//    `description` varchar(255) DEFAULT NULL,
//    `status` int(20) DEFAULT NULL,
//    PRIMARY KEY (`id`))
//    ENGINE=InnoDB DEFAULT CHARSET=utf8;


//select * from demand;
//select * from demand where postPhoneNumber = 13111985087;

//insert into demand (postPhoneNumber, item, description, status) values ("13111895087", "篮球", "很想打篮球，求好心人借一个！！！", 0);

//update demand set status = 1 where id = 1;