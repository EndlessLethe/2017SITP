package com.sitp2017.hujiepingtai6;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NavPersonnalImforNamePage extends AppCompatActivity {

    private EditText namePicker;

    private Toolbar toolbar;

    private TextView confirmName;

    private void initSurface() {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS); //声明会使用动画
        StatusBarColorUtil.StatusBarLightMode(this); //设置白底黑字导航栏
    }

    private void findViews() {
        namePicker = (EditText) findViewById(R.id.name_picker);
        confirmName = (TextView) findViewById(R.id.confirm_name);
        confirmName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("data_return", namePicker.getText().toString());
                Toast.makeText(NavPersonnalImforNamePage.this,
                        "成功更改昵称为:"+ intent.getStringExtra("data_return"),
                        Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ActivityCollector.addActivity(this);

        super.onCreate(savedInstanceState);
        initSurface();
        setContentView(R.layout.activity_nav_personnal_imfor_name_page);

        toolbar = (Toolbar) findViewById(R.id.toolbar_personal_name);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        findViews();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
        }
        return true;
    }
}
