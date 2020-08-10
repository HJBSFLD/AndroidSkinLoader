package com.mgtv.lib.skin.loader.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.mgtv.lib.skin.loader.MSkinLoader;
import com.mgtv.lib.skin.loader.callback.ISkinChangeListener;
import com.mgtv.lib.skin.loader.callback.ISkinDynamicAddView;
import com.mgtv.lib.skin.loader.model.DynamicAttr;
import com.mgtv.lib.skin.loader.resource.MSkinInflaterFactory;

import java.util.List;

/**
 * author  Li Peng on 2020/8/7.
 * Phone 18974450920
 * Mail lipeng@mgtv.com
 */
public class BaseSkinActivity extends Activity implements ISkinChangeListener, ISkinDynamicAddView {
    protected MSkinInflaterFactory factory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        factory = new MSkinInflaterFactory();
        getLayoutInflater().setFactory(factory);
        MSkinLoader.getInstance().bind(this);
    }


    @Override
    public void onSkinChange() {
        if (factory != null) {
            factory.applySkin();
        }
        skinChange();
    }

    public void skinChange() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (factory != null) {
            factory.clean();
        }
        MSkinLoader.getInstance().unBind(this);
    }

    @Override
    public void onDynamicAddView(View view, List<DynamicAttr> attrs) {
        if (factory != null) {
            factory.dynamicAddSkinView(this, view, attrs);
        }
    }

    @Override
    public void onDynamicAddView(View view, String attrType, int attrId) {
        if (factory != null) {
            factory.dynamicAddSkinView(this, view, attrType, attrId);
        }
    }
}
