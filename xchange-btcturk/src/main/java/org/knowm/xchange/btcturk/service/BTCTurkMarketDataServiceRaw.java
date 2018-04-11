package org.knowm.xchange.btcturk.service;

import java.io.IOException;
import javax.annotation.Nullable;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcturk.BTCTurk;
import org.knowm.xchange.btcturk.dto.marketdata.BTCTurkOHLC;
import org.knowm.xchange.btcturk.dto.marketdata.BTCTurkOrderBook;
import org.knowm.xchange.btcturk.dto.marketdata.BTCTurkTicker;
import org.knowm.xchange.btcturk.dto.marketdata.BTCTurkTrade;
import org.knowm.xchange.currency.CurrencyPair;
import si.mazi.rescu.RestProxyFactory;

/** @author semihunaldi */
public class BTCTurkMarketDataServiceRaw extends BTCTurkBaseService {

  private final BTCTurk btcTurk;

  public BTCTurkMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
    this.btcTurk =
        RestProxyFactory.createProxy(
            BTCTurk.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
  }

  public BTCTurkTicker getBTCTurkTicker(@Nullable CurrencyPair pair) throws IOException {
    return btcTurk.getTicker(new BTCTurk.Pair(pair));
  }

  public BTCTurkOrderBook getBTCTurkOrderBook(CurrencyPair pair) throws IOException {
    return btcTurk.getOrderBook(new BTCTurk.Pair(pair));
  }

  public BTCTurkTrade[] getBTCTurkTrades(CurrencyPair pair, @Nullable Integer last)
      throws IOException {
    return btcTurk.getTrades(new BTCTurk.Pair(pair), last);
  }

  public BTCTurkOHLC[] getBTCTurkOHLC(@Nullable CurrencyPair pair, @Nullable Integer last)
      throws IOException {
    return btcTurk.getOHLC(new BTCTurk.Pair(pair), last);
  }
}
