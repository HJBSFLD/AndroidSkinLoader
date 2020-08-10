package com.mgtv.lib.skin.loader.model;


import java.util.List;

/**
 * author  Li Peng on 2020/8/6.
 * Phone 18974450920
 * Mail lipeng@mgtv.com
 */
public abstract class SkinHold<T> {
    protected String tag;

    protected List<SkinAttr<T>> attrs;

    public SkinHold(List<SkinAttr<T>> attrs, String tag) {
        this.attrs = attrs;
        this.tag = tag;
    }

    public List<SkinAttr<T>> getAttrs() {
        return attrs;
    }

    public void setAttrs(List<SkinAttr<T>> attrs) {
        this.attrs = attrs;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
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
