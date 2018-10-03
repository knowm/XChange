package org.knowm.xchange.yobit.dto;

import java.util.Arrays;
import java.util.Collection;
import org.knowm.xchange.currency.CurrencyPair;

public class MultiCurrencyPublicTradesDataRequestParams implements PublicTradesRequestParams {
  public final Collection<CurrencyPair> currencyPairs;

  public MultiCurrencyPublicTradesDataRequestParams(CurrencyPair... currencyPairs) {
    this(Arrays.asList(currencyPairs));
  }

  public MultiCurrencyPublicTradesDataRequestParams(Collection<CurrencyPair> currencyPairs) {
    this.currencyPairs = currencyPairs;
  }

  public Collection<CurrencyPair> getCurrencyPairs() {
    return currencyPairs;
  }
}
