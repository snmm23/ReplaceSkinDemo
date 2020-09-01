package com.replaceskindemo.app.skin.action;

import android.graphics.drawable.Drawable;
import android.util.Log;

import com.replaceskindemo.app.skin.SkinManager;
import com.replaceskindemo.app.skin.exception.SkinResouceNotFoundException;


public class AndroidLayoutBackgroupAttrAction extends AndroidAttrAction{
	
	public static final String ATTR_NAME = "background";
	public static final int ATTR_INT = android.R.attr.background;
	
	private static final String TAG = AndroidLayoutBackgroupAttrAction.class.getSimpleName();
	
	public void handle(LayoutAttrAction.ActionArgs actionArgs){
    	try {

    		Log.i(TAG, "[SkinManager] ****backgroupAction attrValue = " + actionArgs.attr.entryName);

			Drawable resource = SkinManager.getDefault().getDrawableResource(actionArgs.attr.entryName);

			Log.i(TAG, "[SkinManager] backgroupAction view instance = " + actionArgs.target);

			actionArgs.target.setBackgroundDrawable(resource);

			Log.i(TAG, "[SkinManager] backgroupAction setBackgroundDrawable completed");

		} catch (SkinResouceNotFoundException e) {
			e.printStackTrace();

			Log.i(TAG, "[SkinManager] SkinResouceNotFoundException ("+e.getMessage()+")");
		}

	}

	@Override
	public String attrName() {
		return ATTR_NAME;
	}

	@Override
	public int attrInt() {
		return ATTR_INT;
	}
}
