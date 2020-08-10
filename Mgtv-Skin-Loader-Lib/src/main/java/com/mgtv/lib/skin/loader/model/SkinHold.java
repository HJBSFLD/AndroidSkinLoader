package com.mgtv.lib.skin.loader.model;


import java.util.List;

/**
 * author  Li Peng on 2020/8/6.
 * Phone 18974450920
 * Mail lipeng@mgtv.com
 */
public abstract class SkinHold {
    protected String tag;

    protected List<SkinAttr> attrs;

    public SkinHold(List<SkinAttr> attrs, String tag) {
        this.attrs = attrs;
        this.tag = tag;
    }

    public List<SkinAttr> getAttrs() {
        return attrs;
    }

    public void setAttrs(List<SkinAttr> attrs) {
        this.attrs = attrs;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public abstract void apply();

    public void clean() {
        if (attrs == null || attrs.size() == 0) {
            return;
        }
        attrs.clear();
        attrs = null;
    }
}
