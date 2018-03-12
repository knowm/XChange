package org.knowm.xchange.bl3p;

import org.knowm.xchange.currency.CurrencyPair;

import java.math.BigDecimal;

public class Bl3pUtils {
    private Bl3pUtils() {}

    private static final BigDecimal SATOSHI = new BigDecimal(1e8);
    private static final BigDecimal EUROSHI = new BigDecimal(1e5);

    public static final BigDecimal fromSatoshi(BigDecimal bd) {
        return bd.multiply(SATOSHI);
    }

    public static final BigDecimal fromEuroshi(BigDecimal bd) {
        return bd.multiply(EUROSHI);
    }

    public static String toPairString(CurrencyPair currencyPair) {
        return currencyPair.base.getCurrencyCode() + currencyPair.counter.getCurrencyCode();
    }
}
