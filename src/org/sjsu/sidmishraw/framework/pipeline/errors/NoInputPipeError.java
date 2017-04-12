/**
 *	Project:	PipelineFramework
 *  Package:	org.sjsu.sidmishraw.framework.pipeline.errors
 *  File:		NoInputPipeError.java
 * 
 *  @author 			sidmishraw
 *  Last modified: 		Apr 12, 2017 11:28:49 AM
 */
package org.sjsu.sidmishraw.framework.pipeline.errors;


/**
 * @author 			sidmishraw
 *
 * Qualified Name: 	org.sjsu.sidmishraw.framework.pipeline.errors.NoInputPipeError
 *
 */
public class NoInputPipeError extends Exception {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public NoInputPipeError() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param message
	 */
	public NoInputPipeError(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param cause
	 */
	public NoInputPipeError(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param message
	 * @param cause
	 */
	public NoInputPipeError(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public NoInputPipeError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}
	
}
