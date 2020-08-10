package com.mgtv.lib.skin.loader.utils;

import com.mgtv.lib.skin.loader.anno.SkinAttrType;
import com.mgtv.lib.skin.loader.model.SkinAttr;
import com.mgtv.lib.skin.loader.model.inner.BackgroundAttr;
import com.mgtv.lib.skin.loader.model.inner.SrcAttr;
import com.mgtv.lib.skin.loader.model.inner.TextColorAttr;

/**
 * author  Li Peng on 2020/8/7.
 * Phone 18974450920
 * Mail lipeng@mgtv.com
 */
public class AttrHelper {
    public static SkinAttr createSkinAttr(String attrType, int attrId, String attrName, String typeName) {
        SkinAttr mSkinAttr = null;
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
}
