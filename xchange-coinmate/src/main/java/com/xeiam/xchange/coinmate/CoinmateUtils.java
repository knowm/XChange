package com.xeiam.xchange.coinmate;

import com.xeiam.xchange.currency.CurrencyPair;

/**
 *
 * @author Martin Stachon
 */
public class CoinmateUtils {

    public static String getPair(CurrencyPair currencyPair) {

        return currencyPair.baseSymbol.toUpperCase() + "_" + currencyPair.counterSymbol.toUpperCase();
    }
    
    public static CurrencyPair getPair(String currencyPair) {
        if ("BTC_USD".equals(currencyPair)) {
            return CurrencyPair.BTC_USD;
        } else {
            return null;
        }
    }

}
