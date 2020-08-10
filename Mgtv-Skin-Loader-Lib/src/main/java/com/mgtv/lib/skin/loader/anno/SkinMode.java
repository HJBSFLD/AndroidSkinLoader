package com.mgtv.lib.skin.loader.anno;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * author  Li Peng on 2020/8/6.
 * Phone 18974450920
 * Mail lipeng@mgtv.com
 * 当前皮肤模式
 */
@IntDef({SkinMode.MODE_DEFAULT, SkinMode.MODE_LOCAL, SkinMode.MODE_PLUGIN})
@Retention(RetentionPolicy.SOURCE)
public @interface SkinMode {
    int MODE_DEFAULT = 0; //默认皮肤
    int MODE_LOCAL = 1;   //内置皮肤
    int MODE_PLUGIN = 2;  //插件皮肤
}
