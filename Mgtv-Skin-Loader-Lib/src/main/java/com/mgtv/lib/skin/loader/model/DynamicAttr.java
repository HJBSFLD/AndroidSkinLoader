package com.mgtv.lib.skin.loader.model;

import com.mgtv.lib.skin.loader.anno.SkinAttrType;

/**
 * author  Li Peng on 2020/8/7.
 * Phone 18974450920
 * Mail lipeng@mgtv.com
 */
public class DynamicAttr {
    /**
     * 资源类型  {@link com.mgtv.lib.skin.loader.anno.SkinAttrType}
     */
    private String attrType;

    /**
     * 资源Id
     */
    private int attrId;

    public DynamicAttr(String attrType, int attrId) {
        this.attrType = attrType;
        this.attrId = attrId;
    }

    public int getAttrId() {
        return attrId;
    }

    public void setAttrId(int attrId) {
        this.attrId = attrId;
    }

    public String getAttrType() {
        return attrType;
    }

    public void setAttrType(@SkinAttrType String attrType) {
        this.attrType = attrType;
    }
}
