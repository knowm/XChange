package org.knowm.xchange.coindeal.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coindeal.Coindeal;
import org.knowm.xchange.coindeal.CoindealAdapters;
import org.knowm.xchange.coindeal.dto.marketdata.CoindealOrderBook;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.BaseExchangeService;
import si.mazi.rescu.RestProxyFactory;

public class CoindealMarketDataServiceRaw extends BaseExchangeService {

  private final Coindeal coindeal;

  public CoindealMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
    this.coindeal =
        RestProxyFactory.createProxy(
            Coindeal.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
  }

  public CoindealOrderBook getCoindealOrderbook(CurrencyPair currencyPair) throws IOException {
    return coindeal.getOrderBook(CoindealAdapters.adaptCurrencyPairToString(currencyPair));
  }
}
