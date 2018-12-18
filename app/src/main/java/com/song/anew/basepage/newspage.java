package com.song.anew.basepage;

import android.content.Context;

import com.song.anew.BasePage;
import com.song.anew.activity.Mainactivity;

public class newspage extends BasePage {
    public newspage(Context context) {
        super(context);
        Mainactivity mainactivity = (Mainactivity) context;
        mainactivity.setNohua(1);
    }

    @Override
    public void initData() {

        super.initData();
        tv_title.setText("我是新闻页面");
    }
}
