package com.mgtv.lib.skin.loader.resource;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.mgtv.lib.skin.loader.MSkinLoader;
import com.mgtv.lib.skin.loader.anno.SkinMode;
import com.mgtv.lib.skin.loader.model.SkinAttr;

/**
 * author  Li Peng on 2020/8/6.
 * Phone 18974450920
 * Mail lipeng@mgtv.com
 */
public class ResourceManager {
    public static final String MIPMAP_FOLDER = "mipmap";
    public static final String DRAWABLE_FOLDER = "drawable";
    public static final String COLOR_FOLDER = "color";
    private Context mContext;
    //资源对象
    private Resources mResources;
    //插件包名
    private String mPluginPackage;
    //皮肤后缀名
    private String mSkinSuffix;

    public ResourceManager(@NonNull Context mContext, @NonNull Resources resources, String pluginPackage, String skinSuffix) {
        this.mContext = mContext;
        this.mResources = resources;
        this.mPluginPackage = pluginPackage;
        this.mSkinSuffix = skinSuffix;
    }

    /**
     * 根据资源名搜索图片资源
     */
    public Drawable getDrawable(SkinAttr attr) {
        if (attr == null) {
            return null;
        }
        if (MSkinLoader.getInstance().getSkinMode() == SkinMode.MODE_DEFAULT) {
            try {
                return mContext.getResources().getDrawable(attr.getAttrId());
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }
        } else {
            String name = appendSuffix(attr.getAttrName());
            Drawable drawable = null;
            drawable = findDrawableInResource(name, DRAWABLE_FOLDER, mPluginPackage);
            if (drawable == null) {
                //如果在drawable文件夹中没找到 就在mipmap中找一次
                drawable = findDrawableInResource(name, MIPMAP_FOLDER, mPluginPackage);
            }
            if (drawable == null) {
                //如果在mipmap文件夹中没找到 就在颜色中找一次
                drawable = findDrawableInResource(name, COLOR_FOLDER, mPluginPackage);
            }
            if (drawable == null) {
                try {
                    return mContext.getResources().getDrawable(attr.getAttrId());
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            }
            return drawable;
        }
        return null;
    }

    /**
     * 根据资源ID搜索图片资源
     */
    public Drawable getDrawable(int resId) {
        if (MSkinLoader.getInstance().getSkinMode() == SkinMode.MODE_DEFAULT) {
            return mContext.getResources().getDrawable(resId);
        } else {
            String resName = mContext.getResources().getResourceEntryName(resId);
            if (TextUtils.isEmpty(resName)) {
                return null;
            }
            String sName = appendSuffix(resName);
            Drawable drawable = null;
            drawable = findDrawableInResource(sName, DRAWABLE_FOLDER, mPluginPackage);
            if (drawable == null) {
                //如果在drawable文件夹中没找到 就在mipmap中找一次
                drawable = findDrawableInResource(sName, MIPMAP_FOLDER, mPluginPackage);
            }
            if (drawable == null) {
                //如果在mipmap文件夹中没找到 就在颜色中找一次
                drawable = findDrawableInResource(sName, COLOR_FOLDER, mPluginPackage);
            }
            if (drawable == null) {
                //兜底 都没找到就使用默认
                try {
                    return mContext.getResources().getDrawable(resId);
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
            }
            return drawable;
        }
    }

    public int getColor(SkinAttr attr) {
        if (attr == null) {
            return -1;
        }
        if (MSkinLoader.getInstance().getSkinMode() == SkinMode.MODE_DEFAULT) {
            try {
                return mContext.getResources().getColor(attr.getAttrId());
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }
        } else {
            String name = appendSuffix(attr.getAttrName());
            int colorId = mResources.getIdentifier(name, COLOR_FOLDER, mPluginPackage);
            try {
                if (colorId == 0) {
                    return mContext.getResources().getColor(attr.getAttrId());
                } else {
                    return mResources.getColor(colorId);
                }
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
                //如果获取失败 则使用原本颜色
            }
        }
        return 0;
    }

    public int getColor(int resId) {
        if (MSkinLoader.getInstance().getSkinMode() == SkinMode.MODE_DEFAULT) {
            return mContext.getResources().getColor(resId);
        } else {
            String resName = mContext.getResources().getResourceEntryName(resId);
            String name = appendSuffix(resName);
            int colorId = mResources.getIdentifier(name, COLOR_FOLDER, mPluginPackage);
            try {
                if (colorId == 0) {
                    //如果获取失败 则使用原本颜色
                    int realId = mContext.getResources().getIdentifier(resName, COLOR_FOLDER, mContext.getPackageName());
                    if (realId != 0) {
                        return mContext.getResources().getColor(realId);
                    }
                } else {
                    return mResources.getColor(colorId);
                }
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * 根据资源名搜索颜色集合
     */
    public ColorStateList getColorStateList(SkinAttr attr) {
        if (attr == null) {
            return null;
        }
        if (MSkinLoader.getInstance().getSkinMode() == SkinMode.MODE_DEFAULT) {
            try {
                return mContext.getResources().getColorStateList(attr.getAttrId());
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }
        } else {
            String name = appendSuffix(attr.getAttrName());
            int colorId = mResources.getIdentifier(name, COLOR_FOLDER, mPluginPackage);
            try {
                if (colorId == 0) {
                    return mContext.getResources().getColorStateList(attr.getAttrId());
                } else {
                    return mResources.getColorStateList(colorId);
                }
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }
        }
        //以上都没有找到 从颜色中再找一次
        int color = getColor(attr);
        if (color > 0) {
            int[][] states = new int[1][1];
            return new ColorStateList(states, new int[]{color});
        }
        return null;
    }

    /**
     * 根据资源名搜索颜色集合
     */
    public ColorStateList getColorStateList(int resId) {
        if (MSkinLoader.getInstance().getSkinMode() == SkinMode.MODE_DEFAULT) {
            return mContext.getResources().getColorStateList(resId);
        } else {
            String resName = mContext.getResources().getResourceEntryName(resId);
            String name = appendSuffix(resName);
            int colorId = mResources.getIdentifier(name, COLOR_FOLDER, mPluginPackage);
            try {
                if (colorId == 0) {
                    int realId = mContext.getResources().getIdentifier(resName, COLOR_FOLDER, mContext.getPackageName());
                    if (realId != 0) {
                        return mContext.getResources().getColorStateList(realId);
                    }
                } else {
                    return mResources.getColorStateList(colorId);
                }
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }
        }
        //以上都没有找到 从颜色中再找一次
        int color = getColor(resId);
        if (color > 0) {
            int[][] states = new int[1][1];
            return new ColorStateList(states, new int[]{color});
        }
        return null;
    }

    private Drawable findDrawableInResource(String name, String folder, String packageName) {
        try {
            return mResources.getDrawable(mResources.getIdentifier(name, folder, packageName));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将资源名添加后缀，用于app内部的皮肤修改
     *
     * @param name 资源名
     */
    private String appendSuffix(String name) {
        return TextUtils.isEmpty(mSkinSuffix) ? name : name + "_" + mSkinSuffix;
    }
}