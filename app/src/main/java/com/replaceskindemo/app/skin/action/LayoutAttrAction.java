package com.replaceskindemo.app.skin.action;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;


public interface LayoutAttrAction {
	
	public String namespace();
	public String attrName();
	public int attrInt();
	
	public void handle(ActionArgs actionArgs);
	
	public static class ActionArgs {
		public View parent;
		public View target;
		public String targetViewClassName;
		public Context context;
		public AttributeSet attrsSet;
		public Attr attr;
		private ActionArgs(View parent, View target, String className,
				Context context, AttributeSet attrsSet, Attr attr) {
			this.parent = parent;
			this.target = target;
			this.targetViewClassName = className;
			this.context = context;
			this.attrsSet = attrsSet;
			this.attr = attr;
		}
		
		public static ActionArgs obtain(ActionArgs actionArgs) {
			return new ActionArgs(actionArgs.parent,actionArgs.target,actionArgs.targetViewClassName,actionArgs.context,actionArgs.attrsSet,actionArgs.attr);
		}
		
		public static ActionArgs obtain(View parent, View target, String className,
				Context context, AttributeSet attrs, Attr attr) {
			return new ActionArgs(parent,target,className,context,attrs,attr);
		}
		
	}
	
	public static class Attr {
		public String name;
		public String value;
		public String entryName;
		
		public Attr(){}
	}
	
}
