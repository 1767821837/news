package com.song.anew.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.song.anew.R;
import com.song.anew.fragment.ContentFragment;
import com.song.anew.fragment.LiftmenuFragment;
import com.song.anew.util.DensityUtil;

public class Mainactivity extends SlidingFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainactivity);
        initwidgetid();
        initslidingmenu();
        initfragment();

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
        slidingMenu.setBehindOffset(DensityUtil.dp2px(getApplicationContext(), 200));


    }


    public LiftmenuFragment getLiftmenuFragment() {
        return (LiftmenuFragment) getSupportFragmentManager().findFragmentByTag("fl_main_leftcontent");
    }

    public ContentFragment getContentFragment() {

        return (ContentFragment) getSupportFragmentManager().findFragmentByTag("fl_main_content");
    }

    public FragmentManager getfragmentmanager() {
        return getSupportFragmentManager();
    }
}
