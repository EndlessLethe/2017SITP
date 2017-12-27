package com.sitp2017.hujiepingtai6;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AppIntroductionPage extends AppCompatActivity { //介绍APP中可以提供互借物品种类的活动页面

    private List<IntroductionItem> introductionList = new ArrayList<>();

    private TextView nextPage_2;

    private void initSurface() {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS); //声明会使用动画
        StatusBarColorUtil.StatusBarLightMode(this); //设置白底黑字导航栏

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ActivityCollector.addActivity(this);

        super.onCreate(savedInstanceState);
        initSurface();
        setContentView(R.layout.activity_app_introduction_page);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }

        initIntroduction();

        //Toast.makeText(AppIntroductionPage.this, introductionList.size(), Toast.LENGTH_SHORT).show();

        //获取recyclerView的实例
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_introduction);
        //RecyclerView用哪种布局，这里就建立哪种manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);//垂直排列
        recyclerView.setLayoutManager(layoutManager);
        IntroductionAdapter adapter = new IntroductionAdapter(introductionList);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RecycleViewDivider(AppIntroductionPage.this, LinearLayoutManager.HORIZONTAL));

        findViews();
    }

    private void findViews() { //获取View的实例 并设置相关的Listener
        nextPage_2 = (TextView) findViewById(R.id.text_view_next_step_2);
        nextPage_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AppIntroductionPage.this, SelectDutyPage.class);
                startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(AppIntroductionPage.this).toBundle());

            }
        });
    }

    private void initIntroduction() { //初始化RecyclerView的List
        IntroductionItem mItem = new IntroductionItem("充电宝", R.mipmap.ic_launcher, "好用的一批哦");
        for(int i = 0; i < 5; i++) {
            introductionList.add(mItem);
        }
    }
}
