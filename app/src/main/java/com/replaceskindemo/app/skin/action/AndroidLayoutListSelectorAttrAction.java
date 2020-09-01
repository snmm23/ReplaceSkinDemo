package com.replaceskindemo.app.skin.action;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.replaceskindemo.app.skin.SkinManager;
import com.replaceskindemo.app.skin.exception.SkinResouceNotFoundException;


public class AndroidLayoutListSelectorAttrAction extends AndroidAttrAction{

	public static final String ATTR_NAME = "listSelector";
	public static final int ATTR_INT = android.R.attr.listSelector;

	private static final String TAG = AndroidLayoutListSelectorAttrAction.class.getSimpleName();

	public void handle(ActionArgs actionArgs){
		try {

			Log.i(TAG, "[SkinManager] ****listSelectorAction attrValue = " + actionArgs.attr.entryName);

			Drawable resource = SkinManager.getDefault().getDrawableResource(actionArgs.attr.entryName);

			Log.i(TAG, "[SkinManager] listSelectorAction view instance = " + actionArgs.target);

			if(actionArgs.target!=null){
				if(actionArgs.target instanceof ListView){
					ListView listView = (ListView)  actionArgs.target;

					listView.setSelector(resource);

					Log.i(TAG, "[SkinManager] listSelectorAction setListSelectorDrawable completed");
				}
				if(actionArgs.target instanceof ExpandableListView){
					ExpandableListView listView = (ExpandableListView)  actionArgs.target;

					listView.setSelector(resource);

					Log.i(TAG, "[SkinManager] listSelectorAction setListSelectorDrawable completed");
				}



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
