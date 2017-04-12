/**
 * Project: PipelineFramework
 * Package: org.sjsu.sidmishraw.framework.pipeline.filters
 * File: Producer.java
 * 
 * @author sidmishraw
 *         Last modified: Apr 12, 2017 1:06:15 AM
 */
package org.sjsu.sidmishraw.framework.pipeline.filters;

import org.sjsu.sidmishraw.framework.pipeline.core.Filter;
import org.sjsu.sidmishraw.framework.pipeline.core.Pipe;

/**
 * @author sidmishraw
 *
 *         Qualified Name:
 *         org.sjsu.sidmishraw.framework.pipeline.filters.Producer
 *
 */
public abstract class Producer<T> extends Filter<T> {
	
	
	/**
	 * 
	 */
	public Producer() {}
	
	/**
	 * @param inPipe
	 * @param outPipe
	 */
	public Producer(Pipe<T> inPipe, Pipe<T> outPipe) {
		
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
		// keep producing messages and dumping them into the outPipe
		// stop the thread after sending out the `quit:true` flagged message
	}
	
	/**
	 * 
	 * @return messageContent T
	 *         The message content produced by the
	 */
	// The user of the framework doesn't need to know about Message, since it is
	// a wrapper
	// used internally in the framework
	public abstract T produce();
}
