package com.song.anew.basepage;

import android.content.Context;

import com.song.anew.BasePage;
import com.song.anew.activity.Mainactivity;

public class Settingpage extends BasePage {
    public Settingpage(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        Mainactivity mainactivity = (Mainactivity) context;
        mainactivity.setNohua(1);
        super.initData();
        tv_title.setText("我是设置页面");
    }
}
