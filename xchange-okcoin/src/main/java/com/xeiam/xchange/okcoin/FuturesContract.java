package com.xeiam.xchange.okcoin;

/**
 * Delivery dates for future date currencies
 */
public enum FuturesContract {
  ThisWeek("this_week"), NextWeek("next_week"), Month("month"), Quarter("quarter");

  private final String name;

  /**
   * Private constructor so it cannot be instantiated
   */
  private FuturesContract(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
