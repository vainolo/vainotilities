package com.vainolo.utils;

import java.util.Calendar;
import java.util.logging.ConsoleHandler;
import java.util.logging.Filter;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Simple logger that writes the output to the console.
 * @author vainolo
 *
 */
public class MyLogger {
	
	private static Logger logger;
	
	/**
	 * Remove all loghandlers of a given logger.
	 * @param logger 
	 */
	@SuppressWarnings("unused")
	private static void removeAllHandlers(Logger logger) {
		Handler[] lh = logger.getHandlers();
		for (int i = 0; i < lh.length; i++) {
			logger.removeHandler(lh[i]);
		}
		
	}
	
	/**
	 * Set the loggin level of the logger
	 * @param level
	 */
	public void setLoggerLevel(Level level) {
		if(logger == null) 
			createLogger("");
		
		logger.setLevel(level);
	}
	
	/**
	 * Create a singleton logger that logs to the console.
	 * @param name
	 */
	public static void createLogger(String name) {
		Formatter f = new MyFormatter();
		// The console handler is provided by the JDK to log to the
		// console.
		Handler handler = new ConsoleHandler();
		
		// You can change this to many pre-defined handlers
		// Handler handler = new StreamHandler()
		// Handler handler = new FileHandler();
		// Handler handler = new SocketHandler();
		// Handler handler = new MemoryHandler();		
		
		handler.setFormatter(f);
		logger = Logger.getLogger("");
		logger.addHandler(handler);
		logger.setFilter(new MyFilter());
		logger.setLevel(Level.INFO);
	}
	
	public static void log(String s) {
		if(logger == null) 
			createLogger("");
		// There are many ways to call the logger.
		
		// First, the logger can be called with the wanted log level. 
		// logger.log(Level.INFO, s);
		
		// Second, the level is implied in the function call of the logger.
		logger.info(s); // same as logger.log(Leve.INFO, s)
		
		// The logger does a "best effort" to find the source
		// class and source function of the log call. If we know this
		// will be difficult for him, we should add this information
		// in the call.
		// logger.logp(Level.INFO, "MyLogger", "log", s);
		
		// There are many many more ways to call the logger, functions
		// that receive parameter object, array of parameter objects
		// and also a throwable object. Check the javadocs for more.
	}
	
	/**
	 * Convenience method to log entering into a function call. 
	 * @param sourceClass
	 * @param sourceMethod
	 */
	public static void entering(String sourceClass, String sourceMethod) {
		entering(sourceClass, sourceMethod, null);
	}
	
	/**
	 * Convenience method to log entering into a function call.
	 * @param sourceClass
	 * @param sourceMethod
	 * @param params
	 */
	public static void entering(String sourceClass, String sourceMethod, Object[] params) {
		if(logger == null) 
			createLogger("");
		
		logger.entering(sourceClass, sourceMethod, params);
	}
	
	/**
	 * Convenience method to log exiting from a function call.
	 * @param sourceClass
	 * @param sourceMethod
	 */
	public static void exiting(String sourceClass, String sourceMethod) {
		exiting(sourceClass, sourceMethod, null);
	}
	
	/**
	 * Convenience method to log exiting from a function call.
	 * @param sourceClass
	 * @param sourceMethod
	 * @param result
	 */
	public static  void exiting(String sourceClass, String sourceMethod, Object result) {
		if(logger == null)
			createLogger("");
		logger.exiting(sourceClass, sourceMethod, result);
	}
	
	/**
	 * Convenience method to log throwing of a throwable object.
	 * @param sourceClass
	 * @param sourceMethod
	 * @param thrown
	 */
	public static void throwing(String sourceClass, String sourceMethod, Throwable thrown) {
		if(logger == null)
			createLogger("");
		logger.throwing(sourceClass, sourceMethod, thrown);
	}
	
	/**
	 * Log entries can be formatted for output the way we want.
	 * This is done using a log formatter.
	 * 
	 * @author vainolo
	 *
	 */	
	private static class MyFormatter extends SimpleFormatter {

		@Override
		public synchronized String format(LogRecord record) {
			Calendar c = Calendar.getInstance();
			return "["+
				c.get(Calendar.DAY_OF_MONTH) + "/" +
				(c.get(Calendar.MONTH)+1) + "/" +
				c.get(Calendar.YEAR) + "-" +
				c.get(Calendar.HOUR_OF_DAY) + ":" +
				c.get(Calendar.MINUTE) + ":" +
				c.get(Calendar.SECOND) + " " +
				record.getLevel() + "] (" +
				record.getSourceClassName() + "." +
				record.getSourceMethodName() + ") " +
				record.getMessage()+"\n";
		}
	}
	
	private static class MyFilter implements Filter {

		/**
		 * This function checks whether a log entry passes the logging
		 * filter and if so, forwards it to the output handler.
		 * 
		 * @param record Log record to filter.
		 * @see java.util.logging.Filter#isLoggable(java.util.logging.LogRecord)
		 */
		@Override
		public boolean isLoggable(LogRecord record) {
			if(record.getLevel().intValue() >= logger.getLevel().intValue()) 	
				return true;
			else
				return false;
		}
		
	}
}

