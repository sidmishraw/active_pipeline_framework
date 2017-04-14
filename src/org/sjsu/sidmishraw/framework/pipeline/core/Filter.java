/**
 * Project: PipelineFramework
 * Package: org.sjsu.sidmishraw.framework.pipeline.core
 * File: Filter.java
 * 
 * @author sidmishraw
 *         Last modified: Apr 12, 2017 1:02:57 AM
 */
package org.sjsu.sidmishraw.framework.pipeline.core;

import org.sjsu.sidmishraw.framework.pipeline.errors.PipesNotAttachedError;

/**
 * @author sidmishraw
 *
 *         Qualified Name: org.sjsu.sidmishraw.framework.pipeline.core.Filter
 *
 */
public abstract class Filter<T> implements Runnable {
	
	
	protected Pipe<T>	inPipe	= null;
	protected Pipe<T>	outPipe	= null;
	
	// this thread will start automatically when Producer is created, this way
	// the user of the framework
	// doesn't need to create threads explicitly in the main driver to start the
	// Producer
	private Thread		me		= null;
	
	/**
	 * 
	 */
	public Filter() {
		
		// create a thread for the filter and start it
		this.me = new Thread(this, this.getClass().getName());
	}
	
	/**
	 * @param inPipe
	 * @param outPipe
	 */
	public Filter(Pipe<T> inPipe, Pipe<T> outPipe) {
		
		this.inPipe = inPipe;
		this.outPipe = outPipe;
		
		// create a thread for the filter and start it
		this.me = new Thread(this, this.getClass().getName());
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
	
	/**
	 * Connects this filter to it's next filter in the pipeline
	 * 
	 * @param nextFilter
	 */
	public void connectOut(Filter<T> nextFilter) {
		
		this.outPipe = new Pipe<>();
		nextFilter.setInPipe(this.outPipe);
	}
	
	/**
	 * Connects this filter to it's previous filter in the pipeline
	 * 
	 * @param prevFilter
	 */
	public void connectIn(Filter<T> prevFilter) {
		
		this.inPipe = new Pipe<>();
		prevFilter.setOutPipe(this.inPipe);
	}
	
	/**
	 * Starts the filter, needs to be called on the filter to start the filter
	 * in its own thread of control, it done so that the Filter doesn't start up
	 * without the inPipes and outPipes attached.
	 */
	public final void startThis() throws PipesNotAttachedError {
		
		if (null == this.inPipe && null == this.outPipe) {
			
			throw new PipesNotAttachedError("Please connect the filter to another filter before starting.");
		}
		
		this.me.start();
	}
	
	/**
	 * The calling thread waits till Filter dies
	 * 
	 * @throws InterruptedException
	 */
	public final void waitForMe() throws InterruptedException {
		
		this.me.join();
	}
	
	/**
	 * Checks if this thread is alive
	 * 
	 * @return boolean
	 */
	public final boolean isAlive() {
		
		return this.me.isAlive();
	}
}
