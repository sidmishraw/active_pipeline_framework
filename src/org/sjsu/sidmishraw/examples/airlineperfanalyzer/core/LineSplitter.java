/**
 * Project: PipelineFramework
 * Package: org.sjsu.sidmishraw.examples.airlineperfanalyzer.core
 * File: LineSplitter.java
 * 
 * @author sidmishraw
 *         Last modified: Apr 13, 2017 6:16:23 PM
 */
package org.sjsu.sidmishraw.examples.airlineperfanalyzer.core;

import java.util.ArrayList;
import java.util.List;

import org.sjsu.sidmishraw.framework.pipeline.core.Pipe;
import org.sjsu.sidmishraw.framework.pipeline.filters.Transformer;

/**
 * @author sidmishraw
 *
 *         Qualified Name:
 *         org.sjsu.sidmishraw.examples.airlineperfanalyzer.core.LineSplitter
 *
 */
public class LineSplitter extends Transformer<String[]> {
	
	
	/**
	 * 
	 */
	public LineSplitter() {}
	
	/**
	 * @param inPipe
	 * @param outPipe
	 */
	public LineSplitter(Pipe<String[]> inPipe, Pipe<String[]> outPipe) {
		
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
		
		String[] splitLine = messageContent[0].split(",");
		
		List<String> strings = new ArrayList<>();
		String prev = "";
		
		for (String value : splitLine) {
			
			// handle cases where the city occurs with the state names
			if (value.startsWith("\"")) {
				
				prev = value.substring(1);
			} else if (value.endsWith("\"")) {
				
				prev += "," + value.substring(0, value.length() - 1);
				
				strings.add(prev);
				
				prev = "";
			} else {
				
				strings.add(value);
			}
		}
		
		// return the string array after dumping the elements into a string
		// array
		return strings.toArray(new String[0]);
	}
	
}
