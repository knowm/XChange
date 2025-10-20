package org.knowm.xchange.utils;

/**
 * Simple helper to log current XChange version for debugging and CI builds.
 * 
 * Example usage:
 *   ExchangeVersionLogger.printVersion();
 */
public class ExchangeVersionLogger {

    private static final String VERSION = "5.2.3-SNAPSHOT";

    private ExchangeVersionLogger() {
        // Prevent instantiation
    }

    public static void printVersion() {
        System.out.println("XChange Core version: " + VERSION);
    }
}
