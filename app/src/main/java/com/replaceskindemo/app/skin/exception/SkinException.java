package com.replaceskindemo.app.skin.exception;


public class SkinException extends Exception {

	private static final long serialVersionUID = 1L;

	public SkinException(String message, Throwable cause) {
		super(message, cause);
	}

	public SkinException(String message) {
		super(message);
	}

	public SkinException(Throwable cause) {
		super(cause);
	}

}
