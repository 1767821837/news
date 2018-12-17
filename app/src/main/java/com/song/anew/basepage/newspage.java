package com.song.anew.basepage;

import android.content.Context;

import com.song.anew.BasePage;

public class newspage extends BasePage {
    public newspage(Context context) {
        super(context);
    }

    @Override
    public void initData() {

        super.initData();
        tv_title.setText("我是新闻页面");
    }
}
