package org.knowm.xchange.chbtc.dto.marketdata;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import si.mazi.rescu.serialization.jackson.serializers.TimestampDeserializer;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class ChbtcTrade {

  @JsonDeserialize(using = TimestampDeserializer.class)
  private Date date;
  private Integer tid;
  private BigDecimal price;
  private BigDecimal amount;
  private Type type;
  private TradeType tradeType;

  public Date getDate() {
    return date;
  }

  public Integer getTid() {
    return tid;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public Type getType() {
    return type;
  }

  public TradeType getTradeType() {
    return tradeType;
  }

  @Override
  public String toString() {
    return String.format("ChbtcTrade{date=%s, tid=%d, price=%s, amount=%s, type=%s, tradeType=%s}", date, tid, price, amount, type, tradeType);
  }

  public enum Type {
    buy, sell
  }

  public enum TradeType {
    bid, ask
  }
}
