package com.sitp2017.hujiepingtai6;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity { //展示4张图片的APP开始介绍活动页面
    // 到达最后一张
    private static final int TO_THE_END = 0;
    // 离开最后一张
    private static final int LEAVE_FROM_END = 1;
    // 如果想直接应用到你的项目中，只需在这里添加删除图片id即可
    private int[] ids = { R.drawable.guide_1,
            R.drawable.guide_2, R.drawable.guide_3,
            R.drawable.guide_4
    };
    private List<View> guides = new ArrayList<View>();
    private ViewPager pager;
    private TextView start;     // 点击体验
    private ImageView curDot;
    private LinearLayout dotContain; // 存储点的容器
    private int offset;       // 位移量
    private int curPos = 0;     // 记录当前的位置
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        ActivityCollector.addActivity(this);

        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        StatusBarColorUtil.transparencyBar(this);
        setContentView(R.layout.activity_guide);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }
        init();
    }
    private ImageView buildImageView(int id)
    {
        ImageView iv = new ImageView(this);
        iv.setImageResource(id);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        iv.setLayoutParams(params);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        return iv;
    }
    // 功能介绍界面的初始化
    private void init()
    {
        this.getView();
        initDot();
        ImageView iv = null;
        guides.clear();
        for (int i = 0; i < ids.length; i++) {
            iv = buildImageView(ids[i]);
            guides.add(iv);
        }
        System.out.println("guild_size="+guides.size());
        // 当curDot的所在的树形层次将要被绘出时此方法被调用
        curDot.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    public boolean onPreDraw() {
                        // 获取ImageView的宽度也就是点图片的宽度
                        offset = curDot.getWidth();
                        return true;
                    }
                });
        final GuidePagerAdapter adapter = new GuidePagerAdapter(guides);
        // ViewPager设置数据适配器，这个类似于使用ListView时用的adapter
        pager.setAdapter(adapter);
        pager.clearAnimation();
        // 为Viewpager添加事件监听器 OnPageChangeListener
        pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position)
            {
                int pos = position % ids.length;
                moveCursorTo(pos);
                if (pos == ids.length-1) {// 到最后一张了
                    handler.sendEmptyMessageDelayed(TO_THE_END, 500);
                } else if (curPos == ids.length - 1) {
                    handler.sendEmptyMessageDelayed(LEAVE_FROM_END, 100);
                }
                curPos = pos;
                super.onPageSelected(position);
            }
        });
    }
    /**
     * 在layout中实例化一些View
     */
    private void getView()
    {
        dotContain = (LinearLayout)this.findViewById(R.id.dot_contain);
        pager = (ViewPager) findViewById(R.id.contentPager);
        curDot = (ImageView) findViewById(R.id.cur_dot);
        start = (TextView) findViewById(R.id.open);
        start.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(GuideActivity.this, LoginPage.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(GuideActivity.this).toBundle());
            }
        });
    }
    /**
     * 初始化点 ImageVIew
     * @return 返回true说明初始化点成功，否则实例化失败
     */
    private boolean initDot()
    {
        if(ids.length > 0){
            ImageView dotView ;
            for(int i=0; i<ids.length; i++)
            {
                dotView = new ImageView(this);
                dotView.setImageResource(R.drawable.dot_selected);
                dotView.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,1.0f));
                dotContain.addView(dotView);
            }
            return true;
        }else{
            return false;
        }
    }
    /**
     * 移动指针到相邻的位置 动画
     * @param position
     * 指针的索引值
     * */
    private void moveCursorTo(int position) {
        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation tAnim =
                new TranslateAnimation(offset*curPos, offset*position, 0, 0);
        animationSet.addAnimation(tAnim);
        animationSet.setDuration(300);
        animationSet.setFillAfter(true);
        curDot.startAnimation(animationSet);
    }
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == TO_THE_END)
                start.setVisibility(View.VISIBLE);
            else if (msg.what == LEAVE_FROM_END)
                start.setVisibility(View.GONE);
        }
    };
    // ViewPager 适配器
    class GuidePagerAdapter extends PagerAdapter {
        private List<View> views;
        public GuidePagerAdapter(List<View> views){
            this.views=views;
        }
//        @Override
//        public void destroyItem(View arg0, int arg1, Object arg2) {
//            ((ViewPager) arg0).removeView(views.get(arg1 % views.size()));
//        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position % views.size()));
        }

        @Override
        public int getCount() {
            // 注意这里一定要返回一个稍微大点值,不然滑到顶就滑不动了
            return views.size()*20;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Log.e("tag", "instantiateItem = "+ position);
            container.addView(views.get(position % views.size()),0);
            return views.get(position % views.size());
        }

//        @Override
//        public Object instantiateItem(View arg0, int arg1) {
//            Log.e("tag", "instantiateItem = "+arg1);
//            ((ViewPager) arg0).addView(views.get(arg1 % views.size()),0);
//            return views.get(arg1 % views.size());
//        }
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == (arg1);
        }
        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }
        @Override
        public Parcelable saveState() {
            return null;
        }

    }

}
