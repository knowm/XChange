package org.knowm.xchange.coindirect;

import org.knowm.xchange.currency.CurrencyPair;

public class CoindirectAdapters {
    public static String toSymbol(CurrencyPair pair) {
        return pair.base.getCurrencyCode() + "-" + pair.counter.getCurrencyCode();
    }

    public static CurrencyPair toCurrencyPair(String symbol) {
        int token = symbol.indexOf('-');
        String left = symbol.substring(0, token);
        String right = symbol.substring(token+1);
        return new CurrencyPair(left, right);
    }

    public static void main(String[] args) {
        System.out.println(toCurrencyPair("ETH-BTC"));
    }
}
