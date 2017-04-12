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
import org.sjsu.sidmishraw.framework.pipeline.core.Message;
import org.sjsu.sidmishraw.framework.pipeline.core.Pipe;
import org.sjsu.sidmishraw.framework.pipeline.errors.DisconnectedTransformerError;

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
	
	/**
	 * 
	 * @return true if Transformer is connected to Pipes on both the ends else
	 *         false
	 */
	private final boolean isConnectedPipes() {
		
		if (null == this.inPipe || null == this.outPipe) {
			
			try {
				
				throw new DisconnectedTransformerError("The Transformer is not connected to pipes on both the ends");
			} catch (DisconnectedTransformerError e) {
				
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
		
		boolean shutdown = false;
		
		// TODO Auto-generated method stub
		// keep transforming the messages from inPipe and write to outPipe till
		// inPipe is empty. If it receives a message that already has a
		// `fail:true` flag, passes
		// on the message without modification.
		// Stop the thread after writing the message with `quit:flag` to the
		// outPipe
		
		while (true) {
			
			if (!isConnectedPipes() || shutdown) {
				
				break;
			}
			
			// Message<T> readMessage = null;
			
			// the Transformer blocks till the inPipe has data
			Message<T> readMessage = this.inPipe.read();
			
			// synchronized (this.inPipe) {
			//
			// // block and wait on the inPipe till it is filled with new
			// // messages to read
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
			// // after reading the value successfully, notifyAll other threads
			// // waiting on the
			// // Pipe
			// this.inPipe.notifyAll();
			// }
			
			if (!readMessage.isFail()) {
				
				T transformedMessageContent = transform(readMessage.getContent());
				
				readMessage.setContent(transformedMessageContent);
			}
			
			if (readMessage.isQuit()) {
				
				shutdown = true;
			}
			
			// write to the outPipe after obtaining lock while preventing
			// others to read
			// from the outPipe
			
			// synchronized (this.outPipe) {
			//
			// this.outPipe.write(readMessage);
			//
			// this.outPipe.notifyAll();
			// }
			
			this.outPipe.write(readMessage);
		}
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
