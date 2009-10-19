package com.icecream.exception;

public class MissingComponentException extends Exception{
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 5091083733501702459L;
	
	/**
	 * Constructor of <code>MissingComponentException</code>.
	 */
	public MissingComponentException() {
		super();
	}

	/**
	 * Constructor of <code>MissingComponentException</code>.
	 * @param component The name of the <code>Component</code> that is missing.
	 */
	public MissingComponentException(String component) {
		super("Missing Component: " + component.substring(component.lastIndexOf(".")+1, component.length()));
	}
}