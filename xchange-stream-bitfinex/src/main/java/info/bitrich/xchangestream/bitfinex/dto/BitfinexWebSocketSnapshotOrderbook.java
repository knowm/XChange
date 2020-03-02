package info.bitrich.xchangestream.bitfinex.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

/** Created by Lukas Zaoralek on 8.11.17. */
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
public class BitfinexWebSocketSnapshotOrderbook extends BitfinexWebSocketOrderbookTransaction {
  public BitfinexOrderbookLevel[] levels;

  @Override
  public BitfinexOrderbook toBitfinexOrderBook(BitfinexOrderbook orderbook) {
    return new BitfinexOrderbook(levels);
  }
}
