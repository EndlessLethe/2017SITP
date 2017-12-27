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

    //个人信息中性别设置所需

    private List<String> sexList = new ArrayList<>();

    private PickerView sexPicker;

    private PopupWindow popupSex;

    private PercentRelativeLayout sexPickLayout;

    private TextView popSexCancel;

    private TextView popSexConfirm;

    private TextView navSetSex;

    //个人信息中生日设置所需

    private List<String> birthYearList = new ArrayList<>();

    private List<String> birthMonthList = new ArrayList<>();

    private List<String> birthDayList = new ArrayList<>();

    private PickerView birthYearPicker;

    private PickerView birthMonthPicker;

    private PickerView birthDayPicker;

    private PopupWindow popupBirth;

    private PercentRelativeLayout birthPickLayout;

    private TextView popBirthCancel;

    private TextView popBirthConfirm;

    private TextView navSetBirth;

    //个人信息中年级设置所需

    private List<String> gradeList = new ArrayList<>();

    private PickerView gradePicker;

    private PopupWindow popupGrade;

    private PercentRelativeLayout gradePickLayout;

    private TextView popGradeCancel;

    private TextView popGradeConfirm;

    private TextView navSetGrade;

    private TextView popNameInfo;
    private TextView popSetName;

    private void initSurface() {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS); //声明会使用动画
        StatusBarColorUtil.StatusBarLightMode(this); //设置白底黑字导航栏

    }

    private void initPopSex() {
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

    private void initPopBirth() {
        for(int i = 1910; i <= 2025; i++) {
            birthYearList.add("" + i);
        }
        for(int i = 1; i <= 12; i++) {
            birthMonthList.add("" + i);
        }
        for(int i = 1; i <= 31; i++) {
            birthDayList.add("" + i);
        }

        View contentView = LayoutInflater.from(NavPersonalImforPage.this).inflate(R.layout.popup_select_birth_style, null);

        popupBirth = new PopupWindow(contentView);

        popupBirth.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupBirth.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupBirth.setBackgroundDrawable(new ColorDrawable(0x00000000));//不这样无法通过点击空白处退出

        popupBirth.setOutsideTouchable(true);
        popupBirth.setFocusable(true);
        popupBirth.setAnimationStyle(R.style.popup_bottom_anim);

        birthYearPicker = (PickerView) contentView.findViewById(R.id.birth_year_picker);
        birthMonthPicker = (PickerView) contentView.findViewById(R.id.birth_month_picker);
        birthDayPicker = (PickerView) contentView.findViewById(R.id.birth_day_picker);

        birthYearPicker.setData(birthYearList);
        birthMonthPicker.setData(birthMonthList);
        birthDayPicker.setData(birthDayList);

        popupBirth.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });

        popBirthCancel = (TextView)contentView.findViewById(R.id.popup_birth_cancel);
        popBirthCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupBirth.dismiss();
            }
        });

        popBirthConfirm = (TextView)contentView.findViewById(R.id.popup_birth_confirm);
        popBirthConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navSetBirth.setText(birthYearPicker.getCurSelected() + "-" +
                birthMonthPicker.getCurSelected() + "-" +
                birthDayPicker.getCurSelected() + "  >");
                popupBirth.dismiss();
            }
        });
    }

    private void initPopGrade() {
        gradeList.add("大一");
        gradeList.add("大二");
        gradeList.add("大三");
        gradeList.add("大四");
        gradeList.add("硕士生");
        gradeList.add("博士生");

        View contentView = LayoutInflater.from(NavPersonalImforPage.this).inflate(R.layout.popup_select_grade_style, null);

        popupGrade = new PopupWindow(contentView);

        popupGrade.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupGrade.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupGrade.setBackgroundDrawable(new ColorDrawable(0x00000000));//不这样无法通过点击空白处退出

        popupGrade.setOutsideTouchable(true);
        popupGrade.setFocusable(true);
        popupGrade.setAnimationStyle(R.style.popup_bottom_anim);

        gradePicker = (PickerView) contentView.findViewById(R.id.grade_picker);
        gradePicker.setData(gradeList);


        popupGrade.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });

        popGradeCancel = (TextView)contentView.findViewById(R.id.popup_grade_cancel);
        popGradeCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupGrade.dismiss();
            }
        });

        popGradeConfirm = (TextView)contentView.findViewById(R.id.popup_grade_confirm);
        popGradeConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navSetGrade.setText(gradePicker.getCurSelected() + "  >");
                popupGrade.dismiss();
            }
        });

        popNameInfo = (TextView) findViewById(R.id.nav_username_imfor);
        popNameInfo.setText(MyApplication.getInstance().customer.getName());
        popSetName = (TextView) findViewById(R.id.nav_set_name);
        popSetName.setText(MyApplication.getInstance().customer.getName());
    }

    private void initPopupWindow() {
        initPopSex();
        initPopBirth();
        initPopGrade();
    }

    private void showPopSexWindow() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp); //设置背景变暗
        popupSex.showAtLocation(findViewById(R.id.quit_app), Gravity.BOTTOM, 0, 0);
    }

    private void showPopBirthWindow() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp); //设置背景变暗
        popupBirth.showAtLocation(findViewById(R.id.quit_app), Gravity.BOTTOM, 0, 0);
    }

    private void showPopGradeWindow() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.7f;
        getWindow().setAttributes(lp); //设置背景变暗
        popupGrade.showAtLocation(findViewById(R.id.quit_app), Gravity.BOTTOM, 0, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ActivityCollector.addActivity(this);

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

        //find sex
        sexPickLayout = (PercentRelativeLayout) findViewById(R.id.sex_pick_layout);
        sexPickLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopSexWindow();
                //Toast.makeText(NavPersonalImforPage.this, "fuck0", Toast.LENGTH_SHORT).show();
            }
        });
        navSetSex = (TextView) findViewById(R.id.nav_set_sex);

        //find birth
        birthPickLayout = (PercentRelativeLayout) findViewById(R.id.birth_pick_layout);
        birthPickLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopBirthWindow();;
            }
        });
        navSetBirth = (TextView) findViewById(R.id.nav_set_birth);

        //find grade
        gradePickLayout = (PercentRelativeLayout) findViewById(R.id.grade_pick_layout);
        gradePickLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopGradeWindow();
            }
        });
        navSetGrade = (TextView) findViewById(R.id.nav_set_grade);
    }
}
