package com.sitp2017.hujiepingtai6;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

/**
 * Created by gameben on 2017/5/5.
 */
public class IDVerficationPage extends AppCompatActivity implements View.OnClickListener{ //学生教职工身份认证活动页面

    private Button confirmVerfication;
    private EditText setName, setSchool, setId;
    private String nameText, schoolText, idText;
    private ProgressDialog progressDialog;

    private void initSurface() {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        StatusBarColorUtil.StatusBarLightMode(this);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        ActivityCollector.addActivity(this);

        super.onCreate(savedInstanceState);
        initSurface();
        setContentView(R.layout.activity_idverfication_page);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }


        findViews();

        confirmVerfication.setOnClickListener(this);
    }

    private void findViews() {
        confirmVerfication = (Button) findViewById(R.id.button_confirm_verication);
        setName = (EditText) findViewById(R.id.edit_text_set_name);
        setSchool = (EditText) findViewById(R.id.edit_text_set_school);
        setId = (EditText) findViewById(R.id.edit_text_set_id);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_confirm_verication:
                nameText = setName.getText().toString();
                schoolText = setSchool.getText().toString();
                idText = setId.getText().toString();
                int errorCode = isVerified();
                if (errorCode == 1) {
                    MyApplication.getInstance().customer.setName(nameText);
                    MyApplication.getInstance().customer.setSchool(schoolText);
                    MyApplication.getInstance().customer.setId(idText);
                    MyApplication.getInstance().customer.setCreateDate(new Date());

                    new LoginTask().execute();
                    Intent intent = new Intent(IDVerficationPage.this, AppIntroductionPage.class);
                    startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(IDVerficationPage.this).toBundle());
                }
                else if (errorCode == -1) {
                    Toast.makeText(getApplicationContext(), "对不起，数据不能为空", Toast.LENGTH_SHORT).show();
                }
                else if (errorCode == -2) {
                    Toast.makeText(getApplicationContext(), "对不起，目前仅支持四川大学", Toast.LENGTH_SHORT).show();
                }
                else {//errorCode == 0
                    Toast.makeText(getApplicationContext(), "验证失败", Toast.LENGTH_SHORT).show();
                }
            default:
                break;
        }
    }
    private int isVerified() {
        if (nameText.length() <= 0 || schoolText.length() <= 0 || idText.length() <= 0) return -1;
        if (!schoolText.equals("四川大学") && !schoolText.equals("scu")) return -2;
        if (idText.length() != 13 && idText.length() != 8) return 0;
        return 1;
    }

    class LoginTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(IDVerficationPage.this);
            progressDialog.setTitle("登陆成功！正在加载资源");
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }
        @Override
        protected Boolean doInBackground(String...params){
            try {
                SQLService.insertPersonalInfo(MyApplication.getInstance().customer);
                MyApplication.getInstance().Login(MyApplication.getInstance().customer.getPhoneNumber(),
                        MyApplication.getInstance().customer.getPassword());
            }
            catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        @Override
        protected void onPostExecute(Boolean result) {
            progressDialog.dismiss();
            if (result) {
                Intent intent2 = new Intent(IDVerficationPage.this, MainPage.class);
                startActivity(intent2, ActivityOptions.makeSceneTransitionAnimation(IDVerficationPage.this).toBundle());
            }
        }
    }
}

