package com.mgtv.lib.skin.loader.model.inner;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.mgtv.lib.skin.loader.MSkinLoader;
import com.mgtv.lib.skin.loader.model.SkinAttr;

import static com.mgtv.lib.skin.loader.constant.Constants.TAG;

/**
 * author  Li Peng on 2020/8/7.
 * Phone 18974450920
 * Mail lipeng@mgtv.com
 */
public class SrcAttr extends SkinAttr<View> {
    @Override
    public void apply(View view) {
        if(MSkinLoader.getInstance().getResourceManager() == null){
            Log.e(TAG, "M SKIN ResourceManager is null");
            return;
        }
        if (view instanceof ImageView) {
            ImageView tv = (ImageView) view;
            tv.setImageDrawable(MSkinLoader.getInstance().getResourceManager().getDrawable(this));
        }
    }
}
