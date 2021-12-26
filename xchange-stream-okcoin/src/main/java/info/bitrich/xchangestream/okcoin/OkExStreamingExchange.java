package info.bitrich.xchangestream.okcoin;

/** Created by Lukas Zaoralek on 17.11.17. */
public class OkExStreamingExchange extends OkCoinStreamingExchange {
  private static final String API_URI = "wss://real.okex.com:8443/ws/v3";

  public OkExStreamingExchange() {
    super(new OkCoinStreamingService(API_URI));
  }

  public OkExStreamingExchange(String apiUrl) {
    super(new OkCoinStreamingService(apiUrl));
  }
}
