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

}
