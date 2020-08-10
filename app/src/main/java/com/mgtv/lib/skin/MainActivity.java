package com.mgtv.lib.skin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mgtv.lib.skin.loader.MSkinLoader;
import com.mgtv.lib.skin.loader.base.BaseSkinAppCompatActivity;
import com.mgtv.lib.skin.loader.callback.ISkinLoadListener;

public class MainActivity extends BaseSkinAppCompatActivity {
    private Button b1;
    private Button b2;
    private Button b3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String pluginPath = getExternalFilesDir(null) + "/skinTest.apk";
        final String pluginPackage = "com.lp.skin_plugin";
        final String suffix = "test";
        b1 = findViewById(R.id.test_4);
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
        final String suffix_inside = "in";
        b2 = findViewById(R.id.test_5);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MSkinLoader.getInstance().changeSkin(suffix_inside);
            }
        });
        b3 = findViewById(R.id.test_6);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void skinChange() {

    }
}