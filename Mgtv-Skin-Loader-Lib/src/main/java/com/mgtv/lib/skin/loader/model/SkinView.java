package com.mgtv.lib.skin.loader.model;

import android.view.View;

import java.util.List;

/**
 * author  Li Peng on 2020/8/6.
 * Phone 18974450920
 * Mail lipeng@mgtv.com
 */
public class SkinView extends SkinHold {
    private View view;

    public SkinView(View view, List<SkinAttr> attrs, String tag) {
        super(attrs, tag);
        this.view = view;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void apply() {
        if (view == null || attrs == null || attrs.size() == 0) {
            return;
        }
        for (SkinAttr skinAttr : attrs) {
            skinAttr.apply(view);
        }
    }

    public void clean() {
        if (attrs == null || attrs.size() == 0) {
            return;
        }
        attrs.clear();
        attrs = null;
    }
}
