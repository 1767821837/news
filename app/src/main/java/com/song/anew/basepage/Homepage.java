package com.song.anew.basepage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.song.anew.BasePage;
import com.song.anew.Bean.HomePageBean;
import com.song.anew.activity.Mainactivity;
import com.song.anew.fragment.ContentFragment;
import com.song.anew.fragment.LiftmenuFragment;
import com.song.anew.fragment.TestFragment;
import com.song.anew.util.Constants;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class Homepage extends BasePage {
    String[] pageTitles = {"北京", "中国", "国际", "文娱", "体育", "生活", "旅游", "科技", "军事", "财经", "女性", "倍儿逗"};

    public Homepage(Context context) {

        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        Log.i("##@@", "getPageTitle: " + pageTitles[1]);
        tv_title.setText("我是主页页面");
        viewPager.setVisibility(View.VISIBLE);
        indicator.setVisibility(View.VISIBLE);
        Mainactivity mainactivity = (Mainactivity) context;
        ContentFragment contentFragment = mainactivity.getContentFragment();
        FragmentManager fm = contentFragment.getChildFragmentManager();
        viewPager.setAdapter(new VpAdapter(mainactivity.getSupportFragmentManager()));

        getDataFromNet();

    }


    public void getDataFromNet() {
        RequestParams params = new RequestParams(Constants.HOME_PAGE_URL);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                HomePageBean homePageBean = processData(result);
                Mainactivity mainactivity = (Mainactivity) context;
                LiftmenuFragment liftmenuFragment = mainactivity.getLiftmenuFragment();
                liftmenuFragment.setData(homePageBean.getData());
                Log.i("*****", "onSuccess: " + homePageBean.getData().get(0).getChildren().get(0).getTitle());
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("******", "onError: " + ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
                Log.i("******", "onFinished: ");
            }
        });

    }

    /**
     * 解析数据
     *
     * @param result json 解析
     */
    private HomePageBean processData(String result) {
        Gson gson = new Gson();
        HomePageBean homePageBean = gson.fromJson(result, HomePageBean.class);

        return homePageBean;
    }


    class VpAdapter extends FragmentPagerAdapter {
        VpAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            Log.i("##@@", "getPageTitle:1111 " + pageTitles.length);
            return pageTitles.length;
    }

        @Override
        public CharSequence getPageTitle(int position) {
            Log.i("##@@", "getPageTitle:1111 " + pageTitles[position]);
            return pageTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return new TestFragment();
        }
    }
}
