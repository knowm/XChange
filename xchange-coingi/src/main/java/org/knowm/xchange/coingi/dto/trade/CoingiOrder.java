package org.knowm.xchange.coingi.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Map;

public class CoingiOrder {
  /*
      {
              "id": "11e6e96e-0677-8a94-8609-0059bb86f908",
              "type": 1,
              "timestamp": 14302485047653,
              "currencyPair": {
              "base": "LTC",
              "counter": "BTC"
              },
              "price": 20,
              "baseAmount": 500,
              "counterAmount": 2,
              "originalBaseAmount": 500,
              "originalCounterAmount": 2,
              "status": 0
      }
  */
  private String id;
  private int type;
  private Long timestamp;
  private Map<String, String> currencyPair;
  private BigDecimal price;
  private BigDecimal baseAmount;
  private BigDecimal counterAmount;
  private BigDecimal originalBaseAmount;
  private BigDecimal originalCounterAmount;
  private int status;

  public CoingiOrder(
      @JsonProperty("id") String id,
      @JsonProperty("type") int type,
      @JsonProperty("timestamp") Long timestamp,
      @JsonProperty("currencyPair") Map<String, String> currencyPair,
      @JsonProperty("price") BigDecimal price,
      @JsonProperty("baseAmount") BigDecimal baseAmount,
      @JsonProperty("counterAmount") BigDecimal counterAmount,
      @JsonProperty("originalBaseAmount") BigDecimal originalBaseAmount,
      @JsonProperty("originalCounterAmount") BigDecimal originalCounterAmount,
      @JsonProperty("status") int status) {

    this.id = id;
    this.type = type;
    this.timestamp = timestamp;
    this.currencyPair = currencyPair;
    this.price = price;
    this.baseAmount = baseAmount;
    this.counterAmount = counterAmount;
    this.originalBaseAmount = originalBaseAmount;
    this.originalCounterAmount = originalCounterAmount;
    this.status = status;
  }

  public String getId() {
    return id;
  }

  public int getType() {
    return type;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public Map<String, String> getCurrencyPair() {
    return currencyPair;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getBaseAmount() {
    return baseAmount;
  }

  public BigDecimal getCounterAmount() {
    return counterAmount;
  }

  public BigDecimal getOriginalBaseAmount() {
    return originalBaseAmount;
  }

  public BigDecimal getOriginalCounterAmount() {
    return originalCounterAmount;
  }

  public int getStatus() {
    return status;
  }
}
