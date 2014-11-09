package com.xeiam.xchange.currency;

/**
 * Delivery dates for future date currencies
 */
public enum FuturesPrompt {
  ThisWeek("this_week"), NextWeek("next_week"), Month("month"), Quarter("quarter");
  
  private final String name;
	/**
	 * Private constructor so it cannot be instantiated
	 */
	private FuturesPrompt(String name) {
	  this.name = name;
	}

  public String getName() {
    return name;
  }
}
