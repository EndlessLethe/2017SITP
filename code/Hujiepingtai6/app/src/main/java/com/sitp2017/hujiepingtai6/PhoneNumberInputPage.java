package com.sitp2017.hujiepingtai6;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

public class PhoneNumberInputPage extends AppCompatActivity { //电话设置界面的滑动

    private EditText phoneNumberInput;

    private TextView nextStep;

    private void initSurface() {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        StatusBarColorUtil.StatusBarLightMode(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSurface();
        setContentView(R.layout.activity_phone_number_input_page);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }

        findViews();
        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhoneNumberInputPage.this, SecurityCodeInputPage.class);
                startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(PhoneNumberInputPage.this).toBundle());
               // overridePendingTransition(R.transition.slide_start, R.transition.slide_end);
            }
        });
    }

    private void findViews() {
        phoneNumberInput = (EditText) findViewById(R.id.edit_view_phone_number_input);
        nextStep = (TextView) findViewById(R.id.text_view_next_step_0);
    }


}
