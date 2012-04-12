package com.xeiam.xchange.mtgox.v1.service.marketdata;

import com.xeiam.xchange.service.marketdata.streaming.DefaultStreamingMarketDataService;

/**
 * <p>Streaming market data service to provide the following to applications:</p>
 * <ul>
 * <li></li>
 * </ul>
 *
 * @since 0.0.1
 *         
 * TODO Implement this using a SocketIO based producer
 */
public class MtGoxStreamingMarketDataService extends DefaultStreamingMarketDataService {

  /**
   * A direct socket connection only requires a host and a port (no protocol)
   *
   * @param host The host name
   * @param port The port to connect to
   */
  public MtGoxStreamingMarketDataService(String host, int port) {
    super(host, port);
  }

}
