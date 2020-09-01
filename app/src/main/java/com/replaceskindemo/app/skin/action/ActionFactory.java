package com.replaceskindemo.app.skin.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public final class ActionFactory {

	private static final List<LayoutAttrAction> actionList = new ArrayList<LayoutAttrAction>();
	
	private static LayoutAttrAction sLayoutStyleAttrAction = new AndroidLayoutStyleAttrAction();
	
	private static int[] supportAttrArray;
	
	private static final LayoutAttrAction EMTPY = new LayoutAttrAction() {
		
		private static final String NONE = "NONE";
		
		@Override
		public void handle(ActionArgs actionArgs) {
		}

		@Override
		public String namespace() {
			return NONE;
		}

		@Override
		public String attrName() {
			return NONE;
		}

		@Override
		public int attrInt() {
			return -1;
		}
	};
	
	
	static {
		
		addLayoutAttrHolder(new AndroidLayoutTextSizeAttrAction());
		addLayoutAttrHolder(new AndroidLayoutTextColorAttrAction());
		addLayoutAttrHolder(new AndroidLayoutBackgroupAttrAction());
		addLayoutAttrHolder(new AndroidLayoutListSelectorAttrAction());

		fillAttrArray();
		
	}
	
	public static List<LayoutAttrAction> getSupportList(){
		return Collections.unmodifiableList(actionList);
	}
	
	public static boolean isSupportAttr(String attrName){
		
		if(AndroidLayoutStyleAttrAction.ATTR_NAME.equals(attrName)) {
			return true;
		}
		
		for (int i = 0; i < actionList.size(); i++) {
			LayoutAttrAction layoutAttrAction = actionList.get(i);
			
			if(layoutAttrAction.attrName().equals(attrName)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static LayoutAttrAction getLayoutAttrAction(int attrInt){
		
		if(sLayoutStyleAttrAction.attrInt() == attrInt) {
			return sLayoutStyleAttrAction;
		}
		
		for (int i = 0; i < actionList.size(); i++) {
			LayoutAttrAction layoutAttrAction = actionList.get(i);
			
			if(layoutAttrAction.attrInt() == attrInt) {
				return layoutAttrAction;
			}
		}
		
		return EMTPY;
	}
	
	public static LayoutAttrAction getLayoutAttrAction(String attrName){
		
		if(sLayoutStyleAttrAction.attrName().equals(attrName)) {
			return sLayoutStyleAttrAction;
		}
		
		for (int i = 0; i < actionList.size(); i++) {
			LayoutAttrAction layoutAttrAction = actionList.get(i);
			
			if(layoutAttrAction.attrName().equals(attrName)) {
				return layoutAttrAction;
			}
		}
		
		return EMTPY;
	}
	
	public static void addLayoutAttrHolder(LayoutAttrAction layoutAttrAction){
		actionList.add(layoutAttrAction);
		fillAttrArray();
	}
	
	public static int[] getSupportAttrs(){
		return supportAttrArray;
	}
	
	private static void fillAttrArray(){
		supportAttrArray = getSupportAttrsInternal();
	}
	
	private static int[] getSupportAttrsInternal(){
		
		int attrsArray[] = new int[actionList.size()];
		
		for (int i = 0; i < actionList.size(); i++) {
			LayoutAttrAction layoutAttrAction = actionList.get(i);
			
			attrsArray[i] = layoutAttrAction.attrInt();
		}
		
		return attrsArray;
	}
	
}
