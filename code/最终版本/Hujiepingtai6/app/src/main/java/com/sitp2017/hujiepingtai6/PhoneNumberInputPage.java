package com.sitp2017.hujiepingtai6;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PhoneNumberInputPage extends AppCompatActivity { //电话设置界面的滑动

    private EditText phoneNumberInput;
    private TextView nextStep;
    private String phoneNumberText;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    break;
                case 2:
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(PhoneNumberInputPage.this);
                    alertDialog.setTitle("Error!");
                    alertDialog.setMessage("无网络连接或其他内部错误");
                    alertDialog.show();
                    break;
            }
        }
    };


    private void initSurface() {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        StatusBarColorUtil.StatusBarLightMode(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ActivityCollector.addActivity(this);

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
                phoneNumberText = phoneNumberInput.getText().toString();
                if (phoneNumberText.length() <= 0) {
                    Toast.makeText(getApplicationContext(), "手机号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    if (SQLService.checkPhoneNumber(phoneNumberText)) {
                        Toast.makeText(getApplicationContext(), "输入的手机号已注册", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                catch (Exception e) {
                    Message message = new Message();//发送一个消息，该消息用于在handleMessage中区分是谁发过来的消息；
                    message.what = 2;
                    handler.sendMessage(message);
                    return;
                }
                MyApplication.getInstance().customer.setPhoneNumber(phoneNumberText);
                Intent intent = new Intent(PhoneNumberInputPage.this, SecurityCodeInputPage.class);
                startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(PhoneNumberInputPage.this).toBundle());
            }
        });
    }

    private void findViews() {
        phoneNumberInput = (EditText) findViewById(R.id.edit_view_phone_number_input);
        nextStep = (TextView) findViewById(R.id.text_view_next_step_0);
    }
}
