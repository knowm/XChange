package info.bitrich.xchangestream.binance;

public enum BinanceSubscriptionType {
  DEPTH("depth"),
  DEPTH20("depth20"),
  TRADE("trade"),
  TICKER("ticker"),
  BOOK_TICKER("bookTicker"),
  KLINE("kline");

  private final String type;

  BinanceSubscriptionType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
