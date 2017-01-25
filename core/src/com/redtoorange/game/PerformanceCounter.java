package com.redtoorange.game;

/**
 * ${FILE_NAME}.java - Description
 *
 * @author
 * @version 23/Jan/2017
 */
public class PerformanceCounter {
	String tag;
	long startTime = 0;
	long endTime = 0;

	public PerformanceCounter( String tag ) {
		this.tag = tag;
	}

	public void start( ) {
		startTime = System.currentTimeMillis( );
		endTime = 0;
	}

	public void stop( ) {
		endTime = getCurrent( );
		startTime = 0;
	}

	public long getCurrent( ) {
		return System.currentTimeMillis( ) - startTime;
	}

	@Override
	public String toString( ) {
		return tag + " " + getCurrent( );
	}
}
