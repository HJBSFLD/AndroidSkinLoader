package com.mgtv.lib.skin;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mgtv.lib.skin.loader.MSkinLoader;
import com.mgtv.lib.skin.loader.base.BaseSkinActivity;
import com.mgtv.lib.skin.loader.callback.ISkinLoadListener;


/**
 * author  Li Peng on 2020/8/5.
 * Phone 18974450920
 * Mail lipeng@mgtv.com
 */
public class SecondActivity extends BaseSkinActivity {
    private Button b1, b2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long start = System.currentTimeMillis();
        setContentView(R.layout.activity_second);
        Log.d("LSkin", "second onCreate use time = " + (System.currentTimeMillis() - start));
        b1 = findViewById(R.id.second_12);
        b2 = findViewById(R.id.second_13);
        final String pluginPath = getExternalFilesDir(null) + "/skinTest.apk";
        final String pluginPackage = "com.lp.skin_plugin";
        final String suffix = "test";

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MSkinLoader.getInstance().changeSkin(pluginPath, pluginPackage, suffix, new ISkinLoadListener() {
                    @Override
                    public void onStart() {
                        Log.i("lp", "onStart");
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e("lp", "onError" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i("lp", "onComplete");
                    }
                });
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MSkinLoader.getInstance().removeSkinAndBackToDefault();
            }
        });
    }

    @Override
    public void skinChange() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
