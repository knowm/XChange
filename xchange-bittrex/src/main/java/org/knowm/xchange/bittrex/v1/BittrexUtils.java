package org.knowm.xchange.bittrex.v1;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.xml.bind.DatatypeConverter;

import org.knowm.xchange.currency.CurrencyPair;

/**
 * A central place for shared Bittrex properties
 */
public final class BittrexUtils {

    private static final String TIMEZONE = "UTC";

    /**
     * private Constructor
     */
    private BittrexUtils() {

    }

    public static String toPairString(CurrencyPair currencyPair) {
        return currencyPair.counter.getCurrencyCode().toUpperCase() + "-" + currencyPair.base.getCurrencyCode().toUpperCase();
    }

    public static Date toDate(String date) {
        Calendar cal = DatatypeConverter.parseDateTime(date);
        cal.setTimeZone(TimeZone.getTimeZone(TIMEZONE));
        return cal.getTime();
    }

}