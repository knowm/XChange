package org.knowm.xchange.coinmarketcap.pro.v1;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CmcQuote;
import org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata.CmcTicker;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;

public class CmcAdapter {

  public static Ticker adaptTicker(CmcTicker ticker, CurrencyPair currencyPair) {

    Date timestamp = ticker.getLastUpdated();
    CmcQuote cmcQuote = ticker.getQuote().get(currencyPair.getCounter().getCurrencyCode());
    BigDecimal price = cmcQuote.getPrice();
    BigDecimal volume24h = cmcQuote.getVolume24h();

    return new Ticker.Builder()
        .currencyPair(currencyPair)
        .timestamp(timestamp)
        .open(price)
        .last(price)
        .bid(price)
        .ask(price)
        .high(price)
        .low(price)
        .vwap(price)
        .volume(volume24h)
        .build();
  }

  public static List<Ticker> adaptTickerMap(Map<String, CmcTicker> cmcTickerMap) {

    List<Ticker> tickerList = new ArrayList<>();
    cmcTickerMap.forEach(
        (baseSymbol, cmcTicker) -> {
          cmcTicker
              .getQuote()
              .forEach(
                  (currencySymbol, quote) -> {
                    CurrencyPair pair = new CurrencyPair(cmcTicker.getSymbol(), currencySymbol);
                    tickerList.add(adaptTicker(cmcTicker, pair));
                  });
        });

    return tickerList;
  }

  public static List<Ticker> adaptTickerList(List<CmcTicker> cmcTickerList) {

    List<Ticker> tickerList = new ArrayList<>();
    cmcTickerList.forEach(
        cmcTicker -> {
          cmcTicker
              .getQuote()
              .forEach(
                  (currencySymbol, quote) -> {
                    CurrencyPair pair = new CurrencyPair(cmcTicker.getSymbol(), currencySymbol);
                    tickerList.add(adaptTicker(cmcTicker, pair));
                  });
        });

    return tickerList;
  }
}
