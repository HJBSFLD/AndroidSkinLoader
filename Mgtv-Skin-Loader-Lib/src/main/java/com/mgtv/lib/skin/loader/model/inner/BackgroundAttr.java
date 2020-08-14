package com.mgtv.lib.skin.loader.model.inner;

import android.os.Build;
import android.util.Log;
import android.view.View;

import com.mgtv.lib.skin.loader.MSkinLoader;
import com.mgtv.lib.skin.loader.model.SkinAttr;
import com.mgtv.lib.skin.loader.resource.ResourceManager;

import static com.mgtv.lib.skin.loader.constant.Constants.TAG;

/**
 * author  Li Peng on 2020/8/7.
 * Phone 18974450920
 * Mail lipeng@mgtv.com
 */
public class BackgroundAttr extends SkinAttr<View> {
    @Override
    public void apply(View view) {
        if(MSkinLoader.getInstance().getResourceManager() == null){
            Log.e(TAG, "M SKIN ResourceManager is null");
            return;
        }
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
