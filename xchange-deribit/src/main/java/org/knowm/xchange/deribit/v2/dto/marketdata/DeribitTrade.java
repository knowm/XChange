package org.knowm.xchange.deribit.v2.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeribitTrade {

  @JsonProperty("trade_seq")
  private int tradeSeq;

  @JsonProperty("trade_id")
  private String tradeId;

  @JsonProperty("timestamp")
  private long timestamp;

  @JsonProperty("tick_direction")
  private int tickDirection;

  @JsonProperty("price")
  private BigDecimal price;

  @JsonProperty("instrument_name")
  private String instrumentName;

  @JsonProperty("index_price")
  private BigDecimal indexPrice;

  @JsonProperty("direction")
  private String direction;

  @JsonProperty("amount")
  private BigDecimal amount;


  public int getTradeSeq() {
    return tradeSeq;
  }

  public void setTradeSeq(int tradeSeq) {
    this.tradeSeq = tradeSeq;
  }

  public String getTradeId() {
    return tradeId;
  }

  public void setTradeId(String tradeId) {
    this.tradeId = tradeId;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public int getTickDirection() {
    return tickDirection;
  }

  public void setTickDirection(int tickDirection) {
    this.tickDirection = tickDirection;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public String getInstrumentName() {
    return instrumentName;
  }

  public void setInstrumentName(String instrumentName) {
    this.instrumentName = instrumentName;
  }

  public BigDecimal getIndexPrice() {
    return indexPrice;
  }

  public void setIndexPrice(BigDecimal indexPrice) {
    this.indexPrice = indexPrice;
  }

  public String getDirection() {
    return direction;
  }

  public void setDirection(String direction) {
    this.direction = direction;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  @Override
  public String toString() {
    return "DeribitTrade{" +
            "tradeSeq=" + tradeSeq +
            ", tradeId='" + tradeId + '\'' +
            ", timestamp=" + timestamp +
            ", tickDirection=" + tickDirection +
            ", price=" + price +
            ", instrumentName='" + instrumentName + '\'' +
            ", indexPrice=" + indexPrice +
            ", direction='" + direction + '\'' +
            ", amount=" + amount +
            '}';
  }
}
