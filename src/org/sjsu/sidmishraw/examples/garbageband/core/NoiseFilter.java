/**
 * Project: PipelineFramework
 * Package: org.sjsu.sidmishraw.examples.garbageband.core
 * File: NoiseFilter.java
 * 
 * @author sidmishraw
 *         Last modified: Apr 12, 2017 1:03:58 PM
 */
package org.sjsu.sidmishraw.examples.garbageband.core;

import org.sjsu.sidmishraw.framework.pipeline.core.Pipe;
import org.sjsu.sidmishraw.framework.pipeline.filters.Tester;

/**
 * @author sidmishraw
 *
 *         Qualified Name:
 *         org.sjsu.sidmishraw.examples.garbageband.core.NoiseFilter
 *
 */
public class NoiseFilter extends Tester<Note> {
	
	
	private Note predecessorNote = null;
	
	// private int counter = 0;
	
	/**
	 * 
	 */
	public NoiseFilter() {
		
		super();
	}
	
	/**
	 * @param inPipe
	 * @param outPipe
	 */
	public NoiseFilter(Pipe<Note> inPipe, Pipe<Note> outPipe) {
		
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
	public boolean test(Note messageContent) {
		
		// System.out.println("Filter: counter = " + counter);
		// this.counter++;
		
		float frequency = messageContent.getFrequency();
		float amplitude = messageContent.getAmplitude();
		// moved over to Duration Filter
		// float duration = messageContent.getDuration();
		
		if (frequency < 20 || frequency > 20000) {
			
			// inaudible frequencies
			System.out.println("NoiseFilter: Frequency is inaudible: frequency=" + frequency);
			
			return false;
		} else if (amplitude < 30 || amplitude > 90) {
			
			// too quiet or too loud
			System.out.println("NoiseFilter: Too quiet or too loud: volume=" + amplitude);
			
			return false;
		}
		// moved over to Duration Filter
		// else if (duration < 500) {
		//
		// // too short duration
		// System.out.println("Filter: Too short of a duration: duration=" +
		// duration);
		//
		// return false;
		// }
		else if (null != this.predecessorNote && (Math.abs(this.predecessorNote.getAmplitude() - amplitude) > 50)) {
			
			// suspicious - might be static or pop
			System.out.println("NoiseFilter: The note is suspicious: amplitude=" + amplitude
					+ " predecessor's amplitude=" + this.predecessorNote.getAmplitude());
			
			return false;
		}
		
		// update the predecessor note
		this.predecessorNote = messageContent;
		
		System.out.println(String.format("NoiseFilter: Accepted Note: frequency=%s amplitude=%s duration=%s",
				messageContent.getFrequency(), messageContent.getAmplitude(), messageContent.getDuration()));
		
		return true;
	}
}
