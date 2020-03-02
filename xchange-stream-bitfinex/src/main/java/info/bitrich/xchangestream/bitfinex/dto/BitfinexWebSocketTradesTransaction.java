package info.bitrich.xchangestream.bitfinex.dto;

import org.knowm.xchange.bitfinex.v1.dto.marketdata.BitfinexTrade;

/** Created by Lukas Zaoralek on 8.11.17. */
public abstract class BitfinexWebSocketTradesTransaction {
  public String channelId;

  public BitfinexWebSocketTradesTransaction() {}

  public BitfinexWebSocketTradesTransaction(String channelId) {
    this.channelId = channelId;
  }

  public String getChannelId() {
    return channelId;
  }

  public abstract BitfinexTrade[] toBitfinexTrades();
}
