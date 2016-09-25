package com.crt.jobs.exceptions;

/**
 * An unexpected exception
 */
public class UnexpectedException extends RuntimeException {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 5006650435397642529L;

	public UnexpectedException(String message) {
        super(message);
    }

    public UnexpectedException(Throwable exception) {
        super("Unexpected Error", exception);
    }
    
    public UnexpectedException(String message, Throwable cause) {
        super(message, cause);
    }
}

