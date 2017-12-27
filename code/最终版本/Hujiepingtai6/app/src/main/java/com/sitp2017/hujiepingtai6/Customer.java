package com.sitp2017.hujiepingtai6;

import java.util.Date;

public class Customer {
    private String name;
    private String password;
    private String id;
    private String phoneNumber;
    private int sex;//0为男，1为女
    private long createDate;
    private Date birthday;
    private String school;
    private int grade;//年级
    private int  privilege;

    protected Customer(){}

    public String getName() {
        return name;
    }
    public String getPassword() {
        return password;
    }
    public int getSex() {
        return sex;
    }
    public long getCreateDate() {
        return createDate;
    }
    public Date getBirthday() {
        return birthday;
    }
    public String getSchool() {
        return school;
    }
    public int getGrade() {
        return grade;
    }
    public int getPrivilege() {
        return privilege;
    }
    public String getId() {
        return id;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setSex(int sex) {
        this.sex = sex;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate.getTime();
    }
    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    public void setSchool(String school) {
        this.school = school;
    }
    public void setGrade(int grade) {
        this.grade = grade;
    }
    public void setPrivilege(int privilege) {
        this.privilege = privilege;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
//use test;
//CREATE TABLE `custumor` (
//    `name` varchar(255) DEFAULT NULL,
//    `password` varchar(255) DEFAULT NULL,
//    `id` varchar(255) DEFAULT NULL,
//    `phoneNumber` varchar(255) DEFAULT NULL,
//    `sex` varchar(255) DEFAULT NULL,
//    `createDate` bigint(20) DEFAULT NULL,
//    `birthday` varchar(255) DEFAULT NULL,
//    `school` varchar(255) DEFAULT NULL,
//    `grade` bigint(20) DEFAULT NULL,
//    `privilege` int(20) DEFAULT NULL,
//    PRIMARY KEY (`phoneNumber`))
//    ENGINE=InnoDB DEFAULT CHARSET=utf8;

//use test;
//insert into custumor (name, password, id, phoneNumber, createDate, school) values ("曾四为", "123456", "2015141462006", "13111895087", 1438692801766, "四川大学");
//insert into custumor (name, password, id, phoneNumber, createDate, school) values ("魏兆威", "zzz123", "2015141462238", "13112395087", 1438692801766, "四川大学");
//select * from custumor;

//select * from custumor where phoneNumber = 13111895087;
//select name,sex,createDate,birthday,school,grade from custumor where phoneNumber = 13111895087;

//update custumor set sex=1,grade=3 where phoneNumber = 13111895087;
//sex birthday grade