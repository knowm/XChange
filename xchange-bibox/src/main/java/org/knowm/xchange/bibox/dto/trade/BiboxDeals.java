package org.knowm.xchange.bibox.dto.trade;

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

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({"pair", "price", "amount", "time", "side"})
public class BiboxDeals {

  @JsonProperty("id")
  private String id;

  @JsonProperty("pair")
  private String pair;

  /** transaction price */
  @JsonProperty("price")
  private BigDecimal price;

  /** transaction amount */
  @JsonProperty("amount")
  private BigDecimal amount;

  /** transaction time */
  @JsonProperty("time")
  private long time;

  /** transaction side，1-bid，2-ask */
  @JsonProperty("side")
  private int side;

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  @JsonProperty("id")
  public String getId() {
    return id;
  }

  @JsonProperty("id")
  public void setId(String id) {
    this.id = id;
  }

  @JsonProperty("pair")
  public String getPair() {
    return pair;
  }

  @JsonProperty("pair")
  public void setPair(String pair) {
    this.pair = pair;
  }

  @JsonProperty("price")
  public BigDecimal getPrice() {
    return price;
  }

  @JsonProperty("price")
  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  @JsonProperty("amount")
  public BigDecimal getAmount() {
    return amount;
  }

  @JsonProperty("amount")
  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  @JsonProperty("time")
  public long getTime() {
    return time;
  }

  @JsonProperty("time")
  public void setTime(long time) {
    this.time = time;
  }

  @JsonProperty("side")
  public int getSide() {
    return side;
  }

  @JsonProperty("side")
  public void setSide(int side) {
    this.side = side;
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
