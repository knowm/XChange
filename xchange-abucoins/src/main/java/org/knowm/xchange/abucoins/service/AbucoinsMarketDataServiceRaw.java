package org.knowm.xchange.abucoins.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.abucoins.Abucoins;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsDepth;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsTicker;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsTrade;
import org.knowm.xchange.currency.CurrencyPair;

import si.mazi.rescu.RestProxyFactory;

/**
 * @author timmolter
 */
public class AbucoinsMarketDataServiceRaw extends AbucoinsBaseService {

  private final Abucoins abucoins;

  /**
   * Constructor
   *
   * @param exchange
   */
  public AbucoinsMarketDataServiceRaw(Exchange exchange) {

    super(exchange);

    this.abucoins = RestProxyFactory.createProxy(Abucoins.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
  }

  public AbucoinsTicker getAbucoinsTicker(CurrencyPair currencyPair) throws IOException {

    AbucoinsTicker abucoinsTicker = abucoins.getTicker(currencyPair.toString().replace('/','-'));

    return abucoinsTicker;
  }

  public AbucoinsDepth getAbucoinsOrderBook(CurrencyPair currencyPair) throws IOException {

    AbucoinsDepth abucoinsDepth = null;//abucoins.getDepth(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());

    return abucoinsDepth;
  }

  public AbucoinsTrade[] getAbucoinsTrades(CurrencyPair currencyPair, Long since) throws IOException {

    AbucoinsTrade[] trades;

    if (since != null) {
      trades = null;//abucoins.getTradesSince(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode(), since);
    } else { // default to full available trade history
      trades = null;//abucoins.getTrades(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());
    }

    return trades;
  }

}
