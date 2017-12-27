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

public class SetPasswordPage extends AppCompatActivity {
    //设置密码的活动特免

    private EditText setPassword;

    private TextView nextStep_1;

    private void initSurface() {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        StatusBarColorUtil.StatusBarLightMode(this);

    }

    private void findViews() {
        setPassword = (EditText) findViewById(R.id.edit_text_set_password);
        nextStep_1 = (TextView) findViewById(R.id.text_view_next_step_1);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSurface();
        setContentView(R.layout.activity_set_password_page);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }

        findViews();

        nextStep_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetPasswordPage.this, IDVerficationPage.class);
                startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(SetPasswordPage.this).toBundle());
                //overridePendingTransition(R.transition.slide_start, R.transition.slide_end);
            }
        });
    }
}
