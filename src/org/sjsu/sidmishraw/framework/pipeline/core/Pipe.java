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
	
	
	private Queue<Message<T>> messageQueue = null;
	
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
	public Message<T> read() {
		
		Message<T> message = this.getMessageQueue().poll();
		
		return message;
	}
	
	/**
	 * 
	 * @param message
	 *            {@link Message}<T>
	 *            Writes the message to the pipe's message queue
	 */
	public void write(Message<T> message) {
		
		this.getMessageQueue().add(message);
	}
}
