package org.uurla.services.exception;


/*
 * L'applicazione non è nello stato giusto
 */
public class UurlaAppAuthException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2482582810541331871L;
	
    public UurlaAppAuthException() {
        super();
    }

    public UurlaAppAuthException(String message) {
        super(message);
    }

    public UurlaAppAuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public UurlaAppAuthException(Throwable cause) {
        super(cause);
    }


}
