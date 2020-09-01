package com.replaceskindemo.app.skin;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;

import com.replaceskindemo.app.skin.exception.SkinException;
import com.replaceskindemo.app.skin.exception.SkinResouceNotFoundException;
import com.replaceskindemo.app.skin.exception.SkinRunntimeException;
import com.replaceskindemo.app.skin.loader.AndroidResourcesLoader;
import com.replaceskindemo.app.support.LayoutInflaterCompat;


public final class SkinManager {

	private static final String TAG = SkinManager.class.getSimpleName();
	
	public interface SkinAttrs {
		
		public static final String SKIN_NAME_SPACE = "http://schemas.android.com/android/ViewSkin";
		public static final String SKIN_ATTR_SUPPORT_NAME = "isSupport";
		
	}
	
	private Resources skinResources;
	private String skinPackageName;
	private static SkinManager defaultInstance = new SkinManager();
	final SkinConfiguration defaultConfiguration = new SkinConfiguration();
	private SkinConfiguration skinConfiguration = defaultConfiguration;
	
	public SkinManager(){} 
	
	public static SkinManager getDefault(){
		return defaultInstance;
	}
	
	public SkinManager config(SkinConfiguration config) {
		skinConfiguration = config;
		return new SkinManager();
	}
	
	public SkinConfiguration getConfiguration(){
		return skinConfiguration;
	}
	
	public void setSkinResource(Context context,Resources res,String resPackage) throws SkinException {
		
		if(!isSupport()) {
			Log.i(TAG, "not support skin replace");
			return ;
		}
		
		skinResources = res;
		skinPackageName = resPackage;
		
	}
	
	public void loadSkinByApkPath(Context context,String skinApkPath) throws SkinException {
		
		if(!isSupport()) {
			Log.i(TAG, "not support skin replace");
			return ;
		}
		
		if(context == null || skinApkPath == null)
			throw new SkinRunntimeException("skinApkPath and context ,  can't be null");

		try {
			PackageManager pm = context.getPackageManager();
	        PackageInfo packageInfo = pm.getPackageArchiveInfo(skinApkPath, PackageManager.GET_ACTIVITIES);
	        
			skinResources = AndroidResourcesLoader.load(skinApkPath, context);
			skinPackageName = packageInfo.packageName;
		} catch(Exception e){
			throw new SkinException("load skin exception" , e);
		}
		
	}
	
	public void loadSkinByPackageName(Context context,String packageName) throws SkinException {
		
		if(!isSupport()) {
			Log.i(TAG, "not support skin replace");
			return ;
		}
		
		if(context == null || packageName == null)
			throw new SkinRunntimeException("packageName and context ,  can't be null");
		
		PackageManager pm = context.getPackageManager();
		try {
			PackageInfo pi = pm.getPackageInfo(packageName, 0);
			
			String sourceDir = pi.applicationInfo.sourceDir;
			
			loadSkinByApkPath(context,sourceDir);
			
		} catch (Exception e) {
			throw new SkinException("package not found" , e);
		}
	}
	
	private void check(){
		if(skinResources == null)
			throw new SkinRunntimeException("android.content.res.Resources(skinResources) ,  can't be null");
	}
	
	private boolean isSupport(){
		return getConfiguration().isSupport();
	}
	
	public void initSkinLayoutFactory(LayoutInflater inflater){
		
		if(!isSupport()) {
			Log.i(TAG, "initSkinLayoutFactory not support skin replace");
			return ;
		}
		
		if(skinResources == null){
			Log.i(TAG, "1.skin resouce load fail \n2.package not found");
			return ;
		}
		
		LayoutInflaterCompat.setFactory(inflater, new SkinLayoutFactory());
	}
	/**
	 * 获取资源
	 * @param resName 资源名称
	 * @return
	 * @throws SkinResouceNotFoundException 
	 */
	public int getColorResource(String resName) throws SkinResouceNotFoundException {
		
		check();

		if(TextUtils.isEmpty(resName)){
			throw new SkinResouceNotFoundException("resName = null, resStyle = color not found in skin resouces");
		}
		
		Log.i(TAG, "getColorResource = " + resName + " , skin packagename = " + skinPackageName);
		
		int resId = skinResources.getIdentifier(resName, "color", skinPackageName);
		
		Log.i(TAG, "res id = " + resId);
		
		int finalColorValue = -1;
		
		try {
			finalColorValue = skinResources.getColor(resId);
			
			Log.i(TAG, "getColorResource final color value = " + finalColorValue);
			
		} catch(Exception e){
			Log.i(TAG, "getColorResource exception = " + e.getMessage());
			throw new SkinResouceNotFoundException("resName = " + resName + " , resStyle = color not found in skin resouces" , e);
		}
		
		return finalColorValue;
	}
	
	/**
	 * 获取资源
	 * @param resName 资源名称
	 * @return
	 * @throws SkinResouceNotFoundException 
	 */
	public float getDimenResource(String resName) throws SkinResouceNotFoundException{
		
		check();

		if(TextUtils.isEmpty(resName)){
			throw new SkinResouceNotFoundException("resName = null, resStyle = dimen not found in skin resouces");
		}
		
		Log.i(TAG, "getDimenResource = " + resName + " , skin packagename = " + skinPackageName);
		int resId = skinResources.getIdentifier(resName, "dimen", skinPackageName);
		
		Log.i(TAG, "res id = " + resId);
		
		float finalColorValue = -1f;
		
		try {
			finalColorValue = skinResources.getDimension(resId);
			
			Log.i(TAG, "getDimenResource final dimen value = " + finalColorValue);
			
		} catch(Exception e){
			Log.i(TAG, "getDimenResource exception = " + e.getMessage());
			throw new SkinResouceNotFoundException("resName = " + resName + " , resStyle = dimen not found in skin resouces" , e);
		}
		
		return finalColorValue;
	}
	
	/**
	 * 获取资源
	 * @param resName 资源名称
	 * @return
	 * @throws SkinResouceNotFoundException 
	 */
	public Drawable getDrawableResource(String resName) throws SkinResouceNotFoundException{
		
		check();

		if(TextUtils.isEmpty(resName)){
			throw new SkinResouceNotFoundException("resName = null, resStyle = drawable not found in skin resouces");
		}
		
		int resId = skinResources.getIdentifier(resName, "drawable", skinPackageName);
		
		Drawable finalRes = null;
		try {
			finalRes = skinResources.getDrawable(resId);
		} catch(Exception e){
			throw new SkinResouceNotFoundException("resName = " + resName + " , resStyle = drawable not found in skin resouces" , e);
		}
		
		if(finalRes == null){
			throw new SkinResouceNotFoundException("resName = " + resName + " , resStyle = drawable not found in skin resouces");
		}
		
		return finalRes;
	}
}
