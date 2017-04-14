/**
 * Project: PipelineFramework
 * Package: org.sjsu.sidmishraw.examples.garbageband.core
 * File: DurationFilter.java
 * 
 * @author sidmishraw
 *         Last modified: Apr 13, 2017 4:44:29 PM
 */
package org.sjsu.sidmishraw.examples.garbageband.core;

import org.sjsu.sidmishraw.framework.pipeline.core.Pipe;
import org.sjsu.sidmishraw.framework.pipeline.filters.Tester;

/**
 * @author sidmishraw
 *
 *         Qualified Name:
 *         org.sjsu.sidmishraw.examples.garbageband.core.DurationFilter
 *
 */
public class DurationFilter extends Tester<Note> {
	
	
	/**
	 * 
	 */
	public DurationFilter() {
		
		super();
	}
	
	/**
	 * @param inPipe
	 * @param outPipe
	 */
	public DurationFilter(Pipe<Note> inPipe, Pipe<Note> outPipe) {
		super(inPipe, outPipe);
		// TODO Auto-generated constructor stub
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sjsu.sidmishraw.framework.pipeline.filters.Tester#test(java.lang.
	 * Object)
	 */
	@Override
	public boolean test(Note messageContent) {
		
		float duration = messageContent.getDuration();
		
		if (duration < 500) {
			
			// too short duration
			System.out.println("DurationFilter: Too short of a duration: duration=" + duration);
			
			return false;
		}
		
		System.out.println(String.format("DurationFilter: Accepted Note: frequency=%s amplitude=%s duration=%s",
				messageContent.getFrequency(), messageContent.getAmplitude(), messageContent.getDuration()));
		
		return true;
	}
	
}
