package org.knowm.xchange.bl3p;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;

import java.math.BigDecimal;

public class Bl3pUtils {
    private Bl3pUtils() {}

    private static final BigDecimal SATOSHI = new BigDecimal(1e8);
    private static final BigDecimal EUROSHI = new BigDecimal(1e5);

    public static final BigDecimal fromSatoshi(BigDecimal bd) {
        return bd.divide(SATOSHI);
    }

    public static final BigDecimal fromEuroshi(BigDecimal bd) {
        return bd.divide(EUROSHI);
    }

    public static String toPairString(CurrencyPair currencyPair) {
        return currencyPair.base.getCurrencyCode() + currencyPair.counter.getCurrencyCode();
    }

    public static Order.OrderType fromBl3pStatus(String bl3pStatus) {
        return bl3pStatus.equals("bid") ? Order.OrderType.BID : Order.OrderType.ASK;
    }
}
