package org.knowm.xchange.bitfinex.v2;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.stream.Collectors;
import org.knowm.xchange.bitfinex.v2.dto.marketdata.BitfinexTicker;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class BitfinexAdapters {

  public static final Logger log = LoggerFactory.getLogger(BitfinexAdapters.class);

  private BitfinexAdapters() {}

  public static String adaptCurrencyPairsToTickersParam(Collection<CurrencyPair> currencyPairs) {
    return currencyPairs
        .stream()
        .map(currencyPair -> "t" + currencyPair.base + currencyPair.counter)
        .collect(Collectors.joining(","));
  }

  public static Ticker adaptTicker(BitfinexTicker bitfinexTicker) {

    BigDecimal last = bitfinexTicker.getLastPrice();
    BigDecimal bid = bitfinexTicker.getBid();
    BigDecimal ask = bitfinexTicker.getAsk();
    BigDecimal high = bitfinexTicker.getHigh();
    BigDecimal low = bitfinexTicker.getLow();
    BigDecimal volume = bitfinexTicker.getVolume();

    CurrencyPair currencyPair =
        CurrencyPairDeserializer.getCurrencyPairFromString(bitfinexTicker.getSymbol().substring(1));

    return new Ticker.Builder()
        .currencyPair(currencyPair)
        .last(last)
        .bid(bid)
        .ask(ask)
        .high(high)
        .low(low)
        .volume(volume)
        .build();
  }
}
