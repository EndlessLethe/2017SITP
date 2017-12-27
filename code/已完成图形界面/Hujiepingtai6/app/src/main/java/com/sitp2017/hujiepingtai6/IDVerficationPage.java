package com.sitp2017.hujiepingtai6;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * Created by gameben on 2017/5/5.
 */
public class IDVerficationPage extends AppCompatActivity { //学生教职工身份认证活动页面

    private Button confirmVerfication;

    private void initSurface() {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        StatusBarColorUtil.StatusBarLightMode(this);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSurface();
        setContentView(R.layout.activity_idverfication_page);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }

        findViews();
    }

    private void findViews() {
        confirmVerfication = (Button) findViewById(R.id.button_confirm_verication);

        confirmVerfication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IDVerficationPage.this, AppIntroductionPage.class);
                startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(IDVerficationPage.this).toBundle());
                //overridePendingTransition(R.transition.slide_start, R.transition.slide_end);
            }
        });
    }
}
