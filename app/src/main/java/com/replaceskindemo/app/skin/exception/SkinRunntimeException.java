package com.replaceskindemo.app.skin.exception;

import android.util.AndroidRuntimeException;


public class SkinRunntimeException extends AndroidRuntimeException {

	private static final long serialVersionUID = 1L;

	public SkinRunntimeException(Exception cause) {
		super(cause);
	}

	public SkinRunntimeException(String name) {
		super(name);
	}
	
}
