package info.bitrich.xchangestream.kraken.dto;

import info.bitrich.xchangestream.kraken.KrakenOrderBookStorage;
import info.bitrich.xchangestream.kraken.dto.enums.KrakenOrderBookMessageType;
import org.knowm.xchange.kraken.dto.marketdata.KrakenPublicOrder;

/** @author pchertalev */
public class KrakenOrderBook {

  private Integer channelID;
  private String channelName;
  private String pair;
  private KrakenOrderBookMessageType type;

  private KrakenPublicOrder[] ask;
  private KrakenPublicOrder[] bid;

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
    if (type == KrakenOrderBookMessageType.UPDATE) {
      orderbook.updateOrderBook(this);
      return orderbook;
    }
    return new KrakenOrderBookStorage(this, depth);
  }
}
