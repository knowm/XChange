package org.knowm.xchange.bitcointoyou.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

/**
 * @author Danilo Guimaraes
 * @author Jonathas Carrijo
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({"high", "low", "vol", "last", "buy", "buyQty", "sell", "sellQty", "date"})
public class BitcointoyouMarketData {

  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal volume;
  private final BigDecimal last;
  private final BigDecimal buy;
  private final BigDecimal buyQuantity;
  private final BigDecimal sell;
  private final BigDecimal sellQuantity;
  private final Long date;
  @JsonIgnore private final Map<String, Object> additionalProperties = new HashMap<>();

  public BitcointoyouMarketData(
      @JsonProperty("high") BigDecimal high,
      @JsonProperty("low") BigDecimal low,
      @JsonProperty("vol") BigDecimal volume,
      @JsonProperty("last") BigDecimal last,
      @JsonProperty("buy") BigDecimal buy,
      @JsonProperty("buyQty") BigDecimal buyQuantity,
      @JsonProperty("sell") BigDecimal sell,
      @JsonProperty("sellQty") BigDecimal sellQuantity,
      @JsonProperty("date") Long date) {
    this.high = high;
    this.low = low;
    this.volume = volume;
    this.last = last;
    this.buy = buy;
    this.buyQuantity = buyQuantity;
    this.sell = sell;
    this.sellQuantity = sellQuantity;
    this.date = date;
  }

  public BigDecimal getHigh() {

    return high;
  }

  public BigDecimal getLow() {

    return low;
  }

  public BigDecimal getVolume() {

    return volume;
  }

  public BigDecimal getLast() {

    return last;
  }

  public BigDecimal getBuy() {

    return buy;
  }

  public BigDecimal getBuyQuantity() {

    return buyQuantity;
  }

  public BigDecimal getSell() {

    return sell;
  }

  public BigDecimal getSellQuantity() {

    return sellQuantity;
  }

  public Long getDate() {

    return date;
  }

  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {

    return this.additionalProperties;
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, Object value) {

    this.additionalProperties.put(name, value);
  }

  @Override
  public String toString() {
    return String.format(
        "BitcointoyouMarketData [high=%f, low=%f, volume=%f, last=%f, buy=%f, buyQuantity=%f, sell=%f, "
            + "sellQuantity=%f, additionalProperties=%s]",
        high, low, volume, last, buy, buyQuantity, sell, sellQuantity, date);
  }
}
