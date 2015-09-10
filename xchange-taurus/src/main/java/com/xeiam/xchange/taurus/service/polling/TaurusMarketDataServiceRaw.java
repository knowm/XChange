package com.xeiam.xchange.taurus.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.taurus.Taurus;
import com.xeiam.xchange.taurus.dto.marketdata.TaurusOrderBook;
import com.xeiam.xchange.taurus.dto.marketdata.TaurusTicker;
import com.xeiam.xchange.taurus.dto.marketdata.TaurusTransaction;

import si.mazi.rescu.RestProxyFactory;

public class TaurusMarketDataServiceRaw extends TaurusBasePollingService {

  private final Taurus taurus;

  public TaurusMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
    this.taurus = RestProxyFactory.createProxy(Taurus.class, exchange.getExchangeSpecification().getSslUri());
  }

  public TaurusTicker getTaurusTicker() throws IOException {
    return taurus.getTicker();
  }

  public TaurusOrderBook getTaurusOrderBook() throws IOException {
    return taurus.getOrderBook();
  }

  public TaurusTransaction[] getTaurusTransactions() throws IOException {
    return taurus.getTransactions();
  }

  public TaurusTransaction[] getTaurusTransactions(Taurus.Time time) throws IOException {
    return time == null ? taurus.getTransactions() : taurus.getTransactions(time);
  }
}
