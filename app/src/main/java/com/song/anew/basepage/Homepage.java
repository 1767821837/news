package com.song.anew.basepage;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

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
        arrayList.add(new MyFragment("标题3", R.layout.index_layout));
        arrayList.add(new MyFragment("标题4", R.layout.index_layout));
        arrayList.add(new MyFragment("标题5", R.layout.index_layout));
        arrayList.add(new MyFragment("标题6", R.layout.index_layout));
    }

    @Override
    public void initData() {
        super.initData();

        adapter = new ViewpagerAdapter(((Mainactivity) context).getfragmentmanager(), arrayList ,context);
        viewPager.setAdapter(adapter);
viewPager.addOnPageChangeListener(new mypagechange());
        tablayout.setupWithViewPager(viewPager);
        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    class mypagechange implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int i, float v, int i1) {
            if(i!=0){
                Mainactivity mainactivity = (Mainactivity) context;
                mainactivity.setNohua(0);
            }else {
                Mainactivity mainactivity = (Mainactivity) context;
                mainactivity.setNohua(1);
            }
        }

        @Override
        public void onPageSelected(int i) {

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }

}
