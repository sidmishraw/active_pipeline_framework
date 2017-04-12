/**
 * Project: PipelineFramework
 * Package: org.sjsu.sidmishraw.framework.pipeline.core
 * File: Message.java
 * 
 * @author sidmishraw
 *         Last modified: Apr 12, 2017 12:52:55 AM
 */
package org.sjsu.sidmishraw.framework.pipeline.core;

/**
 * @author sidmishraw
 *
 *         Qualified Name: org.sjsu.sidmishraw.framework.pipeline.core.Message
 *
 */
public class Message<T> {
	
	
	private T		content	= null;
	private boolean	quit	= false;
	private boolean	fail	= false;
	
	/**
	 * 
	 */
	public Message() {}
	
	/**
	 * @param content
	 * @param quit
	 * @param fail
	 */
	public Message(T content, boolean quit, boolean fail) {
		
		this.content = content;
		this.quit = quit;
		this.fail = fail;
	}
	
	/**
	 * @return the content
	 */
	public T getContent() {
		
		
		return this.content;
	}
	
	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(T content) {
		
		
		this.content = content;
	}
	
	/**
	 * @return the quit
	 */
	public boolean isQuit() {
		
		
		return this.quit;
	}
	
	/**
	 * @param quit
	 *            the quit to set
	 */
	public void setQuit(boolean quit) {
		
		
		this.quit = quit;
	}
	
	/**
	 * @return the fail
	 */
	public boolean isFail() {
		
		
		return this.fail;
	}
	
	/**
	 * @param fail
	 *            the fail to set
	 */
	public void setFail(boolean fail) {
		
		
		this.fail = fail;
	}
	
}
