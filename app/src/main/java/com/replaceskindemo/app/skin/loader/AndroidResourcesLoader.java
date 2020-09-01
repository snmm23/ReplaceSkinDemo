package com.replaceskindemo.app.skin.loader;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

import com.replaceskindemo.app.skin.exception.SkinResouceLoadException;

import dalvik.system.DexClassLoader;


public class AndroidResourcesLoader {

	private final static String TAG = "ResourceAppLoader";
	
	public static Resources load(String apkPath , Context context) throws SkinResouceLoadException {
		Log.i(TAG, "PlusinUtil#startService");
		DexClassLoader dexClassLoader = initDexClassLoader( apkPath , context);
		try {
			return functionPlugin( apkPath ,dexClassLoader ,context);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SkinResouceLoadException("android resouces object load exception" , e);
		}
	
	}
	

	private static DexClassLoader initDexClassLoader(String apkPath,Context context) {
		DexClassLoader dexClassLoader = null;
		final String dexOutputPath = context.getDir("plugin",Context.MODE_PRIVATE).getAbsolutePath();
		final String libraryPath = context.getDir("plugin_lib",Context.MODE_PRIVATE).getAbsolutePath();

		Log.i(TAG, "dex apk path = " + apkPath + " , dex output path = "+ apkPath + " , lib path = " + libraryPath);

		ClassLoader systemCalssLoader = getSystemLoader();

		Log.i(TAG, "system class loader = " + systemCalssLoader);

		ClassLoader parent = (systemCalssLoader == null ? context.getClassLoader() : systemCalssLoader);

		dexClassLoader = new DexClassLoader(apkPath, dexOutputPath,libraryPath, parent);

		Log.i(TAG, "dex class loader = " + dexClassLoader);
		return dexClassLoader;
	}
	
	
	private static Resources functionPlugin(String apkPath , DexClassLoader dexClassLoader , Context context) throws NameNotFoundException, InstantiationException, IllegalAccessException {
		PackageInfo packageInfo = getPluginApkInfo(context ,apkPath);
		
		if (packageInfo==null) {
			Log.i(TAG,"functionPlugin packageInfo == null");
			return null;
		}
		Resources resources = getPlugApkResource(apkPath, context);
		return resources;
	}
	
	private static PackageInfo getPluginApkInfo(Context cxt, String pluginApkPath)
            throws NameNotFoundException {
        PackageManager pm = cxt.getPackageManager();
        PackageInfo pkgInfo = null;
        pkgInfo = pm.getPackageArchiveInfo(pluginApkPath,PackageManager.GET_ACTIVITIES | PackageManager.GET_SERVICES);
        return pkgInfo;
    }
    
    private static ClassLoader getSystemLoader() {
        Context context = Reflect.on("android.app.ActivityThread").call("currentActivityThread").call("getSystemContext").get();
        return context == null ? null : context.getClassLoader();
    }
	
	private static Resources getPlugApkResource(String pluginApkPath, Context ctx) throws InstantiationException, IllegalAccessException {
       
        AssetManager assetManager = AssetManager.class.newInstance();
        Reflect assetRef = Reflect.on(assetManager);
        assetRef.call("addAssetPath", pluginApkPath);
        
        Resources pluginRes = new Resources(assetManager, ctx
                .getResources().getDisplayMetrics(), ctx.getResources()
                .getConfiguration());
        
        return pluginRes;
    }
}
