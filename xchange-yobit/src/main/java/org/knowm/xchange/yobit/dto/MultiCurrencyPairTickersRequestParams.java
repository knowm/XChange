package org.knowm.xchange.yobit.dto;

import org.knowm.xchange.currency.CurrencyPair;

import java.util.Arrays;
import java.util.Collection;

public class MultiCurrencyPairTickersRequestParams implements TickersRequestParams {
    public final Collection<CurrencyPair> currencyPairs;

    public MultiCurrencyPairTickersRequestParams(CurrencyPair... currencyPairs) {
        this(Arrays.asList(currencyPairs));
    }

    public MultiCurrencyPairTickersRequestParams(Collection<CurrencyPair> currencyPairs) {
        this.currencyPairs = currencyPairs;
    }

    public Collection<CurrencyPair> getCurrencyPairs() {
        return currencyPairs;
    }
}
