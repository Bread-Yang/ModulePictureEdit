package com.mdground.moduleedittest.application;

import android.app.Application;

/**
 * Created by yoghourt on 7/25/16.
 */

public class MDGroundApplication extends Application {

    public static MDGroundApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
}
