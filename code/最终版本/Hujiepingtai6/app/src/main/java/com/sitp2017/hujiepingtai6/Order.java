package com.sitp2017.hujiepingtai6;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by gameben on 2017/8/18.
 */
public class Order { //交易信息记录

    //时间格式
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //状态信息
    public static final int CANCELED = 5;
    public static final int PROGRESS = 6;
    public static final int SUCCESS = 7;

    //物品类别信息
    public static final int POWERBANK = 0;
    public static final int BASKETBALL = 1;
    public static final int UMBRELLA = 2;

    private List<String> goodList = new ArrayList<>(); //list的字符串形式，方便获取对应物品的中文表达

    private Calendar time;

    private int status; //表示此交易的状态，如取消，正在进行，已完成等

    private int goodType; //交易的物品类型

    public Order(Calendar time, int status, int goodType) {
        this.time = time;
        this.status = status;
        this.goodType = goodType;
        goodList.add("充电宝");
        goodList.add("篮球");
        goodList.add("雨伞");
    }

    public Order setTime(Calendar time) {
        this.time = time;
        return this;
    }

    public Order setStatus(int status) {
        this.status = status;
        return this;
    }

    public Order setGoodType(int goodType) {
        this.goodType = goodType;
        return this;
    }

    public String getTimeString() {
        String ans = sdf.format(time.getTime()).toString();
        return ans;
    }

    public String getStatusString() {
        String ans = "";
        switch (status) {
            case CANCELED:
                ans = "已取消";
                break;
            case PROGRESS:
                ans = "进行中";
                break;
            case SUCCESS:
                ans = "已完成";
                break;
        }
        return ans;
    }

    public String getGoodTypeString() {
        return goodList.get(goodType);
    }

    public int getStatus() {
        return status;
    }
}
