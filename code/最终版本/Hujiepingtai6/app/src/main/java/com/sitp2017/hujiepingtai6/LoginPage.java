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
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

public class LoginPage extends AppCompatActivity implements View.OnClickListener{

    private EditText loginPhoneNumberInput;
    private EditText loginPasswordInput;
    private Button loginApp;
    private TextView signOn;
    private String phoneNumber;
    private String password;
    private TextView noticeText;
    private ProgressDialog progressDialog;

    private final String TAG = "LoginPage";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    noticeText.setText("用户不存在或密码不正确！点击");
                    break;
                case 2:
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginPage.this);
                    alertDialog.setTitle("Error!");
                    alertDialog.setMessage("无网络连接或其他内部错误");
                    alertDialog.show();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ActivityCollector.addActivity(this);
        super.onCreate(savedInstanceState);
        initSurface();
        setContentView(R.layout.activity_login_page);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }
        findViews();
    }

    private void initSurface() {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        StatusBarColorUtil.StatusBarLightMode(this);
    }

    private void findViews() {
        loginApp = (Button) findViewById(R.id.login_app);
        loginApp.setOnClickListener(this);
        signOn = (TextView) findViewById(R.id.sign_on);
        signOn.setOnClickListener(this);
        noticeText = (TextView) findViewById(R.id.notice_text);

        loginPhoneNumberInput = (EditText) findViewById(R.id.login_phone_number_input);
        loginPasswordInput = (EditText) findViewById(R.id.login_password_input);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_on:
                Intent intent1 = new Intent(LoginPage.this, PhoneNumberInputPage.class);
                startActivity(intent1, ActivityOptions.makeSceneTransitionAnimation(LoginPage.this).toBundle());
                break;
            case R.id.login_app:
                new VerifyTask().execute();
                break;
            default:
                break;
        }
    }

    class LoginTask extends AsyncTask<String, Void, Boolean>{
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(LoginPage.this);
            progressDialog.setTitle("登陆成功！正在加载资源");
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }
        @Override
        protected Boolean doInBackground(String...params){
            try {
                MyApplication.getInstance().Login(phoneNumber, password);
            }
            catch (Exception e) {
                return false;
            }
            return true;
        }
        @Override
        protected void onPostExecute(Boolean result) {
            progressDialog.dismiss();
            if (result) {
                Intent intent2 = new Intent(LoginPage.this, MainPage.class);
                startActivity(intent2, ActivityOptions.makeSceneTransitionAnimation(LoginPage.this).toBundle());
            }
        }
    }

    class VerifyTask extends AsyncTask<String, Void, Integer>{
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(LoginPage.this);
            progressDialog.setTitle("正在登陆");
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }
        @Override
        protected Integer doInBackground(String...params){
            try {
                phoneNumber = loginPhoneNumberInput.getText().toString();
                password = loginPasswordInput.getText().toString();
                if (phoneNumber.length() <= 0 || password.length() > 11) return 0;
                if (!SQLService.checkPhoneNumber(phoneNumber)) {
                    return 0;
                }
                if (!SQLService.isVerified(phoneNumber, password)) return 0;
            }
            catch (Exception e) {
                Log.d(TAG, "收到异常");
                return -1;
            }
            return 1;
        }
        @Override
        protected void onPostExecute(Integer result) {
            progressDialog.dismiss();
            if (result == 1) {
                new LoginTask().execute();
            }
            else if (result == 0){
                Message message = new Message();//发送一个消息，该消息用于在handleMessage中区分是谁发过来的消息；
                message.what = 1;
                handler.sendMessage(message);
            }
            else {
                Message message = new Message();//发送一个消息，该消息用于在handleMessage中区分是谁发过来的消息；
                message.what = 2;
                handler.sendMessage(message);
            }
        }
    }
}

