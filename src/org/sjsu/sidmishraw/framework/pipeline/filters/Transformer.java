/**
 * Project: PipelineFramework
 * Package: org.sjsu.sidmishraw.framework.pipeline.filters
 * File: Transformer.java
 * 
 * @author sidmishraw
 *         Last modified: Apr 12, 2017 9:39:10 AM
 */
package org.sjsu.sidmishraw.framework.pipeline.filters;

import org.sjsu.sidmishraw.framework.pipeline.core.Filter;
import org.sjsu.sidmishraw.framework.pipeline.core.Pipe;

/**
 * @author sidmishraw
 *
 *         Qualified Name:
 *         org.sjsu.sidmishraw.framework.pipeline.filters.Transformer
 *
 */
public abstract class Transformer<T> extends Filter<T> {
	
	
	/**
	 * 
	 */
	public Transformer() {}
	
	/**
	 * @param inPipe
	 * @param outPipe
	 */
	public Transformer(Pipe<T> inPipe, Pipe<T> outPipe) {
		
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
		// keep transforming the messages from inPipe and write to outPipe till
		// inPipe is empty. If it receives a message that already has a
		// `fail:true` flag, passes
		// on the message without modification.
		// Stop the thread after writing the message with `quit:flag` to the
		// outPipe
	}
	
	/**
	 * 
	 * @param messageContent
	 *            T
	 *            The message content that needs to be transformed
	 * @return transformedMessageContent T
	 *         The message content after transformation
	 */
	public abstract T transform(T messageContent);
}
