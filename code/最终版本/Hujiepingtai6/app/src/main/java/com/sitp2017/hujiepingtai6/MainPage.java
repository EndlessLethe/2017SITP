package com.sitp2017.hujiepingtai6;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gxz.PagerSlidingTabStrip;
import com.hitomi.cslibrary.CrazyShadow;
import com.hitomi.cslibrary.base.CrazyShadowDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainPage extends AppCompatActivity implements View.OnClickListener{ //主页面

    private final String TAG = "MainPage";
    private List<PopupWindowItem> popItemList = new ArrayList<>();
    private PopupWindowAdapter popAdapter;

    private DrawerLayout mainDrawerLayout;
    private ActionBarDrawerToggle mainDrawerToggle; //实现 drawerlayout 滑动时的动态效果

    //用于实现滑动导航栏
    private PagerSlidingTabStrip mainTab;
    private ViewPager mainPager;

    private Toolbar toolbar;

    private FrameLayout switchRoot;
    RotateAnimation rotateAnimation;

    private RecyclerView recyclerView; //设置为成员变量，不然init函数执行完后就没了

    private RelativeLayout slideLayout;

    private PopupWindow popupWindow; //显示可选物品种类的弹出窗口

    private ImageButton mainMoreWays;

    private CircleImageView sendRequestButton; //页面下方正中的按钮，用于发送请求
    private CircleImageView naviButton;
    private CircleImageView freshButton;

    private CircleImageView navHeadImage; //侧滑菜单头布局中的头像
    private TextView navName;

    private TextView publish; //发布按钮

    private MyFragment myFragment;
    private MyPagerAdapter adapter;

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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();//双击退出函数
        }
        return false;
    }

    private static Boolean isExit = false;

    private void exitBy2Click() { //实现双击退出
        Timer tExit = null;
        if (isExit == false) {
            isExit = true;
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000);
        } else {
            ActivityCollector.finishAll(); //清空所有活动
            System.exit(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ActivityCollector.addActivity(this);

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
                //mainDrawerLayout.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.nav_my_order:
                        Intent intent = new Intent(MainPage.this, MyOrderPage.class);
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainPage.this).toBundle());
                        break;
                    case R.id.nav_invite:
                        break;
                    case R.id.nav_settings:
                        break;
                    case R.id.nav_builders:
                        Intent intent1 = new Intent(MainPage.this, AboutUsPage.class);
                        startActivity(intent1, ActivityOptions.makeSceneTransitionAnimation(MainPage.this).toBundle());
                        break;
                }
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
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        mainPager.setAdapter(adapter);
        mainTab.setViewPager(mainPager);

        //设置 navView 宽度为官方指定的 2/3
        ViewGroup.LayoutParams params = navView.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels * 2 / 3;
        navView.setLayoutParams(params);

        //aaa
        slideLayout = (RelativeLayout) findViewById(R.id.slide_layout);

//        //初始化身份切换按钮（界面右上角）
//        switchRoot = (FrameLayout) findViewById(R.id.main_page_switch_root);
//        duty_0 = (ImageView) findViewById(R.id.main_page_duty_0);
//        duty_1 = (ImageView) findViewById(R.id.main_page_duty_1);
//        rotateAnimation = new RotateAnimation(duty_0, duty_1);
//        switchRoot.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                rotateAnimation.startAnimation();
////                if(slideLayout.getVisibility() == View.GONE) {
////                    slideLayout.setVisibility(View.VISIBLE);
////                } else {
////                    slideLayout.setVisibility(View.GONE); //还应该同时去掉阴影
////                }
//            }
//        });

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
        sendRequestButton = (CircleImageView) findViewById(R.id.send_request);
        sendRequestButton.setOnClickListener(this);
        naviButton = (CircleImageView) findViewById(R.id.button_navi);
        naviButton.setOnClickListener(this);
        freshButton = (CircleImageView) findViewById(R.id.button_fresh);
        freshButton.setOnClickListener(this);

        //初始化侧滑菜单头布局的头像，点击后显示设置
        View headerView = navView.getHeaderView(0);
        navHeadImage = (CircleImageView) headerView.findViewById(R.id.nav_user_head_image);
        navHeadImage.setOnClickListener(this);
        navName = (TextView) findViewById(R.id.nav_username);
//        navName.setText(MyApplication.getInstance().customer.getName());//会闪退 应该修改nav header里的

        publish = (TextView) findViewById(R.id.publish);
        publish.setOnClickListener(this);
    }

    @Override
    public void onClick (View view) {

        switch (view.getId()) {
            case R.id.publish:
                Intent intent1 = new Intent(MainPage.this, PublishPage.class);
                startActivity(intent1, ActivityOptions.makeSceneTransitionAnimation(MainPage.this).toBundle());
                break;
            case R.id.nav_user_head_image:
                Intent intent2 = new Intent(MainPage.this, NavPersonalImforPage.class);
                startActivity(intent2, ActivityOptions.makeSceneTransitionAnimation(MainPage.this).toBundle());
                break;
            case R.id.send_request:
//                Toast.makeText(MainPage.this, "按我你会变得快乐吗=v=", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainPage.this);
                String msg = new String("");
                for (UserLocation ul : MyApplication.userLocations) {
                    msg += ul.getPhoneNumber() + " " + ul.getLatitude() + "\n";
                }
                alertDialog.setMessage(msg);
//                alertDialog.setCancelable(false);
                alertDialog.show();
                break;
            case R.id.button_fresh:
                adapter.fragments.get(0).updateMarkers();
                Toast.makeText(MainPage.this, "正在刷新地图", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_navi:
                adapter.fragments.get(0).zoomToDefault(MyApplication.getInstance().getMyLocation());
                Toast.makeText(MainPage.this, "地图正在聚焦", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }
}

