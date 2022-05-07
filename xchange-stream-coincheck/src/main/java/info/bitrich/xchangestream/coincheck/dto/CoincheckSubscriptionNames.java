package info.bitrich.xchangestream.coincheck.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum CoincheckSubscriptionNames {
  ORDERBOOK("orderbook"),
  TRADES("trades");

  @Getter private final String name;

  public String toString() {
    return getName();
  }
}
