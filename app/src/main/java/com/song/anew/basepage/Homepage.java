package com.song.anew.basepage;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.song.anew.BasePage;
import com.song.anew.Bean.HomePageBean;
import com.song.anew.R;
import com.song.anew.activity.Mainactivity;
import com.song.anew.adapter.ViewpagerAdapter;
import com.song.anew.fragment.MyFragment;
import com.song.anew.util.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;

public class Homepage extends BasePage {
    public static final int ERROR = 101;
    public static final int SUCCESS = 102;

    String[] pageTitles = {"北京", "中国", "国际", "文娱", "体育", "生活", "旅游", "科技", "军事", "财经", "女性", "倍儿逗"};
    private ArrayList<MyFragment> arrayList = new ArrayList<>();
    private ViewpagerAdapter adapter;

    public Homepage(final Context context) {
        super(context);

        for (int i = 0; i < pageTitles.length; i++) {
            arrayList.add(new MyFragment(pageTitles[i], R.layout.index_layout,i,context));
        }
    }

    @Override
    public void initData() {
        super.initData();
        adapter = new ViewpagerAdapter(((Mainactivity) context).getContentFragment().getChildFragmentManager(), arrayList, context);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new mypagechange());


        tablayout.setViewPager(viewPager,pageTitles);
initnewData();
        //tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    private void initnewData() {

    }

    class mypagechange implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int i, float v, int i1) {
            if (i != 0) {
                Mainactivity mainactivity = (Mainactivity) context;
                mainactivity.setNohua(0);
            } else {
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
