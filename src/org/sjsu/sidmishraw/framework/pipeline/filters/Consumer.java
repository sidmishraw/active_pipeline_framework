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
import org.sjsu.sidmishraw.framework.pipeline.errors.NoOutPipeError;

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
	public Consumer() {
		
		super();
	}
	
	/**
	 * @param inPipe
	 * @param outPipe
	 */
	public Consumer(Pipe<T> inPipe, Pipe<T> outPipe) {
		
		super(inPipe, outPipe);
	}
	
	/**
	 * 
	 * @return true or false depending if inPipe is null or not
	 */
	private final boolean isInPipeNull() {
		
		if (null == this.inPipe) {
			
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
		
		System.out.println("Started Consumer: " + this.getClass().getSimpleName());
		
		boolean shutdown = false;
		
		// TODO Auto-generated method stub
		// keep consuming messages till inPipe is empty.
		// do nothing to the messages that have a fail status.
		// stop the thread after receiving the message with `quit:true` flag
		
		while (true) {
			
			if (isInPipeNull() || shutdown) {
				
				break;
			}
			
			// Message<T> readMessage = null;
			// consumer is forced to wait when the inPipe blocks for lack of
			// data
			Message<T> readMessage = this.inPipe.read();
			
			// synchronized (this.inPipe) {
			//
			// while (this.inPipe.getMessageQueue().size() == 0 || null ==
			// (readMessage = this.inPipe.read())) {
			//
			// try {
			//
			// this.inPipe.wait();
			// } catch (InterruptedException e) {
			//
			// e.printStackTrace();
			// }
			// }
			//
			// this.inPipe.notifyAll();
			// }
			
			if (null != readMessage && !readMessage.isFail()) {
				
				this.consume(readMessage.getContent());
			}
			
			if (null == readMessage || readMessage.isQuit()) {
				
				// System.out.println(null != readMessage ? "Quit" : "Null");
				shutdown = true;
			}
			
		}
		
		System.out.println("Shutting down Consumer: " + this.getClass().getSimpleName());
		
		Thread.yield();
	}
	
	/**
	 * 
	 * @param content
	 *            T
	 *            The content of the message that needs to be consumed
	 */
	// The user of the framework doesn't need to know about the Message wrapper
	// used in the framework, since all they are concerned about is the contents
	// of the Message
	public abstract void consume(T content);
}
