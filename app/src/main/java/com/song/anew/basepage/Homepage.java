package com.song.anew.basepage;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.song.anew.BasePage;
import com.song.anew.Bean.HomePageBean;
import com.song.anew.activity.Mainactivity;
import com.song.anew.fragment.LiftmenuFragment;
import com.song.anew.util.Constants;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class Homepage extends BasePage {
    public Homepage(Context context) {



        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        tv_title.setText("我是主页页面");
        viewPager.setVisibility(View.VISIBLE);
        indicator.setVisibility(View.VISIBLE);
        getDataFromNet();

    }


     public void getDataFromNet() {
        RequestParams params = new RequestParams(Constants.HOME_PAGE_URL);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
               HomePageBean homePageBean = processData(result);
                Mainactivity mainactivity =(Mainactivity)context;
                LiftmenuFragment liftmenuFragment = mainactivity.getLiftmenuFragment();
                liftmenuFragment.setData(homePageBean.getData());
                Log.i("*****", "onSuccess: "+homePageBean.getData().get(0).getChildren().get(0).getTitle());
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
}
