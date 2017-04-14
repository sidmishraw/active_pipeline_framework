/**
 * Project: PipelineFramework
 * Package: org.sjsu.sidmishraw.examples.garbageband.core
 * File: Player.java
 * 
 * @author sidmishraw
 *         Last modified: Apr 12, 2017 1:03:22 PM
 */
package org.sjsu.sidmishraw.examples.garbageband.core;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;

import org.sjsu.sidmishraw.framework.pipeline.core.Pipe;
import org.sjsu.sidmishraw.framework.pipeline.filters.Consumer;

/**
 * @author sidmishraw
 *
 *         Qualified Name: org.sjsu.sidmishraw.examples.garbageband.core.Player
 *
 */
public class Player extends Consumer<Note> {
	
	
	/**
	 * 
	 */
	public Player() {
		
		super();
	}
	
	/**
	 * @param inPipe
	 * @param outPipe
	 */
	public Player(Pipe<Note> inPipe, Pipe<Note> outPipe) {
		
		super(inPipe, outPipe);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sjsu.sidmishraw.framework.pipeline.filters.Consumer#consume(java.lang
	 * .Object)
	 */
	@Override
	public void consume(Note content) {
		
		System.out.println("Playing: frequency=" + content.getFrequency() + " amplitude=" + content.getAmplitude()
				+ " duration=" + content.getDuration());
		
		// defaulting to a piano channel
		int channel = 0;
		
		// using the Java MIDI library for synthesizing notes based on the Note.
		// following example from
		// http://stackoverflow.com/questions/2064066/does-java-have-built-in-libraries-for-audio-synthesis/2065693#2065693
		try (Synthesizer synthesizer = MidiSystem.getSynthesizer()) {
			
			synthesizer.open();
			
			// noteNumber the MIDI note number, from 0 to 127 (60 = Middle C)
			int noteNumber = (int) ((content.getFrequency() / 10) % 127);
			
			// velocity the speed with which the key was depressed
			int velocity = (int) content.getAmplitude();
			// int velocity = 90;
			
			long duration = (long) content.getDuration();
			
			MidiChannel[] channels = synthesizer.getChannels();
			
			channels[channel].noteOn(noteNumber, velocity);
			
			Thread.sleep(duration);
			
			channels[channel].noteOff(noteNumber);
			
			synthesizer.close();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
}
