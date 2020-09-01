package com.replaceskindemo.app.skin.action;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;


public class AndroidLayoutStyleAttrAction extends AndroidAttrAction{
	
	public static final String ATTR_NAME = "style";
	public static final int ATTR_INT = 10000;
	
	private static final String TAG = AndroidLayoutStyleAttrAction.class.getSimpleName();
	
	public void handle(LayoutAttrAction.ActionArgs actionArgs){
		Context context = actionArgs.context;
        
        Log.i(TAG, "[SkinManager] *****styleAction style name = " + actionArgs.attr.entryName);
        
        int styleID = context.getResources().getIdentifier(actionArgs.attr.entryName, "style", context.getPackageName());
        
        Log.i(TAG, "[SkinManager] styleAction style id = " + styleID);
        
        final int[] attrArray = ActionFactory.getSupportAttrs();
        
        TypedArray typedArray = context.obtainStyledAttributes(styleID, attrArray);
        
        int indexCount = typedArray.getIndexCount();
        Log.i(TAG, "[SkinManager] styleAction obtain styled attributes typedArray count = " + indexCount + " , attrArray len = " + attrArray.length);
        
        try {
        	for(int i = 0;i < attrArray.length;i++){
        		int resId  = typedArray.getResourceId(i, -1);
        		
                if(resId!=-1){
                	
                	int supportAttrInt = attrArray[i];
                	
                	LayoutAttrAction.Attr attr = new LayoutAttrAction.Attr();
                	
                	String entryName = context.getResources().getResourceEntryName(resId);
                	String typeName = context.getResources().getResourceTypeName(resId);
                	LayoutAttrAction layoutAttrAction = ActionFactory.getLayoutAttrAction(supportAttrInt);
                	
                	attr.entryName = entryName;
                	
                	Log.i(TAG, "[SkinManager] styleAction style item entryName = " + entryName + " , typeName = " + typeName + ", attr name = " + layoutAttrAction.attrName()+" resId = " + resId);
	                
	                actionArgs.attr = attr;
	                
	                Log.i(TAG, "[SkinManager] styleAction action = " + layoutAttrAction);
	                
	                layoutAttrAction.handle(ActionArgs.obtain(actionArgs));
                	
                }
            }
        } finally {
        	typedArray.recycle();
        }
    
		
	}

	@Override
	public String attrName() {
		// TODO Auto-generated method stub
		return ATTR_NAME;
	}

	@Override
	public int attrInt() {
		// TODO Auto-generated method stub
		return ATTR_INT;
	}
}
