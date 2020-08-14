package com.mgtv.lib.skin.loader.resource;


import com.mgtv.lib.skin.loader.model.SkinHold;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * author  Li Peng on 2020/8/7.
 * Phone 18974450920
 * Mail lipeng@mgtv.com
 */
public class DynamicSkinViewManager<T extends SkinHold<V>, V> {
    private List<T> skinViews;

    public void dynamicAddSkinView(T skinHold) {
        if (skinViews == null) {
            skinViews = new ArrayList<>();
        }
        skinHold.apply();
        skinViews.add(skinHold);
    }

    public List<T> getSkinViews() {
        if (skinViews == null) {
            skinViews = new ArrayList<>();
        }
        return skinViews;
    }

    public void applySkin() {
        if (skinViews == null || skinViews.size() == 0) {
            return;
        }
        for (T skinHold : skinViews) {
            skinHold.apply();
        }
    }

    public void clean() {
        if (skinViews == null || skinViews.size() == 0) {
            return;
        }
        for (T skinHold : skinViews) {
            skinHold.clean();
        }
        skinViews.clear();
        skinViews = null;
    }

    public void removeByTag(String tag) {
        if (skinViews == null || skinViews.size() == 0) {
            return;
        }
        Iterator<T> iterator = skinViews.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getTag() == tag) {
                iterator.remove();
            }
        }
    }


}
