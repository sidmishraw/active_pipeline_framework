/**
 * Project: PipelineFramework
 * Package: org.sjsu.sidmishraw.examples
 * File: GarbageBand.java
 * 
 * @author sidmishraw
 *         Last modified: Apr 12, 2017 1:05:54 PM
 */
package org.sjsu.sidmishraw.examples;

import org.sjsu.sidmishraw.examples.garbageband.core.Amplifier;
import org.sjsu.sidmishraw.examples.garbageband.core.DigitalComposer;
import org.sjsu.sidmishraw.examples.garbageband.core.NoiseFilter;
import org.sjsu.sidmishraw.examples.garbageband.core.Note;
import org.sjsu.sidmishraw.examples.garbageband.core.Player;
import org.sjsu.sidmishraw.framework.pipeline.core.Pipe;

/**
 * @author sidmishraw
 *
 *         Qualified Name: org.sjsu.sidmishraw.examples.GarbageBand
 *
 */
public class GarbageBand {
	
	
	/**
	 * 
	 */
	public GarbageBand() {}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// make the pipes
		Pipe<Note> composerAmplifierPipe = new Pipe<>();
		Pipe<Note> ampNoiseFilterPipe = new Pipe<>();
		Pipe<Note> noiseFilterPlayerPipe = new Pipe<>();
		
		// Make the Filters
		DigitalComposer composer = new DigitalComposer(null, composerAmplifierPipe);
		Amplifier amplifier = new Amplifier(composerAmplifierPipe, ampNoiseFilterPipe);
		NoiseFilter nFilter = new NoiseFilter(ampNoiseFilterPipe, noiseFilterPlayerPipe);
		Player musicPlayer = new Player(noiseFilterPlayerPipe, null);
		
		System.out.println("Starting the GarBage Band application... start dancing.. PepePls!");
		
		new Thread(composer).start();
		new Thread(amplifier).start();
		new Thread(nFilter).start();
		new Thread(musicPlayer).start();
	}
	
}
