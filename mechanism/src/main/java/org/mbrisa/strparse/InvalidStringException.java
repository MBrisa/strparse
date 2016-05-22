package org.mbrisa.strparse;

public class InvalidStringException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -126277674416220486L;

	public InvalidStringException() {
		super();
	}

	public InvalidStringException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidStringException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidStringException(String message) {
		super(message);
	}

	public InvalidStringException(Throwable cause) {
		super(cause);
	}

	
	
}
