package org.uurla.services.exception;

import org.uurla.services.PersistenceFactory;
import org.uurla.services.UurlaServiceKit;

/*
 * L'applicazione non è nello stato giusto
 */
public class UurlaMessageException extends Exception {
	Object[] params = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = -2482582810541331871L;

	public UurlaMessageException(String message, Object[] params) {
		super(message);
		this.params = params;
	}

	public UurlaMessageException(String message) {
		super(message);
		this.params = null;
	}

	@Override
	public String getMessage() {
		String msg = super.getMessage();
		PersistenceFactory pf = UurlaServiceKit.getPersistenceFactoryImpl();
		return msg;
	}

	@Override
	public String getLocalizedMessage() {
		// TODO Auto-generated method stub
		return super.getLocalizedMessage();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	public UurlaMessageException(String message, Throwable cause) {
		super(message, cause);
	}
	public UurlaMessageException(String message,Object[] params, Throwable cause) {
		super(message, cause);
		this.params=params;
	}

	public UurlaMessageException(Throwable cause) {
		super(cause);
	}

}
