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
import org.sjsu.sidmishraw.framework.pipeline.core.Message;
import org.sjsu.sidmishraw.framework.pipeline.core.Pipe;
import org.sjsu.sidmishraw.framework.pipeline.errors.NoOutPipeError;

/**
 * @author sidmishraw
 *
 *         Qualified Name:
 *         org.sjsu.sidmishraw.framework.pipeline.filters.Producer
 *
 */
public abstract class Producer<T> extends Filter<T> {
	
	
	private boolean markedForShutdown = false;
	
	/**
	 * 
	 */
	public Producer() {
		
		super();
	}
	
	/**
	 * The inPipe of the Producer can be null, but not the outPipe
	 * 
	 * @param inPipe
	 * @param outPipe
	 */
	public Producer(Pipe<T> inPipe, Pipe<T> outPipe) {
		
		super(inPipe, outPipe);
	}
	
	/**
	 * 
	 * @return true or false depending if outPipe is null or not
	 */
	private final boolean isOutPipeNull() {
		
		if (null == this.outPipe) {
			
			try {
				
				throw new NoOutPipeError("The Producer has no outPipes to write to.");
			} catch (NoOutPipeError e) {
				
				// break out the infinite loop
				return true;
			}
		}
		
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		
		System.out.println("Started Producer");
		
		// flag that denotes that the Producer should shutdown
		boolean shutdown = false;
		
		// TODO Auto-generated method stub
		// keep producing messages and dumping them into the outPipe
		// stop the thread after sending out the `quit:true` flagged message
		while (true) {
			
			if (isOutPipeNull() || shutdown) {
				
				break;
			}
			
			T messageContent = produce();
			Message<T> message = null;
			
			if (this.markedForShutdown) {
				
				// message for quitting
				message = new Message<>(messageContent, true, false);
				shutdown = true;
			} else {
				
				message = new Message<>(messageContent, false, false);
			}
			
			if (null != message) {
				
				// synchronized (this.outPipe) {
				//
				// this.outPipe.write(message);
				//
				// this.outPipe.notifyAll();
				// }
				
				// the outPipe.write is a synchronized method with outPipe being
				// the monitor
				this.outPipe.write(message);
			}
		}
		
		System.out.println("Shutting down Producer");
		Thread.yield();
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
	
	/**
	 * Makes the Producer to send out the quit message and shutdown
	 * To be called from within the produce() ideally or can be called from
	 * outside logic to explicitly
	 * shutdown the Producer
	 * 
	 * @param status
	 */
	public final void shutdown(boolean shutdown) {
		
		this.markedForShutdown = shutdown;
	}
}
