package org.knowm.xchange.coindeal;

import org.knowm.xchange.currency.CurrencyPair;

public final class CoindealUtils {

    public static String currencyPairToString(CurrencyPair currencyPair){
        return currencyPair.toString().replace("/","");

    }
}
