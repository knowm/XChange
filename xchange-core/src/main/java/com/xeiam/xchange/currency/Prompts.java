package com.xeiam.xchange.currency;

/**
 * Delivery dates for future date currencies
 */
public class Prompts {

	/**
	 * Private constructor so it cannot be instantiated
	 */
	private Prompts() {

	}

	/**
	 * Futures prompt dates
	 */
	public static final String this_week = "this_week"; // "Delivery this Friday",
	public static final String next_week = "next_week"; // "Deivery next Friday",
	public static final String month = "month"; // "Delivery last Friday of this Month",
	public static final String quarter = "quarter"; // "Delivery last friday of this Quater",

}
