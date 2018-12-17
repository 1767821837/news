package com.song.anew.fragmentpage;

import android.view.View;

import com.song.anew.BaseFragment;

public class chinesefragment extends BaseFragment {
    @Override
    public View initview() {

       getChildFragmentManager().beginTransaction().commit();
return null;

    }
}
