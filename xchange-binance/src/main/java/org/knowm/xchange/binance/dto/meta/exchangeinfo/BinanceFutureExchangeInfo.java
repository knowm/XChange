package org.knowm.xchange.binance.dto.meta.exchangeinfo;

public class BinanceFutureExchangeInfo {
  private String timezone;

  private FutureSymbol[] symbols;

  private String serverTime;

  private RateLimit[] rateLimits;

  public String getTimezone() {
    return timezone;
  }

  public void setTimezone(String timezone) {
    this.timezone = timezone;
  }

  public FutureSymbol[] getSymbols() {
    return symbols;
  }

  public void setSymbols(FutureSymbol[] symbols) {
    this.symbols = symbols;
  }

  public String getServerTime() {
    return serverTime;
  }

  public void setServerTime(String serverTime) {
    this.serverTime = serverTime;
  }

  public RateLimit[] getRateLimits() {
    return rateLimits;
  }

  public void setRateLimits(RateLimit[] rateLimits) {
    this.rateLimits = rateLimits;
  }

  @Override
  public String toString() {
    return "ClassPojo [timezone = "
        + timezone
        + ", symbols = "
        + symbols
        + ", serverTime = "
        + serverTime
        + ", rateLimits = "
        + rateLimits
        + "]";
  }
}
