package com.song.anew.basepage;

import android.content.Context;

import com.song.anew.BasePage;

public class lookpage extends BasePage {
    public lookpage(Context context) {
        super(context);
    }

    @Override
    public void initData() {

        super.initData();
        tv_title.setText("我是查看页面");
    }
}
