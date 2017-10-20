package org.knowm.xchange.cexio.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.cexio.CexIO;
import org.knowm.xchange.cexio.dto.marketdata.CexIODepth;
import org.knowm.xchange.cexio.dto.marketdata.CexIOTicker;
import org.knowm.xchange.cexio.dto.marketdata.CexIOTrade;
import org.knowm.xchange.currency.CurrencyPair;

import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.RestProxyFactory;

/**
 * @author timmolter
 */
public class CexIOMarketDataServiceRaw extends CexIOBaseService {

  private final CexIO cexio;

  /**
   * Constructor
   *
   * @param exchange
   */
  public CexIOMarketDataServiceRaw(Exchange exchange) {

    super(exchange);

    // allow HTTP connect- and read-timeout to be set per exchange
    ClientConfig rescuConfig = new ClientConfig(); // default rescu config
    int customHttpConnTimeout = exchange.getExchangeSpecification().getHttpConnTimeout();
    if (customHttpConnTimeout > 0) {
      rescuConfig.setHttpConnTimeout(customHttpConnTimeout);
    }
    int customHttpReadTimeout = exchange.getExchangeSpecification().getHttpReadTimeout();
    if (customHttpReadTimeout > 0) {
      rescuConfig.setHttpReadTimeout(customHttpReadTimeout);
    }

    this.cexio = RestProxyFactory.createProxy(CexIO.class, exchange.getExchangeSpecification().getSslUri(), rescuConfig);
  }

  public CexIOTicker getCexIOTicker(CurrencyPair currencyPair) throws IOException {

    CexIOTicker cexIOTicker = cexio.getTicker(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());

    return cexIOTicker;
  }

  public CexIODepth getCexIOOrderBook(CurrencyPair currencyPair) throws IOException {

    CexIODepth cexIODepth = cexio.getDepth(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());

    return cexIODepth;
  }

  public CexIOTrade[] getCexIOTrades(CurrencyPair currencyPair, Long since) throws IOException {

    CexIOTrade[] trades;

    if (since != null) {
      trades = cexio.getTradesSince(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode(), since);
    } else { // default to full available trade history
      trades = cexio.getTrades(currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode());
    }

    return trades;
  }

}
