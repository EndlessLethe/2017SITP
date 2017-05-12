package com.sitp2017.hujiepingtai6;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by gameben on 2017/5/8.
 */
public class MyFragment extends Fragment { //决定每个MainPage中的Pager的布局

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout, container,false);
        TextView pagerTitle = (TextView) view.findViewById(R.id.pager_title);
        pagerTitle.setText(getArguments().getString("param") + "地图信息");
        return view;
    }
}

