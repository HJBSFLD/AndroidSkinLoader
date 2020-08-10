package com.mgtv.lib.skin;

import android.app.Application;

import com.mgtv.lib.skin.loader.MSkinLoader;

/**
 * author  Li Peng on 2020/8/7.
 * Phone 18974450920
 * Mail lipeng@mgtv.com
 */
public class SkinApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MSkinLoader.getInstance().init(this);
    }
}
