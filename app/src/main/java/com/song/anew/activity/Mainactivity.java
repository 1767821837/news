package com.song.anew.activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Toast;

import com.githang.statusbar.StatusBarCompat;
import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.song.anew.Bean.HomePageBean;
import com.song.anew.R;
import com.song.anew.fragment.ContentFragment;
import com.song.anew.fragment.LiftmenuFragment;
import com.song.anew.util.Constants;
import com.song.anew.util.DensityUtil;
import com.song.anew.util.StatusBarUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class Mainactivity extends SlidingFragmentActivity {
    private long firstTime = 0;
    public HomePageBean homePageBean;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainactivity);
        //StatusBarUtil.immersive(this, Color.argb(255, 255, 69, 69), 1);
        StatusBarCompat.setStatusBarColor(this, Color.argb(255, 255, 69, 69), true);
        initnewsData();
        initwidgetid();
        initslidingmenu();
        initfragment();
    }

    private void initnewsData() {
        homePageBean = new HomePageBean();
        OkHttpUtils.get()
                .url(Constants.HOME_PAGE_URL)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        homePageBean = new Gson().fromJson(response, HomePageBean.class);
                        for (int i = 0; i <homePageBean.getData().get(0).getChildren().size() ; i++) {
                            Log.i("********", "onResponse: " + homePageBean.getData().get(0).getChildren().get(i).getTitle());
//                            Log.i("********", "onResponse: " + homePageBean.getData().get(0).getChildren().get(2).getTitle());
//                            Log.i("********", "onResponse: " + homePageBean.getData().get(0).getChildren().size());
                        }


                    }
                });
    }
    private void initwidgetid() {
    }
    private void initfragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fl_main_content, new ContentFragment(), "fl_main_content");
        ft.replace(R.id.fl_main_leftcontent, new LiftmenuFragment(), "fl_main_leftcontent");
        ft.commit();
    }

    private void initslidingmenu() {
        WindowManager wm = (WindowManager) Mainactivity.this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        //设置主页面
        setContentView(R.layout.activity_mainactivity);
//        设置策划
        setBehindContentView(R.layout.activity_slidingmeun);
//        设置右侧菜单
        SlidingMenu slidingMenu = getSlidingMenu();

//        设置显示模式
        slidingMenu.setMode(SlidingMenu.LEFT);
        //设置滑动模式
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        //设置主页占据的位置
        slidingMenu.setBehindOffset(150);


    }


    public LiftmenuFragment getLiftmenuFragment() {
        return (LiftmenuFragment) getSupportFragmentManager().findFragmentByTag("fl_main_leftcontent");
    }

    public ContentFragment getContentFragment() {

        return (ContentFragment) getSupportFragmentManager().findFragmentByTag("fl_main_content");
    }
  public void setNohua(int i){
        if(i==0) {
            getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
        else {
            getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        }
  }

    public FragmentManager getfragmentmanager() {
        return getSupportFragmentManager();
    }

 /*   @Override
    public void onBackPressed() {
        long time = SystemClock.currentThreadTimeMillis();
        if((time+2000)!=0){

        }
    }*/

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        getSlidingMenu().toggle(false);
        if (keyCode==KeyEvent.KEYCODE_BACK){
            long secondTime = System.currentTimeMillis();
            if (secondTime-firstTime>2000){
                Toast.makeText(this,"再按一次退出",Toast.LENGTH_SHORT).show();
                firstTime = System.currentTimeMillis();
                return true;
            }else {
                finish();
            }
        }

        return super.onKeyUp(keyCode, event);
    }

    public HomePageBean getHomePageBean(){

     return homePageBean;
    }
}
