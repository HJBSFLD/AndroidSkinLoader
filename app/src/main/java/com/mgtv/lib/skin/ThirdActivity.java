package com.mgtv.lib.skin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.mgtv.lib.skin.loader.base.BaseSkinFragmentActivity;

/**
 * author  Li Peng on 2020/8/7.
 * Phone 18974450920
 * Mail lipeng@mgtv.com
 */
public class ThirdActivity extends BaseSkinFragmentActivity {
    private FrameLayout third_wrap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        third_wrap = findViewById(R.id.third_wrap);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(R.id.third_wrap, new TestFragment());
        ft.commit();

    }

    @Override
    public void skinChange() {

    }
}
