/**
 * Project: PipelineFramework
 * Package: org.sjsu.sidmishraw.examples.garbageband.core
 * File: Note.java
 * 
 * @author sidmishraw
 *         Last modified: Apr 12, 2017 12:55:52 PM
 */
package org.sjsu.sidmishraw.examples.garbageband.core;

import java.io.Serializable;

/**
 * @author sidmishraw
 *
 *         Qualified Name: org.sjsu.sidmishraw.examples.garbageband.core.Note
 *
 */
public class Note implements Serializable {
	// Each note has frequency, amplitude, and duration. (These can be floating
	// point numbers.)
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	private float				frequency;
	private float				amplitude;
	private float				duration;
	
	/**
	 * 
	 */
	public Note() {}
	
	/**
	 * @param frequency
	 * @param amplitude
	 * @param duration
	 */
	public Note(float frequency, float amplitude, float duration) {
		
		this.frequency = frequency;
		this.amplitude = amplitude;
		this.duration = duration;
	}
	
	/**
	 * @return the frequency
	 */
	public float getFrequency() {
		return this.frequency;
	}
	
	/**
	 * @param frequency
	 *            the frequency to set
	 */
	public void setFrequency(float frequency) {
		this.frequency = frequency;
	}
	
	/**
	 * @return the amplitude
	 */
	public float getAmplitude() {
		return this.amplitude;
	}
	
	/**
	 * @param amplitude
	 *            the amplitude to set
	 */
	public void setAmplitude(float amplitude) {
		this.amplitude = amplitude;
	}
	
	/**
	 * @return the duration
	 */
	public float getDuration() {
		return this.duration;
	}
	
	/**
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(float duration) {
		this.duration = duration;
	}
	
}
