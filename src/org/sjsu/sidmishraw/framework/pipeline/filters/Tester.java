/**
 * Project: PipelineFramework
 * Package: org.sjsu.sidmishraw.framework.pipeline.filters
 * File: Tester.java
 * 
 * @author sidmishraw
 *         Last modified: Apr 12, 2017 9:09:10 AM
 */
package org.sjsu.sidmishraw.framework.pipeline.filters;

import org.sjsu.sidmishraw.framework.pipeline.core.Filter;
import org.sjsu.sidmishraw.framework.pipeline.core.Pipe;

/**
 * @author sidmishraw
 *
 *         Qualified Name: org.sjsu.sidmishraw.framework.pipeline.filters.Tester
 *
 */
public abstract class Tester<T> extends Filter<T> {
	
	
	/**
	 * 
	 */
	public Tester() {}
	
	/**
	 * @param inPipe
	 * @param outPipe
	 */
	public Tester(Pipe<T> inPipe, Pipe<T> outPipe) {
		
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
		// tests the messages from the input pipe
		// writes the result into the outPipe if the result is `true`
		// else changes the `fail` flag of the message to true and then writes it to the 
		// outPipe. If it receives a message that already has a `fail:true` flag, passes
		// on the message without modification.
	}
	
	/**
	 * 
	 * @param messageContent
	 *            T
	 *            The content of the message to be tested
	 * @return result {@link Boolean}
	 *         true for accepted, false for rejected
	 */
	public abstract boolean test(T messageContent);
}
