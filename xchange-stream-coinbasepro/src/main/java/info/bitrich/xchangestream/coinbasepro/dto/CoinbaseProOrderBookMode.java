package info.bitrich.xchangestream.coinbasepro.dto;

public enum CoinbaseProOrderBookMode {
  Default("level2"),
  Full("full"),
  Batch("level2_batch");

  private final String name;

  CoinbaseProOrderBookMode(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
