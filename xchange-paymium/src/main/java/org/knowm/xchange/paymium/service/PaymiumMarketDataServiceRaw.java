package org.knowm.xchange.paymium.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.paymium.dto.marketdata.PaymiumMarketDepth;
import org.knowm.xchange.paymium.dto.marketdata.PaymiumTicker;
import org.knowm.xchange.paymium.dto.marketdata.PaymiumTrade;

public class PaymiumMarketDataServiceRaw extends PaymiumBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  protected PaymiumMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public PaymiumTicker getPaymiumTicker() throws IOException {
    return paymium.getPaymiumTicker();
  }

  public PaymiumMarketDepth getPaymiumMarketDepth() throws IOException {
    return paymium.getOrderBook();
  }

  public PaymiumTrade[] getPaymiumTrades() throws IOException {
    return paymium.getTrades();
  }
}
