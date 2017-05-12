package com.sitp2017.hujiepingtai6;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class SecurityCodeInputPage extends AppCompatActivity implements SecurityCodeView.InputCompleteListener{
    //输出验证码的活动界面

    private SecurityCodeView editText;
    private TextView text;

    private void initSurface() {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        StatusBarColorUtil.StatusBarLightMode(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSurface();
        setContentView(R.layout.activity_security_code_input_page);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }

        findViews();
        setListener();
    }

    private void setListener() {
        editText.setInputCompleteListener(this);
    }

    private void findViews() {
        editText = (SecurityCodeView) findViewById(R.id.scv_edittext);
        text = (TextView) findViewById(R.id.tv_text);
    }

    @Override
    public void inputComplete() {
        //Toast.makeText(getApplicationContext(), "验证码是：" + editText.getEditContent(), Toast.LENGTH_LONG).show();
        if (!editText.getEditContent().equals("1234")) {
            text.setText("验证码输入错误");
            text.setTextColor(Color.RED);
        } else {
            Intent intent = new Intent(this, SetPasswordPage.class);
            startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            //overridePendingTransition(R.transition.slide_start, R.transition.slide_end);;
        }
    }

    @Override
    public void deleteContent(boolean isDelete) {
        if (isDelete){
            text.setText("输入验证码表示同意《用户协议》");
            text.setTextColor(Color.BLACK);
        }
    }
}
