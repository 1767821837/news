package com.song.anew.basepage;

import android.content.Context;
import android.support.design.widget.TabLayout;

import com.song.anew.BasePage;
import com.song.anew.R;
import com.song.anew.activity.Mainactivity;
import com.song.anew.adapter.ViewpagerAdapter;
import com.song.anew.fragment.MyFragment;

import java.util.ArrayList;

public class Homepage extends BasePage {
    //String[] pageTitles = {"北京", "中国", "国际", "文娱", "体育", "生活", "旅游", "科技", "军事", "财经", "女性", "倍儿逗"};
    private ArrayList<MyFragment> arrayList = new ArrayList<>();
    private ViewpagerAdapter adapter;

    public Homepage(Context context) {
        super(context);
        arrayList.add(new MyFragment("标题1", R.layout.index_layout));
        arrayList.add(new MyFragment("标题2", R.layout.index_layout));
    }

    @Override
    public void initData() {
        super.initData();

        adapter = new ViewpagerAdapter(((Mainactivity) context).getfragmentmanager(), arrayList);
        viewPager.setAdapter(adapter);

        tablayout.setupWithViewPager(viewPager);
        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }
}
