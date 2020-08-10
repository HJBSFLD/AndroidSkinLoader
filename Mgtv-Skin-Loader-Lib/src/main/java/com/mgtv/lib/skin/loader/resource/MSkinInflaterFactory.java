package com.mgtv.lib.skin.loader.resource;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.mgtv.lib.skin.loader.MSkinLoader;
import com.mgtv.lib.skin.loader.anno.SkinAttrType;
import com.mgtv.lib.skin.loader.anno.SkinMode;
import com.mgtv.lib.skin.loader.constant.Constants;
import com.mgtv.lib.skin.loader.model.DynamicAttr;
import com.mgtv.lib.skin.loader.model.SkinAttr;
import com.mgtv.lib.skin.loader.model.SkinView;
import com.mgtv.lib.skin.loader.utils.AttrHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * author  Li Peng on 2020/8/7.
 * Phone 18974450920
 * Mail lipeng@mgtv.com
 */
public class MSkinInflaterFactory implements LayoutInflater.Factory {
    private static final String STRING_AT = "@";
    private List<SkinView> mSkinItems = new ArrayList<SkinView>();
    private DynamicSkinViewManager<SkinView, View> dynamicSkinViewManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        boolean isSkinEnable = attrs.getAttributeBooleanValue(Constants.NAMESPACE, Constants.ATTR_SKIN_ENABLE, false);
        if (!isSkinEnable) {
            //不是换肤的View 交由上层处理
            return null;
        }
        View v = createView(name, context, attrs);
        if (v == null) {
            return null;
        }
        parseSkinAttr(context, attrs, v);
        return v;
    }

    public void applySkin() {
        if (mSkinItems == null || mSkinItems.size() == 0) {
            return;
        }

        for (SkinView skinView : mSkinItems) {
            skinView.apply();
        }
        if (dynamicSkinViewManager != null) {
            dynamicSkinViewManager.applySkin();
        }
    }

    public void dynamicAddSkinView(Context context, View view, List<DynamicAttr> dAttrs) {
        dynamicAddSkinView(context, view, "", dAttrs);
    }

    public void dynamicAddSkinView(Context context, View view, String tag, List<DynamicAttr> dAttrs) {
        if (dynamicSkinViewManager == null) {
            dynamicSkinViewManager = new DynamicSkinViewManager<>();
        }
        SkinView v = AttrHelper.parseToSkinView(context, view, tag, dAttrs);
        if (v == null) {
            return;
        }
        dynamicSkinViewManager.dynamicAddSkinView(v);
    }

    public void dynamicAddSkinView(Context context, View view, @SkinAttrType String attrType, int attrId) {

    }

    public void dynamicAddSkinView(Context context, View view, String tag, @SkinAttrType String attrType, int attrId) {
        if (dynamicSkinViewManager == null) {
            dynamicSkinViewManager = new DynamicSkinViewManager<>();
        }
        SkinView v = AttrHelper.parseToSkinView(context, view, tag, attrType, attrId);
        if (v == null) {
            return;
        }
        dynamicSkinViewManager.dynamicAddSkinView(v);
    }


    public void clean() {
        if (mSkinItems != null && mSkinItems.size() != 0) {
            for (SkinView skinView : mSkinItems) {
                skinView.clean();
            }
        }
        if (dynamicSkinViewManager != null) {
            dynamicSkinViewManager.clean();
        }
        dynamicSkinViewManager = null;
    }

    private View createView(String name, Context context, AttributeSet attrs) {
        Log.d(Constants.TAG, "M SKIN create view start");
        View view = null;
        try {
            if (-1 == name.indexOf('.')) {
                //系统View
                if ("View".equals(name)) {
                    view = LayoutInflater.from(context).createView(name, "android.view.", attrs);
                }
                if (view == null) {
                    view = LayoutInflater.from(context).createView(name, "android.widget.", attrs);
                }
                if (view == null) {
                    view = LayoutInflater.from(context).createView(name, "android.webkit.", attrs);
                }
            } else {
                //自定义View
                view = LayoutInflater.from(context).createView(name, null, attrs);
            }
        } catch (Exception e) {
            Log.e(Constants.TAG, "M SKIN create view error while name=" + name + " reason is" + e.getMessage());
            view = null;
        }
        return view;
    }


    private void parseSkinAttr(Context context, AttributeSet attrs, View v) {
        List<SkinAttr<View>> skinAttrs = new ArrayList<>();
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            String attrName = attrs.getAttributeName(i);
            String attrValue = attrs.getAttributeValue(i);
            if (!AttrHelper.isSupportedAttr(attrName)) {
                continue;
            }
            if (attrValue.startsWith(STRING_AT)) {
                try {
                    int id = Integer.parseInt(attrValue.substring(1));
                    String entryName = context.getResources().getResourceEntryName(id);
                    String typeName = context.getResources().getResourceTypeName(id);
                    Log.d(Constants.TAG, "typeName = " + typeName + " entryName=" + entryName);
                    SkinAttr<View> mSkinAttr = AttrHelper.createSkinAttr(attrName, id, entryName, typeName);
                    if (mSkinAttr != null) {
                        skinAttrs.add(mSkinAttr);
                    }
                } catch (Exception e) {
                    Log.e(Constants.TAG, "M SKIN parse view attr error while attrName =" + attrName + " reason is" + e.getMessage());
                }
            }
        }
        if (skinAttrs.size() > 0) {
            SkinView skinView = new SkinView(v, skinAttrs, "");
            mSkinItems.add(skinView);
            if (MSkinLoader.getInstance().getSkinMode() != SkinMode.MODE_DEFAULT) {
                skinView.apply();
            }
        }

    }
}
