package org.knowm.xchange.coindirect;

import org.knowm.xchange.currency.CurrencyPair;

public class CoindirectAdapters {
    public static String toSymbol(CurrencyPair pair) {
        return pair.base.getCurrencyCode() + "-" + pair.counter.getCurrencyCode();
    }
}
