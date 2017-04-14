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
import org.sjsu.sidmishraw.examples.garbageband.core.DurationFilter;
import org.sjsu.sidmishraw.examples.garbageband.core.NoiseFilter;
import org.sjsu.sidmishraw.examples.garbageband.core.Player;
import org.sjsu.sidmishraw.framework.pipeline.errors.FilterInstantiationException;
import org.sjsu.sidmishraw.framework.pipeline.errors.PipesNotAttachedError;
import org.sjsu.sidmishraw.framework.pipeline.utils.Utils;

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
	 * @throws PipesNotAttachedError
	 * @throws InterruptedException
	 * @throws FilterInstantiationException
	 */
	public static void main(String[] args)
			throws PipesNotAttachedError, InterruptedException, FilterInstantiationException {
		
		// // make the pipes
		// Pipe<Note> composerAmplifierPipe = new Pipe<>();
		// Pipe<Note> ampNoiseFilterPipe = new Pipe<>();
		// Pipe<Note> noiseFilterPlayerPipe = new Pipe<>();
		
		// // Make the Filters
		// DigitalComposer composer = new DigitalComposer(null,
		// composerAmplifierPipe);
		// Amplifier amplifier = new Amplifier(composerAmplifierPipe,
		// ampNoiseFilterPipe);
		// NoiseFilter nFilter = new NoiseFilter(ampNoiseFilterPipe,
		// noiseFilterPlayerPipe);
		// Player musicPlayer = new Player(noiseFilterPlayerPipe, null);
		
		// DigitalComposer composer = new DigitalComposer();
		// Amplifier amplifier = new Amplifier();
		// NoiseFilter nFilter = new NoiseFilter();
		// DurationFilter dFilter = new DurationFilter();
		// Player musicPlayer = new Player();
		
		// using the Utils's create method to create the Filters
		DigitalComposer composer = (DigitalComposer) Utils.create(DigitalComposer.class);
		Amplifier amplifier = (Amplifier) Utils.create(Amplifier.class);
		NoiseFilter nFilter = (NoiseFilter) Utils.create(NoiseFilter.class);
		DurationFilter dFilter = (DurationFilter) Utils.create(DurationFilter.class);
		Player musicPlayer = (Player) Utils.create(Player.class);
		
		// filter to filter connections
		composer.connectOut(amplifier);
		amplifier.connectOut(nFilter);
		nFilter.connectOut(dFilter);
		dFilter.connectOut(musicPlayer);
		
		System.out.println("Starting the GarBage Band application... start dancing.. PepePls!");
		
		// starts the filters
		Utils.start(composer, amplifier, nFilter, dFilter, musicPlayer);
		
		// making the main driver synchronous
		Utils.waitFor(composer, amplifier, nFilter, dFilter, musicPlayer);
		
		System.out.println("Stop dancing!! Music is over~~~");
		
		// composer.startThis();
		// amplifier.startThis();
		// nFilter.startThis();
		// dFilter.startThis();
		// musicPlayer.startThis();
		
		//
		// new Thread(composer).start();
		// new Thread(amplifier).start();
		// new Thread(nFilter).start();
		// new Thread(dFilter).start();
		// new Thread(musicPlayer).start();
	}
	
}
