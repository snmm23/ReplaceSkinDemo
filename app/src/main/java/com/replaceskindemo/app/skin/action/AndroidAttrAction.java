package com.replaceskindemo.app.skin.action;


public abstract class AndroidAttrAction implements LayoutAttrAction{

	protected String NAME_SPACE = "android";
	
	@Override
	public String namespace() {
		return NAME_SPACE;
	}

}
