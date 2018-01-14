package org.xchange.coinegg.dto.marketdata;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinEggOrder {

  public enum Type {
    BUY, 
    SELL;
    
    @JsonCreator
    public static Type forValue(String value) {
        return Type.valueOf(value.toUpperCase());
    }
  }
  
  private final BigDecimal price;
  private final BigDecimal amount;
  private final Type type;
  private final long timestamp;
  private final int tid;
  
  public CoinEggOrder(@JsonProperty("date") long timestamp, @JsonProperty("price") BigDecimal price,
      @JsonProperty("amount") BigDecimal amount, @JsonProperty("tid") int tid, @JsonProperty("type") Type type) {
    
    this.timestamp = timestamp;
    this.price = price;
    this.amount = amount;
    this.tid = tid;
    this.type = type;
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
  
  public int getTransactionID() {
    return tid;
  }
  
  public long getTimestamp() {
    return timestamp;
  }
}
