/**
 * Project: PipelineFramework
 * Package: org.sjsu.sidmishraw.framework.pipeline.core
 * File: Pipe.java
 * 
 * @author sidmishraw
 *         Last modified: Apr 12, 2017 12:55:56 AM
 */
package org.sjsu.sidmishraw.framework.pipeline.core;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author sidmishraw
 *
 *         Qualified Name: org.sjsu.sidmishraw.framework.pipeline.core.Pipe
 *
 */
public class Pipe<T> {
	
	
	private Queue<Message<T>>	messageQueue	= null;
	
	// after receiving the final `quit:true` message and adding it to the
	// messageQueue
	// the pipes should never block anymore in case they go empty
	// since the system is shutting down
	private boolean				allowBlocking	= true;
	
	/**
	 * 
	 */
	public Pipe() {
		
		if (null == this.messageQueue) {
			
			this.messageQueue = new LinkedList<>();
		}
	}
	
	/**
	 * @param messageQueue
	 */
	public Pipe(Queue<Message<T>> messageQueue) {
		
		this.messageQueue = messageQueue;
	}
	
	/**
	 * @return the messageQueue
	 */
	public Queue<Message<T>> getMessageQueue() {
		
		return this.messageQueue;
	}
	
	/**
	 * @param messageQueue
	 *            the messageQueue to set
	 */
	public void setMessageQueue(Queue<Message<T>> messageQueue) {
		
		this.messageQueue = messageQueue;
	}
	
	/**
	 * 
	 * @return message {@link Message}<T>
	 *         The message read from the messageQueue
	 */
	public synchronized Message<T> read() {
		
		Message<T> message = null;
		
		while (this.messageQueue.size() == 0 || null == (message = this.getMessageQueue().poll())) {
			
			try {
				
				if (!allowBlocking) {
					
					break;
				}
				
				this.wait();
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
		
		this.notifyAll();
		
		return message;
	}
	
	/**
	 * 
	 * @param message
	 *            {@link Message}<T>
	 *            Writes the message to the pipe's message queue
	 */
	public synchronized void write(Message<T> message) {
		
		this.getMessageQueue().add(message);
		
		if (message.isQuit()) {
			
			this.allowBlocking = false;
			
			// System.out.println("NonBlocking");
		}
		
		this.notifyAll();
	}
}
