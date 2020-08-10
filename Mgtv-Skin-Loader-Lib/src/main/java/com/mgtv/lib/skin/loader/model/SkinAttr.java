package com.mgtv.lib.skin.loader.model;


import android.view.View;

/**
 * author  Li Peng on 2020/8/6.
 * Phone 18974450920
 * Mail lipeng@mgtv.com
 */
public abstract class SkinAttr<T> {
    /**
     * attr类型, background、textSize、textColor
     */
    protected String attrType;

    /**
     * attr Id
     */
    protected int attrId;

    /**
     * attr名称
     */
    protected String attrName;

    protected String attrValueTypeName;

    public String getAttrType() {
        return attrType;
    }

    public void setAttrType(String attrType) {
        this.attrType = attrType;
    }

    public int getAttrId() {
        return attrId;
    }

    public void setAttrId(int attrId) {
        this.attrId = attrId;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrValueTypeName() {
        return attrValueTypeName;
    }

    public void setAttrValueTypeName(String attrValueTypeName) {
        this.attrValueTypeName = attrValueTypeName;
    }

    public abstract void apply(T view);

    @Override
    public String toString() {
        return "SkinAttr{" +
                "attrType='" + attrType + '\'' +
                ", attrId=" + attrId +
                ", attrName='" + attrName + '\'' +
                '}';
    }
}
