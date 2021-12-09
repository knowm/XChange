package org.knowm.xchange.raydium.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.marketdata.MarketDataService;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class RaydiumMarketDataService extends RaydiumMarketDataServiceRaw
    implements MarketDataService {

  public RaydiumMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OrderBook getOrderBook(Instrument instrument, Object... args) throws IOException {
    return new OrderBook(Date.from(Instant.now()), new ArrayList<>(), new ArrayList<>());
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    return new OrderBook(Date.from(Instant.now()), new ArrayList<>(), new ArrayList<>());
  }
}
