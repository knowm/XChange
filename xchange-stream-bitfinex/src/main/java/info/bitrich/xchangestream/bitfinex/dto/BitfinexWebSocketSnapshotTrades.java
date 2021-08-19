package info.bitrich.xchangestream.bitfinex.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.bitfinex.v1.dto.marketdata.BitfinexTrade;

/** Created by Lukas Zaoralek on 7.11.17. */
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
public class BitfinexWebSocketSnapshotTrades extends BitfinexWebSocketTradesTransaction {
  public BitfinexWebSocketTrade[] trades;

  public BitfinexWebSocketSnapshotTrades() {}

  public BitfinexWebSocketSnapshotTrades(String channelId, BitfinexWebSocketTrade[] trades) {
    super(channelId);
    this.trades = trades;
  }

  public BitfinexWebSocketTrade[] getTrades() {
    return trades;
  }

  public BitfinexTrade[] toBitfinexTrades() {
    List<BitfinexTrade> bitfinexTrades = new ArrayList<>(getTrades().length);
    for (BitfinexWebSocketTrade websocketTrade : trades) {
      bitfinexTrades.add(websocketTrade.toBitfinexTrade());
    }

    return bitfinexTrades.toArray(new BitfinexTrade[bitfinexTrades.size()]);
  }
}
