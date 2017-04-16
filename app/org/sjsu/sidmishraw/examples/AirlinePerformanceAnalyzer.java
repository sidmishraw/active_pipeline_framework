/**
 * Project: PipelineFramework
 * Package: org.sjsu.sidmishraw.examples
 * File: AirlinePerformanceAnalyzer.java
 * 
 * @author sidmishraw
 *         Last modified: Apr 12, 2017 1:06:38 PM
 */
package org.sjsu.sidmishraw.examples;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.sjsu.sidmishraw.examples.airlineperfanalyzer.core.CSVStream;
import org.sjsu.sidmishraw.examples.airlineperfanalyzer.core.DataCleanser;
import org.sjsu.sidmishraw.examples.airlineperfanalyzer.core.DataCollector;
import org.sjsu.sidmishraw.examples.airlineperfanalyzer.core.LineSplitter;
import org.sjsu.sidmishraw.examples.airlineperfanalyzer.core.Stats;
import org.sjsu.sidmishraw.framework.pipeline.errors.FilterInstantiationException;
import org.sjsu.sidmishraw.framework.pipeline.errors.PipesNotAttachedError;
import org.sjsu.sidmishraw.framework.pipeline.utils.Utils;

/**
 * @author sidmishraw
 *
 *         Qualified Name:
 *         org.sjsu.sidmishraw.examples.AirlinePerformanceAnalyzer
 *
 */
public class AirlinePerformanceAnalyzer {
	
	
	private static String			mostDelayedFlight	= "";
	private static double			mostDelay			= 0;
	private static DecimalFormat	decimalFormatt		= new DecimalFormat("##.00");
	
	/**
	 * 
	 * @param flightString
	 */
	private static String extractCarrierName(String flightString) {
		
		return flightString.split(", ")[0];
	}
	
	/**
	 * 
	 * @param airlineStats
	 * @return table of mappings between airline string and total delays
	 */
	public static Map<String, Integer> getTotalDelays(Map<String, Stats> airlineStats) {
		
		Map<String, Integer> delaysTable = new HashMap<>();
		
		airlineStats.entrySet().stream()
				.forEach(entry -> delaysTable.put(entry.getKey(), entry.getValue().getTotalDelay()));
		
		return delaysTable;
	}
	
	/**
	 * 
	 * @param airlineStats
	 * @return table of mappings between airline string anf avg delays
	 */
	public static Map<String, Double> getAverageDelays(Map<String, Stats> airlineStats) {
		
		Map<String, Double> avgDelaysTable = new HashMap<>();
		
		airlineStats.entrySet().stream().forEach(entry -> avgDelaysTable.put(entry.getKey(),
				((double) entry.getValue().getTotalDelay() / (double) entry.getValue().getNumFlights())));
		
		return avgDelaysTable;
	}
	
	public static void displayTables(Map<String, Stats> airlineStats) {
		
		System.out.println("::Total Delays for flights::");
		
		// stream through the total delays
		// and produce the output
		getTotalDelays(airlineStats).entrySet().stream().forEach(entry -> {
			
			System.out.println(entry.getKey() + " -> " + entry.getValue());
		});
		
		System.out.println("::Average Delays for flights::");
		
		// stream and obtain the average delays
		getAverageDelays(airlineStats).entrySet().stream().forEach(entry -> {
			
			System.out.println(entry.getKey() + " -> " + decimalFormatt.format(entry.getValue()));
			
			if (mostDelay < entry.getValue()) {
				
				mostDelayedFlight = entry.getKey();
				mostDelay = entry.getValue();
			}
		});
		
		System.out.println("Worst carrier is: " + extractCarrierName(mostDelayedFlight) + " with average delay of: "
				+ decimalFormatt.format(mostDelay));
	}
	
	/**
	 * @param args
	 * @throws FilterInstantiationException
	 * @throws PipesNotAttachedError
	 * @throws InterruptedException
	 */
	public static void main(String[] args)
			throws FilterInstantiationException, PipesNotAttachedError, InterruptedException {
		
		// create the filters
		CSVStream csvStream = (CSVStream) Utils.create(CSVStream.class);
		LineSplitter lineSplitter = (LineSplitter) Utils.create(LineSplitter.class);
		DataCleanser dataCleanser = (DataCleanser) Utils.create(DataCleanser.class);
		DataCollector dataCollector = (DataCollector) Utils.create(DataCollector.class);
		
		// connect them to each other to form the pipeline
		csvStream.connectOut(lineSplitter);
		lineSplitter.connectOut(dataCleanser);
		dataCleanser.connectOut(dataCollector);
		
		// can set the source to Stream out of for the CSVStream before
		// starting, changing it midway might change the data in the pipeline
		csvStream.setFileName("resources/ONTIME.csv");
		
		Utils.start(csvStream, lineSplitter, dataCleanser, dataCollector);
		
		Utils.waitFor(csvStream, lineSplitter, dataCleanser, dataCollector);
		
		Map<String, Stats> airlineStats = dataCollector.getAirlineStatsTable();
		
		// airlineStats.values().stream().forEach(System.out::println);
		displayTables(airlineStats);
	}
	
}
