package org.xchange.bitz;

import org.knowm.xchange.currency.CurrencyPair;

public class BitZUtils {

	// TODO: Add Test
	public static String toPairString(CurrencyPair currency) {
		return String.format("%s_%s", 
				currency.base.getCurrencyCode().toLowerCase(), 
				currency.counter.getCurrencyCode().toLowerCase());
	}
	
	// TODO: Add Test
	public static String toNonceString(long value) {
	  return String.format("%06d", Math.abs(value) % 100000);
	}
}
