package org.knowm.xchange.independentreserve.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.independentreserve.IndependentReserve;
import org.knowm.xchange.independentreserve.dto.marketdata.IndependentReserveOrderBook;
import org.knowm.xchange.independentreserve.dto.marketdata.IndependentReserveTicker;

/** Author: Kamil Zbikowski Date: 4/9/15 */
public class IndependentReserveMarketDataServiceRaw extends IndependentReserveBaseService {
  private final IndependentReserve independentReserve;

  public IndependentReserveMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
    this.independentReserve =
        ExchangeRestProxyBuilder.forInterface(
                IndependentReserve.class, exchange.getExchangeSpecification())
            .build();
  }

  public IndependentReserveTicker getIndependentReserveTicker(
      String baseSymbol, String counterSymbol) throws IOException {
    return independentReserve.getMarketSummary(baseSymbol, counterSymbol);
  }

  public IndependentReserveOrderBook getIndependentReserveOrderBook(
      String baseSymbol, String counterSymbol) throws IOException {
    return independentReserve.getOrderBook(baseSymbol, counterSymbol);
  }
}
