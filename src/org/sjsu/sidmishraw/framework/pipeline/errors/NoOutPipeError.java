/**
 *	Project:	PipelineFramework
 *  Package:	org.sjsu.sidmishraw.framework.pipeline.errors
 *  File:		NoOutPipeError.java
 * 
 *  @author 			sidmishraw
 *  Last modified: 		Apr 12, 2017 9:59:13 AM
 */
package org.sjsu.sidmishraw.framework.pipeline.errors;


/**
 * @author 			sidmishraw
 *
 * Qualified Name: 	org.sjsu.sidmishraw.framework.pipeline.errors.NoOutPipeError
 *
 */
public class NoOutPipeError extends Exception {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public NoOutPipeError() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param message
	 */
	public NoOutPipeError(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param cause
	 */
	public NoOutPipeError(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param message
	 * @param cause
	 */
	public NoOutPipeError(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public NoOutPipeError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}
	
}
