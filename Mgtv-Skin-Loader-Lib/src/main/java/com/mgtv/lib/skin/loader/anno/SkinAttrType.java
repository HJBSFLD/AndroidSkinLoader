package com.mgtv.lib.skin.loader.anno;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * author  Li Peng on 2020/8/7.
 * Phone 18974450920
 * Mail lipeng@mgtv.com
 */
@StringDef({SkinAttrType.BACKGROUND, SkinAttrType.TEXT_COLOR, SkinAttrType.SRC})
@Retention(RetentionPolicy.SOURCE)
public @interface SkinAttrType {
    String BACKGROUND = "background";
    String TEXT_COLOR = "textColor";
    String SRC = "src";
}
