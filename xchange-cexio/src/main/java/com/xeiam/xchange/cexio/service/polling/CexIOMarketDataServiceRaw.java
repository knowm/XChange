package com.xeiam.xchange.cexio.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.cexio.CexIO;
import com.xeiam.xchange.cexio.dto.marketdata.CexIODepth;
import com.xeiam.xchange.cexio.dto.marketdata.CexIOTicker;
import com.xeiam.xchange.cexio.dto.marketdata.CexIOTrade;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;

/**
 * @author timmolter
 */
public class CexIOMarketDataServiceRaw extends BasePollingExchangeService {

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

  public CexIOTicker getCexIOTicker(String tradableIdentifier, String currency) throws IOException {

    CexIOTicker cexIOTicker = cexio.getTicker(tradableIdentifier, currency);

    return cexIOTicker;
  }

  public CexIODepth getCexIOOrderBook(String tradableIdentifier, String currency) throws IOException {

    CexIODepth cexIODepth = cexio.getDepth(tradableIdentifier, currency);

    return cexIODepth;
  }

  public CexIOTrade[] getCexIOTrades(String tradableIdentifier, String currency, Long since) throws IOException {

    CexIOTrade[] trades;

    if (since != null) {
      trades = cexio.getTradesSince(tradableIdentifier, currency, since);
    }
    else { // default to full available trade history
      trades = cexio.getTrades(tradableIdentifier, currency);
    }

    return trades;
  }

}
