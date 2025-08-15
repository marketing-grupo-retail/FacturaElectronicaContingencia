package com.grpretail.framework.business.exception;

/**
 * Projct: ITSO PiggyBank Application
 *
 * All business process related exeptions are 
 * indicated by the ServiceException.
 * 
 * @author markus.meser@ch.ibm.com
 */
public class DataAccessException extends Exception {

	/**
	*  An optional nested exception used to provide the ability to encapsulate
	*  a lower-level exception to provide more detailed context information
	*  concerning the exact cause of the exception.
	*/
	protected Throwable rootCause = null;

	/**
	* The additional error code.
	*/
	private String errorCode = null;

	/**
	 * Constructor for ServiceException.
	 */
	public DataAccessException() {
		super();
	}

	/**
	 * Constructor for ServiceException.
	 * @param s
	 */
	public DataAccessException(String s) {
		super(s);
	}

	/**
	 * Constructor for ServiceException.
	 * @param s
	 */
	public DataAccessException(String s, Throwable rootCause) {
		super(s);
		this.rootCause = rootCause;
	}

	/**
	 * Constructor for ServiceException.
	 * @param s
	 */
	public DataAccessException(String s, String errorCode) {
		super(s);
		this.errorCode = errorCode;
	}

	/**
	 * Constructor for ServiceException.
	 * @param s
	 */
	public DataAccessException(String s, String errorCode, Throwable cause) {
		this(s, errorCode);
		this.rootCause = cause;
	}

	/**
	 * Returns the rootCause.
	 * @return Throwable
	 */
	public Throwable getRootCause() {
		return rootCause;
	}

	/**
	 * Returns the errorCode.
	 * @return String
	 */
	public String getErrorCode() {
		return errorCode;
	}

}
