package com.xeiam.xchange.lakebtc.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.lakebtc.LakeBTC;
import com.xeiam.xchange.lakebtc.dto.marketdata.LakeBTCOrderBook;
import com.xeiam.xchange.lakebtc.dto.marketdata.LakeBTCTickers;

/**
 * @author kpysniak
 */
public class LakeBTCMarketDataServiceRaw extends LakeBTCBasePollingService<LakeBTC> {

  private final LakeBTC lakeBTC;

  /**
   * @param exchange
   */
  protected LakeBTCMarketDataServiceRaw(Exchange exchange) {

    super(exchange);

    this.lakeBTC = RestProxyFactory.createProxy(LakeBTC.class, exchange.getExchangeSpecification().getSslUri());
  }

  public LakeBTCTickers getLakeBTCTickers() throws IOException {

    return lakeBTC.getLakeBTCTickers();
  }

  public LakeBTCOrderBook getLakeBTCOrderBookUSD() throws IOException {

    return lakeBTC.getLakeBTCOrderBookUSD();
  }

  public LakeBTCOrderBook getLakeBTCOrderBookCNY() throws IOException {

    return lakeBTC.getLakeBTCOrderBookCNY();
  }
}
