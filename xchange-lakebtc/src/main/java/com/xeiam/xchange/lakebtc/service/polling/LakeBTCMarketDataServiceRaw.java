package com.xeiam.xchange.lakebtc.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.lakebtc.LakeBTC;
import com.xeiam.xchange.lakebtc.dto.marketdata.LakeBTCOrderBook;
import com.xeiam.xchange.lakebtc.dto.marketdata.LakeBTCTickers;
import com.xeiam.xchange.lakebtc.service.LakeBTCBaseService;

/**
 * @author kpysniak
 */
public class LakeBTCMarketDataServiceRaw extends LakeBTCBaseService {

  private final LakeBTC lakeBTC;

  /**
   * Constructor Initialize common properties from the exchange specification
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  protected LakeBTCMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.lakeBTC = RestProxyFactory.createProxy(LakeBTC.class, exchangeSpecification.getSslUri());
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
