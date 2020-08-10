package com.mgtv.lib.skin.loader.callback;

import android.view.View;

import com.mgtv.lib.skin.loader.anno.SkinAttrType;
import com.mgtv.lib.skin.loader.model.DynamicAttr;

import java.util.List;

/**
 * author  Li Peng on 2020/8/7.
 * Phone 18974450920
 * Mail lipeng@mgtv.com
 */
public interface ISkinDynamicAddView {
    void onDynamicAddView(View view, List<DynamicAttr> attrs);

    void onDynamicAddView(View view, @SkinAttrType String attrType, int attrId);
}
