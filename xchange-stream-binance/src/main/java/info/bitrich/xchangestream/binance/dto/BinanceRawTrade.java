package info.bitrich.xchangestream.binance.dto;

import java.math.BigDecimal;

public class BinanceRawTrade {
  private final String eventType;
  private final String eventTime;
  private final String symbol;
  private final long tradeId;
  private final BigDecimal price;
  private final BigDecimal quantity;
  private final long buyerOrderId;
  private final long sellerOrderId;
  private final long timestamp;
  private final boolean buyerMarketMaker;
  private final boolean ignore;

  public BinanceRawTrade(
      String eventType,
      String eventTime,
      String symbol,
      long tradeId,
      BigDecimal price,
      BigDecimal quantity,
      long buyerOrderId,
      long sellerOrderId,
      long timestamp,
      boolean buyerMarketMaker,
      boolean ignore) {
    this.eventType = eventType;
    this.eventTime = eventTime;
    this.symbol = symbol;
    this.tradeId = tradeId;
    this.price = price;
    this.quantity = quantity;
    this.buyerOrderId = buyerOrderId;
    this.sellerOrderId = sellerOrderId;
    this.timestamp = timestamp;
    this.buyerMarketMaker = buyerMarketMaker;
    this.ignore = ignore;
  }

  public String getEventType() {
    return eventType;
  }

  public String getEventTime() {
    return eventTime;
  }

  public String getSymbol() {
    return symbol;
  }

  public long getTradeId() {
    return tradeId;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public long getBuyerOrderId() {
    return buyerOrderId;
  }

  public long getSellerOrderId() {
    return sellerOrderId;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public boolean isBuyerMarketMaker() {
    return buyerMarketMaker;
  }

  public boolean isIgnore() {
    return ignore;
  }
}
