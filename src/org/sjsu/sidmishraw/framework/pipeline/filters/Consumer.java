/**
 * Project: PipelineFramework
 * Package: org.sjsu.sidmishraw.framework.pipeline.filters
 * File: Consumer.java
 * 
 * @author sidmishraw
 *         Last modified: Apr 12, 2017 9:07:27 AM
 */
package org.sjsu.sidmishraw.framework.pipeline.filters;

import org.sjsu.sidmishraw.framework.pipeline.core.Filter;
import org.sjsu.sidmishraw.framework.pipeline.core.Message;
import org.sjsu.sidmishraw.framework.pipeline.core.Pipe;

/**
 * @author sidmishraw
 *
 *         Qualified Name:
 *         org.sjsu.sidmishraw.framework.pipeline.filters.Consumer
 *
 */
public abstract class Consumer<T> extends Filter<T> {
	
	
	/**
	 * 
	 */
	public Consumer() {}
	
	/**
	 * @param inPipe
	 * @param outPipe
	 */
	public Consumer(Pipe<T> inPipe, Pipe<T> outPipe) {
		super(inPipe, outPipe);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		
		// TODO Auto-generated method stub
		// keep consuming messages till inPipe is empty.
		
	}
	
	public abstract void consume(Message<T> message);
}
