package com.replaceskindemo.app.skin.action;

import android.util.Log;
import android.widget.TextView;

import com.replaceskindemo.app.skin.SkinManager;
import com.replaceskindemo.app.skin.exception.SkinResouceNotFoundException;


public class AndroidLayoutTextSizeAttrAction extends AndroidAttrAction{
	
	public static final String ATTR_NAME = "textSize";
	public static final int ATTR_INT = android.R.attr.textSize;
	
	private static final String TAG = AndroidLayoutTextSizeAttrAction.class.getSimpleName();
	
	public void handle(LayoutAttrAction.ActionArgs actionArgs){
		
		Log.e(TAG, "[SkinManager] *****textSizeAction attrValue = " + actionArgs.attr.entryName);
		
        try {
			float textSize = SkinManager.getDefault().getDimenResource(actionArgs.attr.entryName);
			
			Log.e(TAG, "[SkinManager] textSizeAction view instance = " + actionArgs.target);
			Log.e(TAG, "[SkinManager] textSizeAction textSize = " + textSize);
			
			if(actionArgs.target!=null){
				TextView textView = (TextView)  actionArgs.target;
				
				textView.setTextSize(textSize);
				
				Log.e(TAG, "[SkinManager] textSizeAction setTextSize Completed");
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
