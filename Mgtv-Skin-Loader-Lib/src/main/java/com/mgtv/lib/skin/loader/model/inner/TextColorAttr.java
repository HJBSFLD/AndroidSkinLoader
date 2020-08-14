package com.mgtv.lib.skin.loader.model.inner;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mgtv.lib.skin.loader.MSkinLoader;
import com.mgtv.lib.skin.loader.model.SkinAttr;
import com.mgtv.lib.skin.loader.resource.ResourceManager;

import static com.mgtv.lib.skin.loader.constant.Constants.TAG;

/**
 * author  Li Peng on 2020/8/7.
 * Phone 18974450920
 * Mail lipeng@mgtv.com
 */
public class TextColorAttr extends SkinAttr<View> {
    @Override
    public void apply(View view) {
        if (MSkinLoader.getInstance().getResourceManager() == null) {
            Log.e(TAG, "M SKIN ResourceManager is null");
            return;
        }
        if (view instanceof TextView) {
            TextView tv = (TextView) view;
            if (ResourceManager.COLOR_FOLDER.equals(attrValueTypeName)) {
                tv.setTextColor(MSkinLoader.getInstance().getResourceManager().getColorStateList(this));
            }
        }
    }
}
