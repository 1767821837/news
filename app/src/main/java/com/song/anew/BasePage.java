package com.song.anew;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.song.anew.activity.Mainactivity;

/**
 * 公共类
 *
 */
public class BasePage {
    public Context context;
    public View ROotview;//代表各个不同的页面
    public Button but_back;
    public TextView tv_title;
    public ViewPager viewPager;
    public TabLayout tablayout;
    public com.song.anew.view.SimpleViewpagerIndicator indicator;


    public BasePage(Context context) {
        this.context = context;
        ROotview = initview();
    }

    private View initview() {
        View view = View.inflate(context, R.layout.base_page, null);
        but_back = view.findViewById(R.id.but_back);
        tv_title = view.findViewById(R.id.tv_title);
        viewPager = view.findViewById(R.id.viewpager);
        //indicator = view.findViewById(R.id.indicator);
        tablayout=view.findViewById(R.id.tablayout);
but_back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Mainactivity mainActivity = (Mainactivity) context;
        mainActivity.getSlidingMenu().toggle();
    }
});

        return view;
    }

    public void initData() {

    }

}
