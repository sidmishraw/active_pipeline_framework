/**
 * Project: PipelineFramework
 * Package: org.sjsu.sidmishraw.framework.pipeline.utils
 * File: Utils.java
 * 
 * @author sidmishraw
 *         Last modified: Apr 14, 2017 11:05:29 AM
 */
package org.sjsu.sidmishraw.framework.pipeline.utils;

import java.lang.reflect.InvocationTargetException;

import org.sjsu.sidmishraw.framework.pipeline.core.Filter;
import org.sjsu.sidmishraw.framework.pipeline.errors.FilterInstantiationException;
import org.sjsu.sidmishraw.framework.pipeline.errors.PipesNotAttachedError;

/**
 * @author sidmishraw
 *
 *         Qualified Name: org.sjsu.sidmishraw.framework.pipeline.utils.Utils
 *
 */
public class Utils {
	
	
	private static Utils utils = null;
	
	private Utils() {}
	
	/**
	 * Gets the singleton instance of Utils
	 * 
	 * @return {@link Utils}
	 */
	public static Utils getInstance() {
		
		if (null == utils) {
			
			utils = new Utils();
		}
		
		return utils;
	}
	
	/**
	 * Starts the filters by calling the startThis() on them
	 * 
	 * @param filters
	 * @throws PipesNotAttachedError
	 */
	@SafeVarargs
	public static void start(Filter<?>... filters) throws PipesNotAttachedError {
		
		for (Filter<?> filter : filters) {
			
			filter.startThis();
		}
	}
	
	/**
	 * Calls join on the filters so that the calling thread waits till the
	 * filters die.
	 * 
	 * @param filters
	 * @throws InterruptedException
	 */
	@SafeVarargs
	public static void waitFor(Filter<?>... filters) throws InterruptedException {
		
		for (Filter<?> filter : filters) {
			
			if (filter.isAlive()) {
				
				filter.waitForMe();
			}
		}
	}
	
	/**
	 * Creates new instances of the Filters that are passed to it
	 * 
	 * @param clasz
	 * @return {@link Filter}
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	public static Filter<?> create(Class<? extends Filter<?>> clasz) throws FilterInstantiationException {
		
		// get the default constructor and create a new instance from it
		try {
			
			return clasz.cast(clasz.getConstructor().newInstance());
		} catch (Exception e) {
			
			throw new FilterInstantiationException("Couldn't create new Filter of type:" + clasz.getName());
		}
	}
}
