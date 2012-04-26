package com.xeiam.xchange.service.marketdata.streaming;

/**
 * <p>
 * Event interface to provide the following to API:
 * </p>
 * <ul>
 * <li>Provision of market data information to listeners through different implementations</li>
 * </ul>
 * 
 * @since 0.0.1
 */
public interface MarketDataEvent {

  /**
   * @return The raw data provided by the upstream server
   */
  byte[] getRawData();

}
