package com.mgtv.lib.skin.loader.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.mgtv.lib.skin.loader.constant.Constants;

/**
 * author  Li Peng on 2020/8/6.
 * Phone 18974450920
 * Mail lipeng@mgtv.com
 */
public class SkinSPHelper {
    public static final String LOCAL_SKIN_TAG = "local_skin_tag";
    private static final String STRING_EMPTY = "";
    private SharedPreferences sharedPreferences;

    public SkinSPHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(Constants.SP_SKIN_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 获得插件路径
     */
    public String getPluginPath() {
        if (sharedPreferences != null) {
            return getString(Constants.SP_SKIN_PLUGIN_PATH, LOCAL_SKIN_TAG);
        }
        return LOCAL_SKIN_TAG;
    }

    /**
     * 获得资源后缀
     */
    public String getAttrSuffix() {
        if (sharedPreferences != null) {
            return getString(Constants.SP_SKIN_ATTR_SUFFIX, STRING_EMPTY);
        }
        return STRING_EMPTY;
    }

    /**
     * 设置资源后缀
     */
    public void setAttrSuffix(String suffix) {
        if (sharedPreferences == null) {
            return;
        }
        putString(Constants.SP_SKIN_ATTR_SUFFIX, suffix);
    }

    /**
     * 获取插件包名
     */
    public String getPluginPackage() {
        if (sharedPreferences != null) {
            return getString(Constants.SP_SKIN_PLUGIN_PACKAGE, STRING_EMPTY);
        }
        return STRING_EMPTY;
    }

    public void setPluginInfo(String path, String pluginPackage, String suffix) {
        if (sharedPreferences == null) {
            return;
        }
        SharedPreferences.Editor spe = sharedPreferences.edit();
        putString(Constants.SP_SKIN_PLUGIN_PATH, path);
        putString(Constants.SP_SKIN_PLUGIN_PACKAGE, pluginPackage);
        putString(Constants.SP_SKIN_ATTR_SUFFIX, suffix);
        spe.apply();
    }

    public String getString(String key, String def) {
        return sharedPreferences == null ? STRING_EMPTY : sharedPreferences.getString(key, def);
    }

    public void putString(String key, String value) {
        if (sharedPreferences == null) {
            return;
        }
        sharedPreferences.edit().putString(key, value).apply();
    }

    public boolean clear() {
        if (sharedPreferences == null) return false;
        return sharedPreferences.edit().clear().commit();
    }
}
