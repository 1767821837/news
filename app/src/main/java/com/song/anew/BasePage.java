package com.song.anew;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * 公共类
 *
 */
public class BasePage {
    public  Context context;
    public View ROotview;//代表各个不同的页面
    public Button but_back;
    public TextView tv_title;

    public ViewPager viewPager;

    public com.song.anew.view.SimpleViewpagerIndicator indicator;
    public BasePage(Context context) {
        this.context = context;
        ROotview = initview();
    }

    private View initview() {
        View view = View.inflate(context,R.layout.base_page,null);
        but_back = view.findViewById(R.id.but_back);
        tv_title = view.findViewById(R.id.tv_title);
        viewPager = view.findViewById(R.id.viewpager);
        indicator = view.findViewById(R.id.indicator);


        return view;
    }

    public  void initData() {

    }

}
