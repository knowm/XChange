package info.bitrich.xchangestream.bitfinex.dto;

/** Created by Lukas Zaoralek on 8.11.17. */
public abstract class BitfinexWebSocketOrderbookTransaction {
  public String channelId;

  public BitfinexWebSocketOrderbookTransaction() {}

  public BitfinexWebSocketOrderbookTransaction(String channelId) {
    this.channelId = channelId;
  }

  public String getChannelId() {
    return channelId;
  }

  public abstract BitfinexOrderbook toBitfinexOrderBook(BitfinexOrderbook orderbook);
}
