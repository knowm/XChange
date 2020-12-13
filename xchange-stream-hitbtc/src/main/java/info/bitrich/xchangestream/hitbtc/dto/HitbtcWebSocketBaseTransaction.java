package info.bitrich.xchangestream.hitbtc.dto;

/** Created by Pavel Chertalev on 15.03.2018. */
public class HitbtcWebSocketBaseTransaction {

  protected final String method;

  public HitbtcWebSocketBaseTransaction(String method) {
    this.method = method;
  }

  public String getMethod() {
    return method;
  }
}
