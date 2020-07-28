package org.knowm.xchange.bittrex;

/** See https://bittrex.github.io/api/v3 */
public final class BittrexConstants {

  // Sequence header
  public static final String SEQUENCE = "Sequence";

  // Orders direction
  public static final String BUY = "BUY";
  public static final String SELL = "SELL";

  // Order types
  public static final String LIMIT = "LIMIT";
  public static final String MARKET = "MARKET";
  public static final String CEILING_LIMIT = "CEILING_LIMIT";
  public static final String CEILING_MARKET = "CEILING_MARKET";

  // Orders time in force
  public static final String GOOD_TIL_CANCELLED = "GOOD_TIL_CANCELLED";
  public static final String IMMEDIATE_OR_CANCEL = "IMMEDIATE_OR_CANCEL";
  public static final String FILL_OR_KILL = "FILL_OR_KILL";
  public static final String POST_ONLY_GOOD_TIL_CANCELLED = "POST_ONLY_GOOD_TIL_CANCELLED";
  public static final String BUY_NOW = "BUY_NOW";

  // Orders status
  public static final String OPEN = "OPEN";
  public static final String CLOSED = "CLOSED";

  // Currencies status
  public static final String ONLINE = "ONLINE";
  public static final String OFFLINE = "OFFLINE";

  // Batch flags (not documented as of 07/01/2020)
  public static final String POST = "Post";
  public static final String DELETE = "Delete";

  private BittrexConstants() {
    throw new AssertionError();
  }
}
