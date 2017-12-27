package com.sitp2017.hujiepingtai6;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MyOrderPage extends AppCompatActivity {

    private Toolbar toolbar;

    private void initSurface() {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS); //声明会使用动画
        StatusBarColorUtil.StatusBarLightMode(this); //设置白底黑字导航栏
    }

    private List<Order> orderList = new ArrayList<>();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ActivityCollector.addActivity(this);

        super.onCreate(savedInstanceState);
        initSurface();
        setContentView(R.layout.activity_my_order_page);

        toolbar = (Toolbar) findViewById(R.id.toolbar_my_order);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initOrders();

        //获取recyclerView的实例
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_my_order);
        //RecyclerView用哪种布局，这里就建立哪种manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        OrderAdapter adapter = new OrderAdapter(orderList);
        recyclerView.setAdapter(adapter);
    }

    private void initOrders() {
        for(int i = 0; i < 5; i++) {
            orderList.add(new Order(Calendar.getInstance(), Order.SUCCESS, Order.POWERBANK));
        }
        for(int i = 0; i < 5; i++) {
            orderList.add(new Order(Calendar.getInstance(), Order.CANCELED, Order.BASKETBALL));
        }
        for(int i = 0; i < 5; i++) {
            orderList.add(new Order(Calendar.getInstance(), Order.PROGRESS, Order.UMBRELLA));
        }
    }
}
