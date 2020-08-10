package com.mgtv.lib.skin.loader.model;

import android.view.View;

import java.util.List;

/**
 * author  Li Peng on 2020/8/6.
 * Phone 18974450920
 * Mail lipeng@mgtv.com
 */
public class SkinView extends SkinHold<View> {
    private View view;

    public SkinView(View view, List<SkinAttr<View>> attrs, String tag) {
        super(attrs, tag);
        this.view = view;
    }

    public List<SkinAttr<View>> getAttrs() {
        return attrs;
    }

    public void setAttrs(List<SkinAttr<View>> attrs) {
        this.attrs = attrs;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void clean() {
        if (attrs == null || attrs.size() == 0) {
            return;
        }
        attrs.clear();
        attrs = null;
    }

    public void apply() {
        if (view == null || attrs == null || attrs.size() == 0) {
            return;
        }
        for (SkinAttr<View> skinAttr : attrs) {
            skinAttr.apply(view);
        }
    }

}
