package com.xeiam.xchange.lakebtc;

import com.xeiam.xchange.currency.CurrencyPair;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * User: cristian.lucaci
 * Date: 10/3/2014
 * Time: 5:19 PM
 */
public class LakeBTCUtil {

    private static long generatedId = 1;
    private static long lastNonce = 0l;

    /**
     * private Constructor
     */
    private LakeBTCUtil() {

    }

    public synchronized static long getNonce() {
        long newNonce = System.currentTimeMillis() * 1000;
        while (newNonce == lastNonce) {
            newNonce++;
        }
        lastNonce = newNonce;
        return newNonce;
    }

    public static long getGeneratedId() {
        return generatedId++;
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    /**
     * @deprecated scales of BTCCNY, LTCCNY, LTCBTC are different.
     */
    @Deprecated
    public static BigDecimal truncateAmount(BigDecimal value) {
        return value.setScale(3, RoundingMode.FLOOR).stripTrailingZeros();
    }

    public static String toPairString(CurrencyPair currencyPair) {
        return currencyPair.baseSymbol.toLowerCase();
    }
}
