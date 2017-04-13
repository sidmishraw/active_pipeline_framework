/**
 * Project: PipelineFramework
 * Package: org.sjsu.sidmishraw.examples.airlineperfanalyzer.core
 * File: CSVStream.java
 * 
 * @author sidmishraw
 *         Last modified: Apr 12, 2017 11:55:49 PM
 */
package org.sjsu.sidmishraw.examples.airlineperfanalyzer.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

import org.sjsu.sidmishraw.framework.pipeline.core.Pipe;
import org.sjsu.sidmishraw.framework.pipeline.filters.Producer;

/**
 * @author sidmishraw
 *
 *         Qualified Name:
 *         org.sjsu.sidmishraw.examples.airlineperfanalyzer.core.CSVStream
 *
 */
public class CSVStream extends Producer<String[]> {
	
	
	// name of the resource CSV needed for this example
	private final String	ONTIME_CSV	= "ONTIME.csv";
	
	private Queue<String[]>	csvStrings	= null;
	
	/**
	 * @param inPipe
	 * @param outPipe
	 */
	public CSVStream(Pipe<String[]> inPipe, Pipe<String[]> outPipe) {
		
		super(inPipe, outPipe);
		
		if (null == csvStrings) {
			
			csvStrings = new LinkedList<>();
		}
		
		extractLinesFromCSV();
	}
	
	/**
	 * Extracts the lines from the CSV and populates them into the csvStrings
	 * Queue which is then produces the String arrays after splitting on the ','
	 * delimiter of the CSV
	 * for the pipeline
	 */
	private void extractLinesFromCSV() {
		
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(new File(this.ONTIME_CSV))))) {
			
			String line = null;
			
			while (null != (line = br.readLine())) {
				
				this.csvStrings.add(line.split(","));
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sjsu.sidmishraw.framework.pipeline.filters.Producer#produce()
	 */
	@Override
	public String[] produce() {
		
		String[] line = csvStrings.poll();
		
		// last line of the CSV has been read,
		// send out the `quit:true` message
		if (csvStrings.size() == 0) {
			
			this.shutdown(true);
		}
		
		return line;
	}
}
