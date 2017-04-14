/**
 * Project: PipelineFramework
 * Package: org.sjsu.sidmishraw.examples
 * File: AirlinePerformanceAnalyzer.java
 * 
 * @author sidmishraw
 *         Last modified: Apr 12, 2017 1:06:38 PM
 */
package org.sjsu.sidmishraw.examples;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author sidmishraw
 *
 *         Qualified Name:
 *         org.sjsu.sidmishraw.examples.AirlinePerformanceAnalyzer
 *
 */
public class AirlinePerformanceAnalyzer {
	
	
	/**
	 * 
	 */
	public AirlinePerformanceAnalyzer() {}
	
	public static Map<String, Integer> getTotalDelays(String fileName) {
		
		return null;
	}
	
	public static Map<String, Double> getAverageDelays(String fileName) {
		
		return null;
	}
	
	@SuppressWarnings("unused")
	public static void displayTables(String fileName) {
		
		Map<String, Integer> totals = getTotalDelays(fileName);
		Map<String, Double> averages = getAverageDelays(fileName);
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(new File("resources/ONTIME.csv"))))) {
			
			String line1 = br.readLine();
			String line2 = br.readLine();
			
			System.out.println(line1 + "\n" + line2);
			
			List<String> strings = new ArrayList<>();
			String prev = "";
			
			for (String value : line2.split(",")) {
				
				if (value.startsWith("\"")) {
					
					prev = value.substring(1);
				} else if (value.endsWith("\"")) {
					
					prev += "," + value.substring(0, value.length() - 1);
					strings.add(prev);
					prev = "";
				} else {
					
					strings.add(value);
				}
			}
			
			strings.stream().forEach(System.out::println);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	
}
