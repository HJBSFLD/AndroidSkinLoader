package com.mgtv.lib.skin.loader.utils;

import android.content.Context;
import android.view.View;

import com.mgtv.lib.skin.loader.anno.SkinAttrType;
import com.mgtv.lib.skin.loader.model.DynamicAttr;
import com.mgtv.lib.skin.loader.model.SkinAttr;
import com.mgtv.lib.skin.loader.model.SkinView;
import com.mgtv.lib.skin.loader.model.inner.BackgroundAttr;
import com.mgtv.lib.skin.loader.model.inner.SrcAttr;
import com.mgtv.lib.skin.loader.model.inner.TextColorAttr;

import java.util.ArrayList;
import java.util.List;

/**
 * author  Li Peng on 2020/8/7.
 * Phone 18974450920
 * Mail lipeng@mgtv.com
 */
public class AttrHelper {
    public static SkinAttr<View> createSkinAttr(String attrType, int attrId, String attrName, String typeName) {
        SkinAttr<View> mSkinAttr = null;
        switch (attrType) {
            case SkinAttrType
                    .BACKGROUND:
                mSkinAttr = new BackgroundAttr();
                break;
            case SkinAttrType
                    .TEXT_COLOR:
                mSkinAttr = new TextColorAttr();
                break;
            case SkinAttrType
                    .SRC:
                mSkinAttr = new SrcAttr();
                break;
            default:
                return null;
        }
        mSkinAttr.setAttrType(attrType);
        mSkinAttr.setAttrId(attrId);
        mSkinAttr.setAttrName(attrName);
        mSkinAttr.setAttrValueTypeName(typeName);
        return mSkinAttr;
    }

    /**
     * 检查当前支持的换肤属性
     */
    public static boolean isSupportedAttr(String attrName) {
        switch (attrName) {
            case SkinAttrType.BACKGROUND:
            case SkinAttrType.TEXT_COLOR:
            case SkinAttrType.SRC:
                return true;
            default:
                return false;
        }
    }

    public static SkinView parseToSkinView(Context context, View view, @SkinAttrType String attrType, int attrId) {
        return parseToSkinView(context, view, "", attrType, attrId);
    }

    public static SkinView parseToSkinView(Context context, View view, String tag, @SkinAttrType String attrType, int attrId) {
        List<SkinAttr<View>> skinAttrs = new ArrayList<>();
        String entryName = context.getResources().getResourceEntryName(attrId);
        String typeName = context.getResources().getResourceTypeName(attrId);
        if (TextUtils.isEmpty(entryName) || TextUtils.isEmpty(typeName)) {
            return null;
        }
        SkinAttr<View> skinAttr = createSkinAttr(attrType, attrId, entryName, typeName);
        if (skinAttr != null) {
            skinAttrs.add(skinAttr);
            return new SkinView(view, skinAttrs, tag);
        }
        return null;
    }

    public static SkinView parseToSkinView(Context context, View view, List<DynamicAttr> dAttrs) {
        return parseToSkinView(context, view, "", dAttrs);
    }

    public static SkinView parseToSkinView(Context context, View view, String tag, List<DynamicAttr> dAttrs) {
        List<SkinAttr<View>> skinAttrs = new ArrayList<>();
        for (DynamicAttr dAttr : dAttrs) {
            int id = dAttr.getAttrId();
            String entryName = context.getResources().getResourceEntryName(id);
            String typeName = context.getResources().getResourceTypeName(id);
            if (TextUtils.isEmpty(entryName) || TextUtils.isEmpty(typeName)) {
                return null;
            }
            SkinAttr<View> skinAttr = createSkinAttr(dAttr.getAttrType(), id, entryName, typeName);
            if (skinAttr != null) {
                skinAttrs.add(skinAttr);
            }
        }
        if (skinAttrs.size() > 0) {
            return new SkinView(view, skinAttrs, tag);
        }
        return null;
    }
}
