/**
 * Project: PipelineFramework
 * Package: org.sjsu.sidmishraw.examples.airlineperfanalyzer.core
 * File: DataCollector.java
 * 
 * @author sidmishraw
 *         Last modified: Apr 15, 2017 5:38:58 PM
 */
package org.sjsu.sidmishraw.examples.airlineperfanalyzer.core;

import java.util.HashMap;
import java.util.Map;

import org.sjsu.sidmishraw.framework.pipeline.core.Pipe;
import org.sjsu.sidmishraw.framework.pipeline.filters.Consumer;

/**
 * @author sidmishraw
 *
 *         Qualified Name:
 *         org.sjsu.sidmishraw.examples.airlineperfanalyzer.core.DataCollector
 *
 */
public class DataCollector extends Consumer<String[]> {
	
	
	private Map<String, Stats> airlineStatsTable = null;
	
	/**
	 * 
	 */
	public DataCollector() {
		
		super();
		
		if (null == this.airlineStatsTable) {
			
			airlineStatsTable = new HashMap<>();
		}
	}
	
	/**
	 * @param inPipe
	 * @param outPipe
	 */
	public DataCollector(Pipe<String[]> inPipe, Pipe<String[]> outPipe) {
		
		super(inPipe, outPipe);
		
		if (null == this.airlineStatsTable) {
			
			airlineStatsTable = new HashMap<>();
		}
	}
	
	/**
	 * Joins the columns of the string content array into one string
	 * 
	 * @param content
	 * @param startColumnIndex
	 * @param stopColumnIndex
	 * @return String
	 */
	private String join(String[] content, int startColumnIndex, int stopColumnIndex) {
		
		StringBuffer sBuffer = new StringBuffer();
		
		for (int i = startColumnIndex; i <= stopColumnIndex; i++) {
			
			sBuffer.append(content[i]).append(", ");
		}
		
		sBuffer.delete(sBuffer.length() - 2, sBuffer.length());
		
		return sBuffer.toString();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sjsu.sidmishraw.framework.pipeline.filters.Consumer#consume(java.lang
	 * .Object)
	 */
	@Override
	public void consume(String[] content) {
		
		System.out.println("Consumed: " + join(content, 0, 11));
		
		// join the COLUMNS CARRIER, FL_NUM, ORIGIN, ORIGIN_CITY_NAME, DEST,
		// DEST_CITY_NAME
		// to make the table key string
		String tableKey = join(content, 0, 0);
		
		// the total delay is defined as the sum of the departure delay and
		// arrival delays
		int departureDelay = Integer.parseInt(content[8]);
		int arrivalDelay = Integer.parseInt(content[9]);
		int totalDelay = departureDelay + arrivalDelay;
		
		// the current stats for the particular airline table key
		Stats currentStats = this.airlineStatsTable.get(tableKey);
		
		if (null == currentStats) {
			
			this.airlineStatsTable.put(tableKey, new Stats(totalDelay, 1));
		} else {
			
			currentStats.incrementNumFlights();
			currentStats.incrementTotalDelay(departureDelay, arrivalDelay);
		}
	}
	
	/**
	 * @return the airlineStatsTable
	 */
	public Map<String, Stats> getAirlineStatsTable() {
		return this.airlineStatsTable;
	}
	
	/**
	 * @param airlineStatsTable
	 *            the airlineStatsTable to set
	 */
	public void setAirlineStatsTable(Map<String, Stats> airlineStatsTable) {
		this.airlineStatsTable = airlineStatsTable;
	}
	
}
