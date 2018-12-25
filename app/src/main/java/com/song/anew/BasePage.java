package com.song.anew;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;
import com.song.anew.Bean.HomePageBean;
import com.song.anew.Bean.VideoBean;
import com.song.anew.activity.Mainactivity;
import com.song.anew.util.Constants;
import com.song.anew.view.VerticalViewPager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * 公共类
 */
public class BasePage {
    public Context context;
    public View ROotview;//代表各个不同的页面
    public Button but_back;
    public TextView tv_title;
    public ViewPager viewPager;
    public SlidingTabLayout tablayout;
    public HomePageBean homePageBean;
    public LinearLayout ll_top;
    public com.song.anew.view.SimpleViewpagerIndicator indicator;
    public LinearLayout ll_news_item;
    public RelativeLayout rl_vido_item;
    public RecyclerView mRecyclerView;
    public VideoBean bean;
    public BasePage(Context context) {
        this.context = context;
        ROotview = initview();
    }

    public  View initview() {
        View view = View.inflate(context, R.layout.base_page, null);
        but_back = view.findViewById(R.id.but_back);
        tv_title = view.findViewById(R.id.tv_title);
        viewPager = view.findViewById(R.id.viewpager);
        ll_top = view.findViewById(R.id.ll_top);
        ll_news_item = view.findViewById(R.id.ll_news_item);
        rl_vido_item = view.findViewById(R.id.rl_vido_item);
        mRecyclerView = view.findViewById(R.id.recycler);

        tablayout = view.findViewById(R.id.tablayout);
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
//  联网获取数据
        OkHttpUtils.get()
                .url(Constants.VIDEO)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        bean = gson.fromJson(response,VideoBean.class);

                    }
                });

    }

}
