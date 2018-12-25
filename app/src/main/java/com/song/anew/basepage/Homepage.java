package com.song.anew.basepage;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.song.anew.BasePage;
import com.song.anew.R;
import com.song.anew.activity.Mainactivity;
import com.song.anew.adapter.ViewpagerAdapter;
import com.song.anew.fragment.MyFragment;

import java.util.ArrayList;

public class Homepage extends BasePage {
    public static final int ERROR = 101;
    public static final int SUCCESS = 102;
    private boolean aBoolean = false;
    public int tap = 0;
    String[] pageTitles = {"北京", "中国", "国际", "文娱", "体育", "生活", "旅游", "科技", "军事", "财经", "女性", "倍儿逗"};
    private ArrayList<MyFragment> arrayList = new ArrayList<>();
    private ViewpagerAdapter adapter;

    public Homepage(final Context context) {
        super(context);

        for (int i = 0; i < pageTitles.length; i++) {
            arrayList.add(new MyFragment(pageTitles[i], R.layout.index_layout, i, context));
        }
    }

    @Override
    public void initData() {
        super.initData();
        adapter = new ViewpagerAdapter(((Mainactivity) context).getContentFragment().getChildFragmentManager(), arrayList, context);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(5);
        tablayout.setViewPager(viewPager, pageTitles);
        initnewData();
    }
    private void initnewData() {
    }
}
