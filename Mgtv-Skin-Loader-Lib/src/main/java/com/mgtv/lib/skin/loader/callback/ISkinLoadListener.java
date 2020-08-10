package com.mgtv.lib.skin.loader.callback;

/**
 * author  Li Peng on 2020/8/7.
 * Phone 18974450920
 * Mail lipeng@mgtv.com
 */
public interface ISkinLoadListener {
    void onStart();

    void onComplete();

    void onError(Exception e);
}
