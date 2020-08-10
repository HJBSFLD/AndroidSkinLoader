package com.mgtv.lib.skin.loader.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import com.mgtv.lib.skin.loader.callback.ISkinDynamicAddView;
import com.mgtv.lib.skin.loader.model.DynamicAttr;

import java.util.List;


/**
 * author  Li Peng on 2020/8/7.
 * Phone 18974450920
 * Mail lipeng@mgtv.com
 */
public class BaseFragment extends Fragment implements ISkinDynamicAddView {
    private ISkinDynamicAddView parent;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ISkinDynamicAddView) {
            try {
                parent = (ISkinDynamicAddView) context;
            } catch (ClassCastException e) {
                parent = null;
            }
        }
    }

    @NonNull
    @Override
    public LayoutInflater onGetLayoutInflater(@Nullable Bundle savedInstanceState) {
        if (getActivity() != null) {
            return getActivity().getLayoutInflater();
        }
        return super.onGetLayoutInflater(savedInstanceState);
    }

    @Override
    public void onDynamicAddView(View view, List<DynamicAttr> attrs) {
        parent.onDynamicAddView(view, attrs);
    }

    @Override
    public void onDynamicAddView(View view, String attrType, int attrId) {
        parent.onDynamicAddView(view, attrType, attrId);
    }
}
