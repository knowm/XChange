package info.bitrich.xchangestream.okcoin;

public class OkExFuturesStreamingExchange extends OkExStreamingExchange {

  private static final String API_URI = "wss://real.okex.com:10440/websocket/okexapi?compress=true";

  public OkExFuturesStreamingExchange() {
    super(API_URI);
  }
}
