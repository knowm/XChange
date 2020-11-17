package info.bitrich.xchangestream.kraken.dto;

import info.bitrich.xchangestream.kraken.KrakenOrderBookStorage;
import info.bitrich.xchangestream.kraken.dto.enums.KrakenOrderBookMessageType;
import org.knowm.xchange.kraken.dto.marketdata.KrakenPublicOrder;

/** @author pchertalev */
public class KrakenOrderBook {

  private final Integer channelID;
  private final String channelName;
  private final String pair;
  private final KrakenOrderBookMessageType type;

  private final KrakenPublicOrder[] ask;
  private final KrakenPublicOrder[] bid;

  public KrakenOrderBook(
      Integer channelID,
      String channelName,
      String pair,
      KrakenOrderBookMessageType type,
      KrakenPublicOrder[] ask,
      KrakenPublicOrder[] bid) {
    this.channelID = channelID;
    this.channelName = channelName;
    this.pair = pair;
    this.type = type;
    this.ask = ask;
    this.bid = bid;
  }

  public Integer getChannelID() {
    return channelID;
  }

  public String getChannelName() {
    return channelName;
  }

  public String getPair() {
    return pair;
  }

  public KrakenOrderBookMessageType getType() {
    return type;
  }

  public KrakenPublicOrder[] getAsk() {
    return ask;
  }

  public KrakenPublicOrder[] getBid() {
    return bid;
  }

  public KrakenOrderBookStorage toKrakenOrderBook(KrakenOrderBookStorage orderbook, int depth) {
    if (type == KrakenOrderBookMessageType.UPDATE && orderbook != null) {
      orderbook.updateOrderBook(this);
      return orderbook;
    }
    return new KrakenOrderBookStorage(this, depth);
  }
}
