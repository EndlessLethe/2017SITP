package com.sitp2017.hujiepingtai6;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gameben on 2017/5/12.
 */
public class ActivityCollector { //用于管理所有的活动

    public static List<AppCompatActivity> activities = new ArrayList<>();

    public static void addActivity(AppCompatActivity activity) {
        activities.add(activity);
    }

    public static void removeActivity(AppCompatActivity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for(AppCompatActivity activity : activities) {
            if(!activity.isFinishing()) {
                activity.finish();
            }
        }
        activities.clear();
    }
}
