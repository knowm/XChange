package org.knowm.xchange.kucoin.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({"SELL", "BUY"})
public class KucoinOrderBook {

  @JsonProperty("SELL")
  private List<List<BigDecimal>> sell = new ArrayList<List<BigDecimal>>();

  @JsonProperty("BUY")
  private List<List<BigDecimal>> buy = new ArrayList<List<BigDecimal>>();

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /** No args constructor for use in serialization */
  public KucoinOrderBook() {}

  /**
   * @param sell
   * @param buy
   */
  public KucoinOrderBook(List<List<BigDecimal>> sell, List<List<BigDecimal>> buy) {
    super();
    this.sell = sell;
    this.buy = buy;
  }

  /** @return The sell */
  @JsonProperty("SELL")
  public List<List<BigDecimal>> getSell() {
    return sell;
  }

  /** @param sell The sell */
  @JsonProperty("SELL")
  public void setSELL(List<List<BigDecimal>> sell) {
    this.sell = sell;
  }

  /** @return The buy */
  @JsonProperty("BUY")
  public List<List<BigDecimal>> getBuy() {
    return buy;
  }

  /** @param buy The buy */
  @JsonProperty("BUY")
  public void setBuy(List<List<BigDecimal>> buy) {
    this.buy = buy;
  }

  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }
}
