package com.xeiam.xchange.mtgox.v1.service.marketdata;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.service.marketdata.streaming.BaseStreamingMarketDataService;

import java.io.IOException;

/**
 * <p>
 * Streaming market data service to provide the following to applications:
 * </p>
 * <ul>
 * <li></li>
 * </ul>
 * 
 * @since 0.0.1
 */
public class MtGoxStreamingMarketDataService extends BaseStreamingMarketDataService {

  /**
   * @param exchangeSpecification The exchange specification providing the required connection data
   */
  public MtGoxStreamingMarketDataService(ExchangeSpecification exchangeSpecification) throws IOException {
    super(exchangeSpecification);
    // TODO Implement this using a SocketIO based producer
  }
}
