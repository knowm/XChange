package com.xeiam.xchange.cexio.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.cexio.CexIO;
import com.xeiam.xchange.cexio.dto.marketdata.CexIODepth;
import com.xeiam.xchange.cexio.dto.marketdata.CexIOTicker;
import com.xeiam.xchange.cexio.dto.marketdata.CexIOTrade;
import com.xeiam.xchange.currency.CurrencyPair;

/**
 * @author timmolter
 */
public class CexIOMarketDataServiceRaw extends CexIOBasePollingService {

  private final CexIO cexio;

  /**
   * Constructor
   *
   * @param exchange
   */
  public CexIOMarketDataServiceRaw(Exchange exchange) {

    super(exchange);

    this.cexio = RestProxyFactory.createProxy(CexIO.class, exchange.getExchangeSpecification().getSslUri());
  }

  public CexIOTicker getCexIOTicker(CurrencyPair currencyPair) throws IOException {

    CexIOTicker cexIOTicker = cexio.getTicker(currencyPair.baseSymbol, currencyPair.counterSymbol);

    return cexIOTicker;
  }

  public CexIODepth getCexIOOrderBook(CurrencyPair currencyPair) throws IOException {

    CexIODepth cexIODepth = cexio.getDepth(currencyPair.baseSymbol, currencyPair.counterSymbol);

    return cexIODepth;
  }

  public CexIOTrade[] getCexIOTrades(CurrencyPair currencyPair, Long since) throws IOException {

    CexIOTrade[] trades;

    if (since != null) {
      trades = cexio.getTradesSince(currencyPair.baseSymbol, currencyPair.counterSymbol, since);
    } else { // default to full available trade history
      trades = cexio.getTrades(currencyPair.baseSymbol, currencyPair.counterSymbol);
    }

    return trades;
  }

}
