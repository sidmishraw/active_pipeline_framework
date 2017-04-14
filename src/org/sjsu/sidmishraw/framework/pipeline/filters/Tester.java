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
import org.sjsu.sidmishraw.framework.pipeline.core.Message;
import org.sjsu.sidmishraw.framework.pipeline.core.Pipe;
import org.sjsu.sidmishraw.framework.pipeline.errors.DisconnectedTesterError;

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
	public Tester() {
		
		super();
	}
	
	/**
	 * @param inPipe
	 * @param outPipe
	 */
	public Tester(Pipe<T> inPipe, Pipe<T> outPipe) {
		
		super(inPipe, outPipe);
	}
	
	/**
	 * 
	 * @return true if Transformer is connected to Pipes on both the ends else
	 *         false
	 */
	private final boolean isConnectedPipes() {
		
		if (null == this.inPipe || null == this.outPipe) {
			
			try {
				
				throw new DisconnectedTesterError("The Tester is not connected to pipes on both the ends");
			} catch (DisconnectedTesterError e) {
				
				return false;
			}
		}
		
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		
		System.out.println("Started Tester");
		
		// TODO Auto-generated method stub
		// tests the messages from the input pipe
		// writes the result into the outPipe if the result is `true`
		// else changes the `fail` flag of the message to true and then writes
		// it to the
		// outPipe. If it receives a message that already has a `fail:true`
		// flag, passes
		// on the message without modification.
		// Stop the thread after writing the message with `quit:flag` to the
		// outPipe
		boolean shutdown = false;
		
		while (true) {
			
			if (!isConnectedPipes() || shutdown) {
				
				break;
			}
			
			// Message<T> readMessage = null;
			
			// the Tester is blocked till there is data in the inPipe
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
				
				// don't modify the quit message
				// never want to discard the quit message
				if (!readMessage.isQuit() && !test(readMessage.getContent())) {
					
					readMessage.setFail(true);
				}
			}
			
			if (null == readMessage || readMessage.isQuit()) {
				
				// System.out.println(null != readMessage ? "Quit" : "Null");
				
				shutdown = true;
			}
			
			// // write to the outPipe after obtaining lock while preventing
			// // others to read
			// // from the outPipe
			// synchronized (this.outPipe) {
			//
			// this.outPipe.write(readMessage);
			//
			// this.outPipe.notifyAll();
			// }
			if (null != readMessage) {
				
				this.outPipe.write(readMessage);
			}
		}
		
		System.out.println("Shutting down Tester");
		
		Thread.yield();
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
