/**
 * Project: PipelineFramework
 * Package: org.sjsu.sidmishraw.examples.airlineperfanalyzer.core
 * File: Stats.java
 * 
 * @author sidmishraw
 *         Last modified: Apr 15, 2017 5:49:37 PM
 */
package org.sjsu.sidmishraw.examples.airlineperfanalyzer.core;

import java.io.Serializable;

/**
 * @author sidmishraw
 *
 *         Qualified Name:
 *         org.sjsu.sidmishraw.examples.airlineperfanalyzer.core.Stats
 *
 */
public class Stats implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	private int					totalDelay;
	private int					numFlights;
	
	/**
	 * 
	 */
	public Stats() {
		
		this.totalDelay = 0;
		this.numFlights = 0;
	}
	
	/**
	 * @param totalDelay
	 * @param numFlights
	 */
	public Stats(int totalDelay, int numFlights) {
		
		this.totalDelay = totalDelay;
		this.numFlights = numFlights;
	}
	
	/**
	 * @return the totalDelay
	 */
	public int getTotalDelay() {
		return this.totalDelay;
	}
	
	/**
	 * @param totalDelay
	 *            the totalDelay to set
	 */
	public void setTotalDelay(int totalDelay) {
		this.totalDelay = totalDelay;
	}
	
	/**
	 * @return the numFlights
	 */
	public int getNumFlights() {
		return this.numFlights;
	}
	
	/**
	 * @param numFlights
	 *            the numFlights to set
	 */
	public void setNumFlights(int numFlights) {
		this.numFlights = numFlights;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Stats [totalDelay=" + this.totalDelay + ", numFlights=" + this.numFlights + "]";
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.numFlights;
		result = prime * result + this.totalDelay;
		return result;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Stats other = (Stats) obj;
		if (this.numFlights != other.numFlights) return false;
		if (this.totalDelay != other.totalDelay) return false;
		return true;
	}
	
	public void incrementNumFlights() {
		
		this.numFlights++;
	}
	
	public void incrementTotalDelay(int departureDelay, int arrivalDelay) {
		
		this.totalDelay += departureDelay + arrivalDelay;
	}
}
