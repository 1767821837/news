package com.song.anew.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.song.anew.R;
import com.song.anew.fragment.TestFragment;
import com.song.anew.view.SimpleViewpagerIndicator;

public class MainActivity1 extends AppCompatActivity {
    SimpleViewpagerIndicator indicator;
    ViewPager viewPager;


    String[] pageTitles = {"北京", "中国", "国际", "文娱", "体育", "生活", "旅游", "科技", "军事", "财经", "女性", "倍儿逗"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new VpAdapter(getSupportFragmentManager()));

        indicator = findViewById(R.id.indicator);
        indicator.setExpand(false)//设置tab宽度为包裹内容还是平分父控件剩余空间，默认值：false,包裹内容
                .setIndicatorWrapText(true)//设置indicator是与文字等宽还是与整个tab等宽，默认值：true,与文字等宽
                .setSelectedTabTextSize(25)//被选中的文字大小
                .setSelectedTabTextColor(Color.parseColor("#ff3300"));//被选中的文字颜色


        indicator.setViewPager(viewPager);
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class VpAdapter extends FragmentPagerAdapter {
        VpAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return pageTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return pageTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return new TestFragment();
        }
    }
}