/**
 *	Project:	PipelineFramework
 *  Package:	org.sjsu.sidmishraw.framework.pipeline.errors
 *  File:		DisconnectedTesterError.java
 * 
 *  @author 			sidmishraw
 *  Last modified: 		Apr 12, 2017 11:57:24 AM
 */
package org.sjsu.sidmishraw.framework.pipeline.errors;


/**
 * @author 			sidmishraw
 *
 * Qualified Name: 	org.sjsu.sidmishraw.framework.pipeline.errors.DisconnectedTesterError
 *
 */
public class DisconnectedTesterError extends Exception {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public DisconnectedTesterError() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param message
	 */
	public DisconnectedTesterError(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param cause
	 */
	public DisconnectedTesterError(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param message
	 * @param cause
	 */
	public DisconnectedTesterError(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public DisconnectedTesterError(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}
	
}
