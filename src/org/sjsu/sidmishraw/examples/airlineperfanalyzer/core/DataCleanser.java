/**
 * Project: PipelineFramework
 * Package: org.sjsu.sidmishraw.examples.airlineperfanalyzer.core
 * File: DataCleanser.java
 * 
 * @author sidmishraw
 *         Last modified: Apr 15, 2017 5:33:31 PM
 */
package org.sjsu.sidmishraw.examples.airlineperfanalyzer.core;

import org.sjsu.sidmishraw.framework.pipeline.core.Pipe;
import org.sjsu.sidmishraw.framework.pipeline.filters.Tester;

/**
 * @author sidmishraw
 *
 *         Qualified Name:
 *         org.sjsu.sidmishraw.examples.airlineperfanalyzer.core.DataCleanser
 *
 */
public class DataCleanser extends Tester<String[]> {
	
	
	/**
	 * 
	 */
	public DataCleanser() {
		
		super();
	}
	
	/**
	 * @param inPipe
	 * @param outPipe
	 */
	public DataCleanser(Pipe<String[]> inPipe, Pipe<String[]> outPipe) {
		
		super(inPipe, outPipe);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sjsu.sidmishraw.framework.pipeline.filters.Tester#test(java.lang.
	 * Object)
	 */
	@Override
	public boolean test(String[] messageContent) {
		
		// the below logic also removes the header or dirty fields, since they
		// will never have 1 or 0
		// in their fields. If they have we can add one more check on the
		// fields.
		// messageContent String array that has columns 11[index 10], 12[index
		// 11] i.e CANCELLED,
		// DIVERTED set to 1 are to be removed, i.e return false
		// others need to return true
		if (!messageContent[10].equalsIgnoreCase("0") || !messageContent[11].equalsIgnoreCase("0")) {
			
			return false;
		} else {
			
			return true;
		}
	}
	
}
