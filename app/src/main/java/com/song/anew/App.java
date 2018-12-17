package com.song.anew;

import android.app.Application;

import org.xutils.x;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.setDebug(true);
        x.Ext.init(this);
    }
}
