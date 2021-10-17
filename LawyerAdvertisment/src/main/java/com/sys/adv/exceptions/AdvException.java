package com.sys.adv.exceptions;

public class AdvException extends Exception {
	private static final long serialVersionUID = -8732594430590077827L;
	
	private Throwable throwable;
	private String message;

	/**
	 * 
	 */
	public AdvException() {
	}
	
	/**
	 * 
	 * @param throwable
	 */
	public AdvException(Throwable throwable) {
		this.throwable = throwable;
	}
	
	/**
	 * 
	 * @param message
	 */
	public AdvException (String message) {
		this.message = message;
	}
	
	/**
	 * 
	 * @param throwable
	 * @param message
	 */
	public AdvException (Throwable throwable, String message) {
		this.message = message;
		this.throwable = throwable;
	}
	
	/**
	 * @return the throwable
	 */
	public Throwable getThrowable() {
		return throwable;
	}
	/**
	 * @param throwable the throwable to set
	 */
	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param msg the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
