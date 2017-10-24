package org.knowm.xchange.huobi;

/**
 * Delivery dates for future date currencies
 */
public enum FuturesContract {
  ThisWeek("week"), NextWeek("next_week"), Quarter("quarter");

  private final String name;

  /**
   * Private constructor so it cannot be instantiated
   */
  FuturesContract(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
