package com.replaceskindemo.app.skin.action;

import android.util.Log;
import android.widget.TextView;

import com.replaceskindemo.app.skin.SkinManager;
import com.replaceskindemo.app.skin.exception.SkinResouceNotFoundException;


public class AndroidLayoutTextColorAttrAction extends AndroidAttrAction{
	
	public static final String ATTR_NAME = "textColor";
	public static final int ATTR_INT = android.R.attr.textColor;
	
	private static final String TAG = AndroidLayoutTextColorAttrAction.class.getSimpleName();
	
	public void handle(LayoutAttrAction.ActionArgs actionArgs){
		
		Log.e(TAG, "[SkinManager] *****textColorAction attrValue = " + actionArgs.attr.entryName);
		
        try {
			int colorRes = SkinManager.getDefault().getColorResource(actionArgs.attr.entryName);
			
			Log.e(TAG, "[SkinManager] textColorAction view instance = " + actionArgs.target);
			
			if(actionArgs.target!=null){
				TextView textView = (TextView)  actionArgs.target;
				
				textView.setTextColor(colorRes);
				
				Log.e(TAG, "[SkinManager] textColorAction setTextColor Completed");
			}
			
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
