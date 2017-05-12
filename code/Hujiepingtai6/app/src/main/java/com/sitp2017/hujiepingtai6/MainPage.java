package com.sitp2017.hujiepingtai6;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gxz.PagerSlidingTabStrip;
import com.hitomi.cslibrary.CrazyShadow;
import com.hitomi.cslibrary.base.CrazyShadowDirection;
import com.wangjie.shadowviewhelper.ShadowProperty;
import com.wangjie.shadowviewhelper.ShadowViewHelper;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainPage extends AppCompatActivity { //主页面

    private List<PopupWindowItem> popItemList = new ArrayList<>();
    private PopupWindowAdapter popAdapter;

    private DrawerLayout mainDrawerLayout;
    private ActionBarDrawerToggle mainDrawerToggle; //实现 drawerlayout 滑动时的动态效果

    //用于实现滑动导航栏
    private PagerSlidingTabStrip mainTab;
    private ViewPager mainPager;

    private Toolbar toolbar;

    private ImageButton switchButton;

    private RecyclerView recyclerView; //设置为成员变量，不然init函数执行完后就没了


    private PopupWindow popupWindow; //显示可选物品种类的弹出窗口

    private ImageButton mainMoreWays;

    private CircleImageView sendRequest; //页面下方正中的按钮，用于发送请求

    private CircleImageView navHeadImage; //侧滑菜单头布局中的头像

    private void initPopupWindow() { //初始化PopupWindow以及里面的RecyclerView

        for(int i = 0; i < 9; i++) {
            popItemList.add(new PopupWindowItem(R.mipmap.ic_launcher, "充电宝"));
        }


        View contentView = LayoutInflater.from(MainPage.this).inflate(R.layout.popup_window_style, null);
        recyclerView = (RecyclerView) contentView.findViewById(R.id.recycler_view_pop_window); //不能用主活动的view来find！！
        popupWindow = new PopupWindow(contentView);

        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));//不这样无法通过点击空白处退出

        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        //popupWindow.setAnimationStyle(R.style.popwin_anim_style);


        GridLayoutManager layoutManager = new GridLayoutManager(MainPage.this, 3);
        recyclerView.setLayoutManager(layoutManager);
        popAdapter = new PopupWindowAdapter(popItemList);
        recyclerView.setAdapter(popAdapter);
    }

    private void initSurface() {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS); //声明会使用动画
        StatusBarColorUtil.StatusBarLightMode(this); //设置白底黑字导航栏

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSurface();

        setContentView(R.layout.activity_main_page);

        initPopupWindow();

        findViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //HomeAsUp按钮的id永远是 android.R.id.home
            //添加的代码
            case android.R.id.home:
                mainDrawerLayout.openDrawer(GravityCompat.START); //点击按钮后显示滑动菜单
                //START与之前指定的一致
                break;
            //添加完毕

        }
        return true;
    }


    private void showPopupWindow() {

        popupWindow.showAsDropDown(toolbar); //让弹出菜单显示在Toolbar下面
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mainDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        setSupportActionBar(toolbar);

        //设置侧滑菜单页面
        final NavigationView navView = (NavigationView) findViewById(R.id.nav_view);//获取实例
        //navView.setCheckedItem(R.id.nav_call); //设置menu的某一项为默认选中
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override//设置选中后的事件
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(false);
                mainDrawerLayout.closeDrawers();
                return true;
            }
        });

        //在toolbar上显示HomeAsUp按钮
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        mainDrawerToggle = new ActionBarDrawerToggle(this, mainDrawerLayout, R.string.app_name, R.string.app_name);
//        mainDrawerToggle.syncState();
//        mainDrawerLayout.addDrawerListener(mainDrawerToggle);
        //注意，这里是用add， 不在用set方法（过时）

        //初始化Tab，并让Tan与ViewPager关联
        mainTab = (PagerSlidingTabStrip) findViewById(R.id.main_tab);
        mainPager = (ViewPager) findViewById(R.id.main_pager);
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        mainPager.setAdapter(adapter);
        mainTab.setViewPager(mainPager);

        //设置 navView 宽度为官方指定的 2/3
        ViewGroup.LayoutParams params = navView.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels * 2 / 3;
        navView.setLayoutParams(params);

        //初始化身份切换按钮（界面右上角）
        switchButton = (ImageButton) findViewById(R.id.main_page_duty_switch);
        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //初始化让popupWindow弹出的按钮
        mainMoreWays = (ImageButton) findViewById(R.id.main_page_more_ways);
        mainMoreWays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!popupWindow.isShowing()) {
                    showPopupWindow();
                }
                else {
                    popupWindow.dismiss();
                }
            }
        });

//        ShadowViewHelper.bindShadowHelper(
//                new ShadowProperty()
//                        .setShadowColor(0x77000000)
//                        .setShadowDx(ABTextUtil.dip2px(MainPage.this, 0.5f))
//                        .setShadowRadius(ABTextUtil.dip2px(MainPage.this, 3))
//                , findViewById(R.id.slide_layout));

        //用于在Tab下方显示阴影
        new CrazyShadow.Builder()
                .setContext(this)
                .setDirection(CrazyShadowDirection.BOTTOM)
                .setShadowRadius(ABTextUtil.dip2px(MainPage.this,3))
                .setCorner(ABTextUtil.dip2px(MainPage.this,5))
                .setImpl(CrazyShadow.IMPL_WRAP)
                .action(findViewById(R.id.slide_layout));

        //初始化发送请求的按钮
        sendRequest = (CircleImageView) findViewById(R.id.send_request);
        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainPage.this, "You clicked button", Toast.LENGTH_SHORT).show();
            }
        });

        //初始化侧滑菜单头布局的头像，点击后显示设置
        View headerView = navView.getHeaderView(0);
        navHeadImage = (CircleImageView) headerView.findViewById(R.id.nav_user_head_image);
        navHeadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainPage.this, NavPersonalImfor.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainPage.this).toBundle());
            }
        });

    }


}

