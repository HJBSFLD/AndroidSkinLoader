package com.mgtv.lib.skin.loader.model.inner;

import android.os.Build;
import android.view.View;

import com.mgtv.lib.skin.loader.MSkinLoader;
import com.mgtv.lib.skin.loader.model.SkinAttr;
import com.mgtv.lib.skin.loader.resource.ResourceManager;

/**
 * author  Li Peng on 2020/8/7.
 * Phone 18974450920
 * Mail lipeng@mgtv.com
 */
public class BackgroundAttr extends SkinAttr<View> {
    @Override
    public void apply(View view) {
        switch (attrValueTypeName) {
            case ResourceManager.COLOR_FOLDER:
                view.setBackgroundColor(MSkinLoader.getInstance().getResourceManager().getColor(this));
                break;
            case ResourceManager.DRAWABLE_FOLDER:
            case ResourceManager.MIPMAP_FOLDER:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    view.setBackground(MSkinLoader.getInstance().getResourceManager().getDrawable(this));
                } else {
                    view.setBackgroundDrawable(MSkinLoader.getInstance().getResourceManager().getDrawable(this));
                }
                break;
        }
    }
}
