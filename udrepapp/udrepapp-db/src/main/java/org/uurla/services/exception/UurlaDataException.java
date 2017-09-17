package org.uurla.services.exception;

import java.util.Locale;


public class UurlaDataException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5405192442763793956L;
	Locale loc = Locale.ITALY;
	int code = 0;


	public UurlaDataException(String message, Locale locale, int code){
		super(message);
		loc=locale;
		this.code=code;
	}

	public UurlaDataException(String message) {
		super(message);
	}

	public int getCode(){return code;}
	public Locale getLocale(){return loc;}
}
