package com.song.anew.fragment;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.song.anew.BaseFragment;
import com.song.anew.BasePage;
import com.song.anew.R;
import com.song.anew.basepage.Homepage;
import com.song.anew.basepage.Settingpage;
import com.song.anew.basepage.Shoppingpage;
import com.song.anew.basepage.lookpage;
import com.song.anew.basepage.newspage;
import com.song.anew.view.CustomScrollViewPager;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class ContentFragment extends BaseFragment {
    @ViewInject(R.id.viewPager)
    private CustomScrollViewPager viewPagers;
    @ViewInject(R.id.rg_main)
    private RadioGroup rg;
    View view = null;
    private List<BasePage> basePages;

    @Override
    public View initview() {
        view = View.inflate(context, R.layout.content_fragment, null);
        x.view().inject(this, view);
        rg.check(R.id.rb_home);
        rg.setOnCheckedChangeListener(new MyCheckchanglistener());
        basePages = new ArrayList<BasePage>();
        basePages.add(new Homepage(context));
        basePages.add(new newspage(context));
        basePages.add(new lookpage(context));
        viewPagers.setAdapter(new ViewPagerAdapter());
        viewPagers.setOffscreenPageLimit(4);
        return view;
    }


    class MyCheckchanglistener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_home:
                    Log.e("tyh", "onCheckedChanged: 1");
                    viewPagers.setCurrentItem(0,false);
                    break;
                case R.id.rg_news:
                    Log.e("tyh", "onCheckedChanged: 2");
                    viewPagers.setCurrentItem(1,false);
                    break;
                case R.id.rb_look:
                    Log.e("tyh", "onCheckedChanged: 3");
                    viewPagers.setCurrentItem(2,false);
                    break;
                case R.id.rb_setting:
                    Log.e("tyh", "onCheckedChanged: 4");
                    viewPagers.setCurrentItem(3,false);
                    break;
            }
        }
    }


    class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return basePages.size();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = basePages.get(position).ROotview;
            basePages.get(position).initData();
            container.addView(view);
            return view;
        }


        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }


    @Override
    public void initdata() {
        super.initdata();
        basePages.get(0).initData();
    }

}
