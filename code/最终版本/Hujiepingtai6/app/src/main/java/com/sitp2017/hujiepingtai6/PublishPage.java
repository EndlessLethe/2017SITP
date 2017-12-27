package com.sitp2017.hujiepingtai6;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PublishPage extends AppCompatActivity implements View.OnClickListener{

    private List<String> itemList = new ArrayList<>();
    private PopupWindow popupItem;
    private PickerView itemPicker;
    private TextView popItemConfirm;
    private TextView popItemCancel;
    private TextView itemType;
    private RelativeLayout selectItemType;
    private Button submitButton;
    private EditText demandDescription;

    private void findViews() {
        itemType = (TextView) findViewById(R.id.item_type);
        submitButton = (Button) findViewById(R.id.submit_demand_button);
        submitButton.setOnClickListener(this);
        selectItemType = (RelativeLayout) findViewById(R.id.select_item_type);
        selectItemType.setOnClickListener(this);
        demandDescription = (EditText) findViewById(R.id.text_demand_description);
    }

    private void initPopupItem() {
        itemList.add("充电宝");
        itemList.add("篮球");
        itemList.add("雨伞");

        View contentView = LayoutInflater.from(PublishPage.this).inflate(R.layout.popup_select_item, null);

        popupItem = new PopupWindow(contentView);

        popupItem.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupItem.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupItem.setBackgroundDrawable(new ColorDrawable(0x00000000));//不这样无法通过点击空白处退出

        popupItem.setOutsideTouchable(true);
        popupItem.setFocusable(true);
        popupItem.setAnimationStyle(R.style.popup_bottom_anim);

        itemPicker = (PickerView) contentView.findViewById(R.id.item_picker);
        itemPicker.setData(itemList);


        popupItem.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });

        popItemCancel = (TextView)contentView.findViewById(R.id.popup_item_cancel);
        popItemCancel.setOnClickListener(this);

        popItemConfirm = (TextView)contentView.findViewById(R.id.popup_item_confirm);
        popItemConfirm.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_page);

        StatusBarColorUtil.StatusBarLightMode(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        findViews();
        initPopupItem();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.popup_item_confirm:
                itemType.setText(itemPicker.getCurSelected() + "  >");
                popupItem.dismiss();
                break;
            case R.id.popup_item_cancel:
                popupItem.dismiss();
                break;
            case R.id.select_item_type:
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.7f;
                getWindow().setAttributes(lp); //设置背景变暗
                popupItem.showAtLocation(findViewById(R.id.select_item_type), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.submit_demand_button:
                SQLService.insertDemand(MyApplication.customer.getPhoneNumber(),
                        itemPicker.getCurSelected(), demandDescription.getText().toString());
                finish();
                break;
            default:
                break;
        }
    }
}
