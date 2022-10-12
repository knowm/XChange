package info.bitrich.xchangestream.binance;

import org.knowm.xchange.binance.BinanceAuthenticated;

import java.io.IOException;

/**
 * @author Eugene Schava
 */
class BinanceMarginUserDataChannel extends BinanceUserDataChannel {

  BinanceMarginUserDataChannel(BinanceAuthenticated binance, String apiKey, Runnable onApiCall) {
    super(binance, apiKey, onApiCall);
  }

  @Override
  protected String startUserDataStream() throws IOException {
    return binance.startMarginUserDataStream(apiKey).getListenKey();
  }

  @Override
  protected void keepAliveUserDataStream() throws IOException {
    binance.keepAliveMarginUserDataStream(apiKey, listenKey);
  }
}
