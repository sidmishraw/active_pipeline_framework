/**
 *	Project:	PipelineFramework
 *  Package:	org.sjsu.sidmishraw.framework.pipeline.errors
 *  File:		DisconnectedTransformerError.java
 * 
 *  @author 			sidmishraw
 *  Last modified: 		Apr 12, 2017 10:30:24 AM
 */
package org.sjsu.sidmishraw.framework.pipeline.errors;


/**
 * @author 			sidmishraw
 *
 * Qualified Name: 	org.sjsu.sidmishraw.framework.pipeline.errors.DisconnectedTransformerError
 *
 */
public class DisconnectedTransformerError extends Exception {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public DisconnectedTransformerError() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param message
	 */
	public DisconnectedTransformerError(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param cause
	 */
	public DisconnectedTransformerError(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param message
	 * @param cause
	 */
	public DisconnectedTransformerError(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public DisconnectedTransformerError(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}
	
}
