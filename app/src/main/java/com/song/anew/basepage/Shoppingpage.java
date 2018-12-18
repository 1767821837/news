package com.song.anew.basepage;

import android.content.Context;

import com.song.anew.BasePage;
import com.song.anew.activity.Mainactivity;

public class Shoppingpage extends BasePage {
    public Shoppingpage(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        Mainactivity mainactivity = (Mainactivity) context;
        mainactivity.setNohua(1);
        super.initData();
        tv_title.setText("我是购物页面");
    }
}
