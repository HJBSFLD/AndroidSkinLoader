package com.mgtv.lib.skin.loader.resource;

import android.content.Context;
import android.view.View;

import com.mgtv.lib.skin.loader.anno.SkinAttrType;
import com.mgtv.lib.skin.loader.model.DynamicAttr;
import com.mgtv.lib.skin.loader.model.SkinAttr;
import com.mgtv.lib.skin.loader.model.SkinView;
import com.mgtv.lib.skin.loader.utils.AttrHelper;
import com.mgtv.lib.skin.loader.utils.TextUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * author  Li Peng on 2020/8/7.
 * Phone 18974450920
 * Mail lipeng@mgtv.com
 */
public class DynamicSkinViewManager implements IDynamicSkinManager {
    private List<SkinView> skinViews;

    @Override
    public void applySkin() {
        if (skinViews == null || skinViews.size() == 0) {
            return;
        }
        for (SkinView skinView : skinViews) {
            skinView.apply();
        }
    }

    @Override
    public void clean() {
        if (skinViews == null || skinViews.size() == 0) {
            return;
        }
        for (SkinView skinView : skinViews) {
            skinView.clean();
        }
        skinViews.clear();
        skinViews = null;
    }

    public void removeDynamicSkinView(View view) {
        if (skinViews == null || skinViews.size() == 0) {
            return;
        }
        Iterator<SkinView> iterator = skinViews.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getView() == view) {
                iterator.remove();
            }
        }
    }

    public void dynamicAddSkinView(Context context, View view, List<DynamicAttr> dAttrs) {
        List<SkinAttr> skinAttrs = new ArrayList<>();
        for (DynamicAttr dAttr : dAttrs) {
            int id = dAttr.getAttrId();
            String entryName = context.getResources().getResourceEntryName(id);
            String typeName = context.getResources().getResourceTypeName(id);
            if (TextUtils.isEmpty(entryName) || TextUtils.isEmpty(typeName)) {
                return;
            }
            SkinAttr skinAttr = AttrHelper.createSkinAttr(dAttr.getAttrType(), id, entryName, typeName);
            if (skinAttr != null) {
                skinAttrs.add(skinAttr);
            }
        }
        if (skinAttrs.size() > 0) {
            SkinView skinView = new SkinView(view, skinAttrs);
            addSkinView(skinView);
        }

    }

    public void dynamicAddSkinView(Context context, View view, @SkinAttrType String attrType, int attrId) {
        List<SkinAttr> skinAttrs = new ArrayList<>();
        String entryName = context.getResources().getResourceEntryName(attrId);
        String typeName = context.getResources().getResourceTypeName(attrId);
        if (TextUtils.isEmpty(entryName) || TextUtils.isEmpty(typeName)) {
            return;
        }
        SkinAttr skinAttr = AttrHelper.createSkinAttr(attrType, attrId, entryName, typeName);
        if (skinAttr != null) {
            skinAttrs.add(skinAttr);
            SkinView skinView = new SkinView(view, skinAttrs);
            addSkinView(skinView);
        }

    }

    private void addSkinView(SkinView skinView) {
        if (skinViews == null) {
            skinViews = new ArrayList<>();
        }
        skinViews.add(skinView);
    }

}
