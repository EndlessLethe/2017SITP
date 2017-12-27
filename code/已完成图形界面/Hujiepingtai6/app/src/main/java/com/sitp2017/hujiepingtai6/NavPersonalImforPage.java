package com.sitp2017.hujiepingtai6;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.android.percent.support.PercentRelativeLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class NavPersonalImforPage extends AppCompatActivity { //身份设置页面的活动

    private Toolbar toolbar;

    private List<String> sexList = new ArrayList<>();

    private PickerView sexPicker;

    private PopupWindow popupSex;

    private PercentRelativeLayout sexPickLayout;

    private TextView popSexCancel;

    private TextView popSexConfirm;

    private TextView navSetSex;

    private void initSurface() {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS); //声明会使用动画
        StatusBarColorUtil.StatusBarLightMode(this); //设置白底黑字导航栏

    }

    private void initPopupWindow() {
        sexList.add("男");
        sexList.add("女");

        View contentView = LayoutInflater.from(NavPersonalImforPage.this).inflate(R.layout.popup_select_sex_style, null);

        popupSex = new PopupWindow(contentView);

        popupSex.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupSex.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupSex.setBackgroundDrawable(new ColorDrawable(0x00000000));//不这样无法通过点击空白处退出

        popupSex.setOutsideTouchable(true);
        popupSex.setFocusable(true);
        popupSex.setAnimationStyle(R.style.popup_bottom_anim);

        sexPicker = (PickerView) contentView.findViewById(R.id.sex_picker);
        sexPicker.setData(sexList);


        popupSex.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });

        popSexCancel = (TextView)contentView.findViewById(R.id.popup_sex_cancel);
        popSexCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupSex.dismiss();
            }
        });

        popSexConfirm = (TextView)contentView.findViewById(R.id.popup_sex_confirm);
        popSexConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navSetSex.setText(sexPicker.getCurSelected() + "  >");
                popupSex.dismiss();
            }
        });
    }

    private void showPopupWindow() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp); //设置背景变暗
        popupSex.showAtLocation(findViewById(R.id.quit_app), Gravity.BOTTOM, 0, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSurface();
        setContentView(R.layout.activity_nav_personal_imfor);

        toolbar = (Toolbar) findViewById(R.id.toolbar_personal_imfor);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        findViews();
        initPopupWindow();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
        }
        return true;
    }

    private void findViews() {
        sexPickLayout = (PercentRelativeLayout) findViewById(R.id.sex_pick_layout);
        sexPickLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupWindow();
                //Toast.makeText(NavPersonalImforPage.this, "fuck0", Toast.LENGTH_SHORT).show();
            }
        });


        navSetSex = (TextView) findViewById(R.id.nav_set_sex);
    }
}
