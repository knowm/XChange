package org.knowm.xchange.binance.dto.meta.exchangeinfo;

public class BinanceExchangeInfo {
  private String timezone;

  private Symbol[] symbols;

  private String serverTime;

  private RateLimit[] rateLimits;

  private String[] exchangeFilters;

  private String[] permissions;

  public String getTimezone() {
    return timezone;
  }

  public void setTimezone(String timezone) {
    this.timezone = timezone;
  }

  public Symbol[] getSymbols() {
    return symbols;
  }

  public void setSymbols(Symbol[] symbols) {
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

  public String[] getExchangeFilters() {
    return exchangeFilters;
  }

  public void setExchangeFilters(String[] exchangeFilters) {
    this.exchangeFilters = exchangeFilters;
  }

  public String[] getPermissions() {
    return permissions;
  }

  public void setPermissions(String[] permissions) {
    this.permissions = permissions;
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
        + ", exchangeFilters = "
        + exchangeFilters
        + ", permissions = "
        + permissions
        + "]";
  }
}
