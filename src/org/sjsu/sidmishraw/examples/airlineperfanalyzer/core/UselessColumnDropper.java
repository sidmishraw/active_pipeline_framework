/**
 * Project: PipelineFramework
 * Package: org.sjsu.sidmishraw.examples.airlineperfanalyzer.core
 * File: UselessColumnDropper.java
 * 
 * @author sidmishraw
 *         Last modified: Apr 13, 2017 6:18:38 PM
 */
package org.sjsu.sidmishraw.examples.airlineperfanalyzer.core;

import org.sjsu.sidmishraw.framework.pipeline.core.Pipe;
import org.sjsu.sidmishraw.framework.pipeline.filters.Transformer;

/**
 * @author sidmishraw
 *
 *         Qualified Name:
 *         org.sjsu.sidmishraw.examples.airlineperfanalyzer.core.
 *         UselessColumnDropper
 *
 */
public class UselessColumnDropper extends Transformer<String[]> {
	
	
	/**
	 * 
	 */
	public UselessColumnDropper() {}
	
	/**
	 * @param inPipe
	 * @param outPipe
	 */
	public UselessColumnDropper(Pipe<String[]> inPipe, Pipe<String[]> outPipe) {
		
		super(inPipe, outPipe);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sjsu.sidmishraw.framework.pipeline.filters.Transformer#transform(java
	 * .lang.Object)
	 */
	@Override
	public String[] transform(String[] messageContent) {
		
		String[] cleansedMessageContent = dropUselessColumns(messageContent);
		
		return cleansedMessageContent;
	}
	
	/**
	 * @param messageContent
	 * @return String[] - The array holding only the useful columns, the columns
	 *         that were are concerned about
	 */
	private String[] dropUselessColumns(String[] messageContent) {
		
		return null;
	}
	
}
