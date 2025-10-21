package org.knowm.xchange.utils;

/**
 * Simple utility to print current XChange library version.
 * Useful for debugging, build verification, and dependency tracking.
 */
public final class VersionUtils {

  private static final String VERSION = "5.2.3";

  private VersionUtils() {}

  public static void printVersion() {
    System.out.println("XChange version: " + VERSION);
  }

  public static String getVersion() {
    return VERSION;
  }
}
