package com.xeiam.xchange.mtgox.v1.service.marketdata;

import com.xeiam.xchange.service.marketdata.streaming.BaseStreamingMarketDataService;

/**
 * <p>Streaming market data service to provide the following to applications:</p>
 * <ul>
 * <li></li>
 * </ul>
 * Example:<br>
 * <pre>
 * </pre>
 *
 * @since 0.0.1
 *         
 */
public class MtGoxStreamingMarketDataService extends BaseStreamingMarketDataService {

  public MtGoxStreamingMarketDataService(String host, int port) {
    super(host, port);
  }

  @Override
  public void connect() {
    //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void disconnect() {
    //To change body of implemented methods use File | Settings | File Templates.
  }
}
