package info.bitrich.xchangestream.binancefuture;

import info.bitrich.xchangestream.binance.BinanceStreamingExchange;

/** Simple endpoint switch as we cannot inject it when setting up the endpoint. */
public class BinanceFutureStreamingExchange extends BinanceStreamingExchange {

  private static final String WS_API_BASE_URI = "wss://fstream.binance.com/";

  protected String getStreamingBaseUri() {
    return WS_API_BASE_URI;
  }
}
