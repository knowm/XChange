package info.bitrich.xchangestream.binancefuture;

import info.bitrich.xchangestream.binance.BinanceStreamingExchange;

/** Simple endpoint switch as we cannot inject it when setting up the endpoint. */
public class BinanceFutureStreamingExchange extends BinanceStreamingExchange {

  private static final String WS_API_BASE_URI = "wss://fstream.binance.com/";
  private static final String WS_TRADE_API_BASE_URI = "wss://ws-fapi.binance.com/ws-fapi/v1";
  private static final String WS_SANDBOX_API_BASE_URI = "wss://fstream.binancefuture.com/";
  private static final String WS_SANDBOX_TRADE_API_BASE_URI =
      "wss://testnet.binancefuture.com/ws-fapi/v1";

  protected String getStreamingBaseUri() {
    return Boolean.TRUE.equals(exchangeSpecification.getExchangeSpecificParametersItem(USE_SANDBOX))
        ? WS_SANDBOX_API_BASE_URI
        : WS_API_BASE_URI;
  }

  protected String getTradeStreamingBaseUri() {
    return Boolean.TRUE.equals(exchangeSpecification.getExchangeSpecificParametersItem(USE_SANDBOX))
        ? WS_SANDBOX_TRADE_API_BASE_URI
        : WS_TRADE_API_BASE_URI;
  }
}
