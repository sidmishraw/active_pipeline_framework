/**
 * Project: PipelineFramework
 * Package: org.sjsu.sidmishraw.examples.garbageband.core
 * File: DigitalComposer.java
 * 
 * @author sidmishraw
 *         Last modified: Apr 12, 2017 1:04:37 PM
 */
package org.sjsu.sidmishraw.examples.garbageband.core;

import java.util.Random;

import org.sjsu.sidmishraw.framework.pipeline.core.Pipe;
import org.sjsu.sidmishraw.framework.pipeline.filters.Producer;

/**
 * @author sidmishraw
 *
 *         Qualified Name:
 *         org.sjsu.sidmishraw.examples.garbageband.core.DigitalComposer
 *
 */
public class DigitalComposer extends Producer<Note> {
	
	
	private int noteCount = 0;
	
	/**
	 * 
	 */
	public DigitalComposer() {
		
		super();
	}
	
	/**
	 * @param inPipe
	 * @param outPipe
	 */
	public DigitalComposer(Pipe<Note> inPipe, Pipe<Note> outPipe) {
		
		super(inPipe, outPipe);
	}
	
	/**
	 * @return {@link Float}
	 */
	private final float generateFrequency() {
		
		Random random = new Random();
		
		float frequency = random.nextFloat() * 1000;
		
		return frequency;
	}
	
	/**
	 * @return {@link Float}
	 */
	private final float generateAmplitude() {
		
		Random random = new Random();
		
		float amplitude = random.nextFloat() * 100;
		
		return amplitude;
	}
	
	/**
	 * @return {@link Float}
	 */
	private final float generateDuration() {
		
		Random random = new Random();
		
		float duration = random.nextFloat() * 1000;
		
		return duration;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sjsu.sidmishraw.framework.pipeline.filters.Producer#produce()
	 */
	@Override
	public Note produce() {
		
		float frequency = generateFrequency();
		float amplitude = generateAmplitude();
		float duration = generateDuration();
		
		Note note = new Note(frequency, amplitude, duration);
		
		System.out.println("Composer: Produced note #" + noteCount + " having frequency=" + frequency + " amplitude="
				+ amplitude + " duration=" + duration);
		
		this.noteCount++;
		
		if (noteCount >= 100) {
			
			this.shutdown(true);
		}
		
		return note;
	}
	
}
