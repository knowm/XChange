package org.knowm.xchange.bitmex;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.bitmex.dto.account.BitmexTicker;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;

public class BitmexAdapters {

  public static ExchangeMetaData adaptMetaData(List<BitmexTicker> tickers) {
    Map<CurrencyPair, CurrencyPairMetaData> currencyPairs = new HashMap<>();
    Map<Currency, CurrencyMetaData> currencies = new HashMap<>();

    for (BitmexTicker ticker : tickers) {
      //positionCurrency='XMR', quoteCurrency='XBT'
      String positionCurrency = ticker.getPositionCurrency();
      String quoteCurrency = ticker.getQuoteCurrency();
      if (positionCurrency != null && !positionCurrency.isEmpty() && quoteCurrency != null && !quoteCurrency.isEmpty()) {
        CurrencyPair pair = new CurrencyPair(positionCurrency, quoteCurrency);
        currencyPairs.put(pair, null);
      }
    }
    return new ExchangeMetaData(currencyPairs, currencies, null, null, false);
  }
}