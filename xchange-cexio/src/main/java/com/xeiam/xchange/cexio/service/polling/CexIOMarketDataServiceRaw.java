package com.xeiam.xchange.cexio.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
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
   * Initialize common properties from the exchange specification
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  public CexIOMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.cexio = RestProxyFactory.createProxy(CexIO.class, exchangeSpecification.getSslUri());
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
    }
    else { // default to full available trade history
      trades = cexio.getTrades(currencyPair.baseSymbol, currencyPair.counterSymbol);
    }

    return trades;
  }

}
