package com.song.anew.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.LinearLayout;

import com.song.anew.activity.Mainactivity;
import com.song.anew.fragment.LiftmenuFragment;
import com.song.anew.fragment.MyFragment;
import com.song.anew.util.Log;

import java.util.ArrayList;

public class ViewpagerAdapter extends FragmentPagerAdapter {
    private ArrayList<MyFragment> arrayList;
    private  Context context;
    public ViewpagerAdapter(FragmentManager fm, ArrayList<MyFragment> arrayList , Context context) {
        super(fm);
        this.arrayList=arrayList;
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {




        return arrayList.get(i);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return arrayList.get(position).getTitle();
    }
}
