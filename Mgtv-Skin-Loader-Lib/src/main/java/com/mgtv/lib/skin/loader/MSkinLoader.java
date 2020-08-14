package com.mgtv.lib.skin.loader;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.mgtv.lib.skin.loader.anno.SkinMode;
import com.mgtv.lib.skin.loader.callback.ISkinChangeListener;
import com.mgtv.lib.skin.loader.callback.ISkinLoadListener;
import com.mgtv.lib.skin.loader.constant.Constants;
import com.mgtv.lib.skin.loader.resource.ResourceManager;
import com.mgtv.lib.skin.loader.utils.SkinSPHelper;
import com.mgtv.lib.skin.loader.thread.DefaultPoolExecutor;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static com.mgtv.lib.skin.loader.constant.Constants.TAG;

/**
 * author  Li Peng on 2020/8/6.
 * Phone 18974450920
 * Mail lipeng@mgtv.com
 * 换肤框架主入口
 */
public class MSkinLoader {
    private Application mContext;
    private boolean isInit = false;
    private ResourceManager mResourceManager;//换肤的资源管理器
    private SkinSPHelper mSp;
    private int mode;
    private String mSuffix;
    private String mPluginPath;
    private String mPluginPackage;
    private List<ISkinChangeListener> skinObservers;
    private Handler mainThread = new Handler(Looper.getMainLooper());

    public MSkinLoader() {
    }

    private static class SingletonInstance {
        private static final MSkinLoader INSTANCE = new MSkinLoader();
    }

    public static MSkinLoader getInstance() {
        return SingletonInstance.INSTANCE;
    }

    public void init(Application context) {
        this.mContext = context;
        if (isInit) {
            return;
        }
        Log.d(TAG, "M SKIN init start");
        mSp = new SkinSPHelper(context);
        preparePlugin();
        isInit = true;
    }

    public void clear() {
        mResourceManager = null;
        mSp = null;
        mSuffix = null;
        skinObservers.clear();
        skinObservers = null;
        isInit = false;
    }

    public void bind(ISkinChangeListener listener) {
        if (skinObservers == null) {
            skinObservers = new ArrayList<>();
        }
        if (!skinObservers.contains(listener)) {
            skinObservers.add(listener);
        }
    }

    public void unBind(ISkinChangeListener listener) {
        if (skinObservers != null) {
            skinObservers.remove(listener);
        }
    }

    /**
     * 应用内换肤，传入资源区别的后缀
     */
    public void changeSkin(String suffix) {
        clearPluginInfo();
        mSuffix = suffix;
        mSp.setAttrSuffix(suffix);
        changeSkinMode(SkinMode.MODE_LOCAL);
        notifyChangedListeners();
    }

    /**
     * 更换插件皮肤，默认为""
     *
     * @param skinPluginPath
     * @param skinPluginPkg
     * @param callback
     */
    public void changeSkin(final String skinPluginPath, final String skinPluginPkg, ISkinLoadListener callback) {
        changeSkin(skinPluginPath, skinPluginPkg, "", callback);
    }

    /**
     * 根据suffix选择插件内某套皮肤
     *
     * @param skinPluginPath
     * @param skinPluginPkg
     * @param suffix
     * @param callback
     */
    public void changeSkin(final String skinPluginPath, final String skinPluginPkg, final String suffix, final ISkinLoadListener callback) {
        if (callback != null) {
            callback.onStart();
        }
        if (!isPluginParamsValid(skinPluginPath, skinPluginPkg)) {
            Log.e(TAG, "invalid plugin param");
            if (callback != null) {
                callback.onError(new RuntimeException("invalid plugin param"));
            }
            return;
        }
        //TODO 切换至子线程
        DefaultPoolExecutor.getInstance().execute(new Runnable() {

            @Override
            public void run() {
                try {
                    loadPlugin(skinPluginPath, skinPluginPkg, suffix);
                    updatePluginInfo(skinPluginPath, skinPluginPkg, suffix);
                    //切换至主线程更新UI
                    mainThread.post(new Runnable() {
                        @Override
                        public void run() {
                            notifyChangedListeners();
                        }
                    });
                    if (callback != null) {
                        callback.onComplete();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (callback != null) {
                        callback.onError(e);
                    }
                }
            }
        });

    }

    /**
     * 清除所有插件
     */
    public void removeSkinAndBackToDefault() {
        clearPluginInfo();
        notifyChangedListeners();//通知所有的view，修改皮肤
    }

    private void notifyChangedListeners() {
        if (skinObservers == null) return;
        for (ISkinChangeListener observer : skinObservers) {
            observer.onSkinChange();
        }
    }

    public ResourceManager getResourceManager() {
        if (mode != SkinMode.MODE_PLUGIN) {
            mResourceManager = new ResourceManager(mContext, mContext.getResources(), mContext.getPackageName(), mSuffix);
        }
        return mResourceManager;
    }

    /**
     * 清理插件信息
     */
    private void clearPluginInfo() {
        mPluginPath = null;
        mPluginPackage = null;
        mSuffix = null;
        changeSkinMode(SkinMode.MODE_DEFAULT);
        if (mSp != null) {
            mSp.clear();
        }
    }

    private void preparePlugin() {
        final String skinPluginPath = mSp.getPluginPath();
        final String skinPluginPkg = mSp.getPluginPackage();
        final String suffix = mSp.getAttrSuffix();
        Log.d(TAG, "M SKIN load plugin params Path=" + skinPluginPath + " pkg=" + skinPluginPkg + " suffix" + suffix);
        if (skinPluginPath.equals(SkinSPHelper.LOCAL_SKIN_TAG)) {
            if (TextUtils.isEmpty(suffix)) {
                changeSkinMode(SkinMode.MODE_DEFAULT);
            } else {
                changeSkinMode(SkinMode.MODE_LOCAL);
            }
            return;
        }
        if (!isPluginParamsValid(skinPluginPath, skinPluginPkg)) {
            Log.e(TAG, "M SKIN load plugin error invalid params");
            changeSkinMode(SkinMode.MODE_DEFAULT);
            mSp.clear();
            return;
        }
        //切换至子线程 加载皮肤包
        DefaultPoolExecutor.getInstance().execute(new Runnable() {

            @Override
            public void run() {
                try {
                    loadPlugin(skinPluginPath, skinPluginPkg, suffix);
                    mPluginPath = skinPluginPath;
                    mPluginPackage = skinPluginPkg;
                    mSuffix = suffix;
                    mainThread.post(new Runnable() {
                        @Override
                        public void run() {
                            notifyChangedListeners();
                        }
                    });
                    Log.d(TAG, "M SKIN load plugin success");
                } catch (Exception e) {
                    Log.e(TAG, "M SKIN load plugin error");
                    //加载失败时清除sp
                    changeSkinMode(SkinMode.MODE_DEFAULT);
                    mSp.clear();
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 加载皮肤包插件
     */
    private void loadPlugin(String skinPluginPath, String skinPluginPkg, String mSuffix) throws Exception {
        AssetManager assetManager = AssetManager.class.newInstance();
        Method addAssetPath = assetManager.getClass().getMethod(Constants.METHOD_ASSET_PATH, String.class);
        addAssetPath.invoke(assetManager, skinPluginPath);
        Resources superRes = mContext.getResources();
        Resources mResources = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
        mResourceManager = new ResourceManager(mContext, mResources, skinPluginPkg, mSuffix);
        changeSkinMode(SkinMode.MODE_PLUGIN);
    }

    /**
     * 更新插件信息
     */
    private void updatePluginInfo(String skinPluginPath, String pkgName, String suffix) {
        if (mSp != null) {
            mSp.setPluginInfo(skinPluginPath, pkgName, suffix);
        }
        mPluginPackage = pkgName;
        mPluginPath = skinPluginPath;
        mSuffix = suffix;
    }

    /**
     * 检查皮肤包插件参数
     */
    private boolean isPluginParamsValid(String skinPath, String skinPkgName) {
        if (TextUtils.isEmpty(skinPath) || TextUtils.isEmpty(skinPkgName)) {
            return false;
        }
        File file = new File(skinPath);
        if (!file.exists()) {
            return false;
        }
        PackageManager pm = mContext.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES);
        if (info == null || !info.packageName.equals(skinPkgName)) {
            return false;
        }
        return true;
    }

    public void changeSkinMode(@SkinMode int mode) {
        this.mode = mode;
    }

    @SkinMode
    public int getSkinMode() {
        return mode;
    }
}
