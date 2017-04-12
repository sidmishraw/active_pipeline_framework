/**
 * Project: PipelineFramework
 * Package: org.sjsu.sidmishraw.framework.pipeline.core
 * File: Filter.java
 * 
 * @author sidmishraw
 *         Last modified: Apr 12, 2017 1:02:57 AM
 */
package org.sjsu.sidmishraw.framework.pipeline.core;

/**
 * @author sidmishraw
 *
 *         Qualified Name: org.sjsu.sidmishraw.framework.pipeline.core.Filter
 *
 */
public abstract class Filter<T> implements Runnable {
	
	
	protected Pipe<T>	inPipe	= null;
	protected Pipe<T>	outPipe	= null;
	
	/**
	 * 
	 */
	public Filter() {}
	
	/**
	 * @param inPipe
	 * @param outPipe
	 */
	public Filter(Pipe<T> inPipe, Pipe<T> outPipe) {
		
		this.inPipe = inPipe;
		this.outPipe = outPipe;
	}
	
	/**
	 * @return the inPipe
	 */
	public Pipe<T> getInPipe() {
		
		
		return this.inPipe;
	}
	
	/**
	 * @param inPipe
	 *            the inPipe to set
	 */
	public void setInPipe(Pipe<T> inPipe) {
		
		
		this.inPipe = inPipe;
	}
	
	/**
	 * @return the outPipe
	 */
	public Pipe<T> getOutPipe() {
		
		
		return this.outPipe;
	}
	
	/**
	 * @param outPipe
	 *            the outPipe to set
	 */
	public void setOutPipe(Pipe<T> outPipe) {
		
		
		this.outPipe = outPipe;
	}
	
}
