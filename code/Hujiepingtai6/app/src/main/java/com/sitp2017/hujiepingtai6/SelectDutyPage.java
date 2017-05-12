package com.sitp2017.hujiepingtai6;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class SelectDutyPage extends AppCompatActivity {
    //选择身份（需求，提供）的活动页面

    private FloatingActionButton selectDemander;

    private FloatingActionButton selectProvider;

    private void initSurface() {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        StatusBarColorUtil.StatusBarLightMode(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSurface();
        setContentView(R.layout.activity_select_duty_page);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }

        findViews();
    }

    private void findViews() {
        selectDemander = (FloatingActionButton) findViewById(R.id.fa_button_demander);
        selectProvider = (FloatingActionButton) findViewById(R.id.fa_button_provider);

        selectProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectDutyPage.this, MainPage.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(SelectDutyPage.this).toBundle());
            }
        });

        selectDemander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectDutyPage.this, MainPage.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(SelectDutyPage.this).toBundle());
            }
        });
    }
}
