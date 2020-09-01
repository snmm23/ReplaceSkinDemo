package com.replaceskindemo.app.skin;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.replaceskindemo.app.skin.action.ActionFactory;
import com.replaceskindemo.app.skin.action.LayoutAttrAction;
import com.replaceskindemo.app.support.LayoutInflaterFactory;


class SkinLayoutFactory implements LayoutInflaterFactory, SkinManager.SkinAttrs{

	private static final String TAG = SkinLayoutFactory.class.getSimpleName();
	
	@Override
	public View onCreateView(View parent, String name, Context context,AttributeSet attrs) {
		
		boolean isSupportSkinReplace = attrs.getAttributeBooleanValue(SKIN_NAME_SPACE, SKIN_ATTR_SUPPORT_NAME, false);
		
		Log.i(TAG, "[SkinManager] view name = " + name +" , isSupportSkinReplace = " + isSupportSkinReplace);
		
		if(isSupportSkinReplace) {
			
			View view = SkinViewMaker.make(parent, name, context, attrs);
			
			for (int i = 0; i < attrs.getAttributeCount(); i++) {
				
				String attrName = attrs.getAttributeName(i);
	            String attrValue = attrs.getAttributeValue(i);
	            
	            Log.i(TAG, "[SkinManager] attrName = " + attrName +" , attrValue = " + attrValue);
	            
	            if(ActionFactory.isSupportAttr(attrName)) {
	            	
	            	final LayoutAttrAction.Attr attr = new LayoutAttrAction.Attr();
	            	attr.name = attrName;
	            	attr.value = attrValue;

	            	try{
						final int attributeResId = attrs.getAttributeResourceValue(i, -1);

						if(attributeResId!=-1){
							String entryName = context.getResources().getResourceEntryName(attributeResId);
							attr.entryName = entryName;
							Log.i(TAG, "[SkinManager attrTrace] attributeResName = " + attributeResId + " , entryName = " + entryName);
						} else {
							Log.i(TAG, "[SkinManager attrTrace] attributeResName = " + attributeResId);
						}

						LayoutAttrAction layoutAttrAction = ActionFactory.getLayoutAttrAction(attrName);

						Log.i(TAG, "[SkinManager] layoutAttrAction = " + layoutAttrAction);

						if(layoutAttrAction!=null){

							layoutAttrAction.handle(LayoutAttrAction.ActionArgs.obtain(parent, view, attrName, context, attrs, attr));

						}
					}catch (Exception e){
						return view;
					}
	            }
			}
			return view;
		}
		return null;
	}
}
