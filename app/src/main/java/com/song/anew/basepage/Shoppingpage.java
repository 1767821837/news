package com.song.anew.basepage;

import android.content.Context;

import com.song.anew.BasePage;

public class Shoppingpage extends BasePage {
    public Shoppingpage(Context context) {
        super(context);
    }

    @Override
    public void initData() {

        super.initData();
        tv_title.setText("我是购物页面");
    }
}
