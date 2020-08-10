package com.mgtv.lib.skin.loader.model.inner;

import android.view.View;
import android.widget.ImageView;

import com.mgtv.lib.skin.loader.MSkinLoader;
import com.mgtv.lib.skin.loader.model.SkinAttr;

/**
 * author  Li Peng on 2020/8/7.
 * Phone 18974450920
 * Mail lipeng@mgtv.com
 */
public class SrcAttr extends SkinAttr {
    @Override
    public void apply(View view) {
        if (view instanceof ImageView) {
            ImageView tv = (ImageView) view;
            tv.setImageDrawable(MSkinLoader.getInstance().getResourceManager().getDrawable(this));
        }
    }
}
