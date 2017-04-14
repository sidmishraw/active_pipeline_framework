/**
 * Project: PipelineFramework
 * Package: org.sjsu.sidmishraw.examples.garbageband.core
 * File: Amplifier.java
 * 
 * @author sidmishraw
 *         Last modified: Apr 12, 2017 12:58:31 PM
 */
package org.sjsu.sidmishraw.examples.garbageband.core;

import java.util.Random;

import org.sjsu.sidmishraw.framework.pipeline.core.Pipe;
import org.sjsu.sidmishraw.framework.pipeline.filters.Transformer;

/**
 * @author sidmishraw
 *
 *         Qualified Name:
 *         org.sjsu.sidmishraw.examples.garbageband.core.Amplifier
 *
 */
public class Amplifier extends Transformer<Note> {
	
	
	/**
	 * 
	 */
	public Amplifier() {
		
		super();
	}
	
	/**
	 * @param inPipe
	 * @param outPipe
	 */
	public Amplifier(Pipe<Note> inPipe, Pipe<Note> outPipe) {
		
		super(inPipe, outPipe);
	}
	
	/**
	 * Modifies the amplitude depending upon the random numbers
	 * 
	 * @param amplitude
	 *            {@link Float}
	 * @return amplitude {@link Float}
	 */
	private final float modifyVolume(float amplitude) {
		
		Random random = new Random(1234);
		
		if ((random.nextFloat() * 100) % 2 == 0) {
			
			// increase amplitude for even
			amplitude += 10;
		} else {
			
			// decrease amplitude for odd
			amplitude -= 10;
		}
		
		return amplitude;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sjsu.sidmishraw.framework.pipeline.filters.Transformer#transform(java
	 * .lang.Object)
	 */
	@Override
	public Note transform(Note messageContent) {
		
		// volume depends on the amplitude
		float amplitude = modifyVolume(messageContent.getAmplitude());
		
		System.out.println("Amplification: Amplified from old amplitude=" + messageContent.getAmplitude()
				+ " to new amplitude=" + amplitude);
		
		messageContent.setAmplitude(amplitude);
		
		return messageContent;
	}
}
