package org.knowm.xchange.btcturk.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcturk.dto.marketdata.BTCTurkOHLC;
import org.knowm.xchange.btcturk.dto.marketdata.BTCTurkOrderBook;
import org.knowm.xchange.btcturk.dto.marketdata.BTCTurkTicker;
import org.knowm.xchange.btcturk.dto.marketdata.BTCTurkTrades;
import org.knowm.xchange.currency.CurrencyPair;

/**
 * @author semihunaldi
 * @author mertguner
 */
public class BTCTurkMarketDataServiceRaw extends BTCTurkBaseService {

  public BTCTurkMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public BTCTurkTicker getBTCTurkTicker(CurrencyPair pair) throws IOException {
    return btcTurk.getTicker(pair.toString().replace("/", ""));
  }

  public List<BTCTurkTicker> getBTCTurkTicker() throws IOException {
    return btcTurk.getTicker();
  }

  public List<CurrencyPair> getExchangeSymbols() throws IOException {
    List<CurrencyPair> pairs = new ArrayList<CurrencyPair>();

    for (BTCTurkTicker ticker : getBTCTurkTicker()) {
      pairs.add(ticker.getPair());
    }

    return pairs;
  }

  public BTCTurkOrderBook getBTCTurkOrderBook(CurrencyPair pair) throws IOException {
    return btcTurk.getOrderBook(pair.toString().replace("/", ""));
  }

  public List<BTCTurkTrades> getBTCTurkTrades(CurrencyPair pair, Integer last) throws IOException {
    return btcTurk.getTrades(pair.toString().replace("/", ""), last);
  }

  public List<BTCTurkOHLC> getBTCTurkOHLC(CurrencyPair pair) throws IOException {
    return btcTurk.getOHLC(pair.toString().replace("/", ""));
  }

  public List<BTCTurkOHLC> getBTCTurkOHLC(CurrencyPair pair, Integer last) throws IOException {
    return btcTurk.getOHLC(pair.toString().replace("/", ""), last);
  }
}
