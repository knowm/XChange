package org.knowm.xchange.yobit.dto;

import java.util.Arrays;
import java.util.Collection;
import javax.annotation.Nullable;
import org.knowm.xchange.currency.CurrencyPair;

public class MultiCurrencyOrderBooksRequestParams implements OrderBooksRequestParam {
  @Nullable public final Integer desiredDepth;
  public final Collection<CurrencyPair> currencyPairs;

  public MultiCurrencyOrderBooksRequestParams(CurrencyPair... currencyPairs) {
    this(null, currencyPairs);
  }

  public MultiCurrencyOrderBooksRequestParams(Integer desiredDepth, CurrencyPair... currencyPairs) {
    this(desiredDepth, Arrays.asList(currencyPairs));
  }

  public MultiCurrencyOrderBooksRequestParams(Collection<CurrencyPair> currencyPairs) {
    this(null, currencyPairs);
  }

  public MultiCurrencyOrderBooksRequestParams(
      Integer desiredDepth, Collection<CurrencyPair> currencyPairs) {
    this.currencyPairs = currencyPairs;
    this.desiredDepth = desiredDepth;
  }

  public Collection<CurrencyPair> getCurrencyPairs() {
    return currencyPairs;
  }
}
