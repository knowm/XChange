package com.xeiam.xchange.service.marketdata.streaming;

/**
 * <p>Listener to provide the following to API:</p>
 * <ul>
 * <li>Callback methods for market data events</li>
 * </ul>
 *
 * @since 0.0.1
 *         
 */
public interface MarketDataListener {
  
  void onUpdate(MarketDataEvent event);
}
