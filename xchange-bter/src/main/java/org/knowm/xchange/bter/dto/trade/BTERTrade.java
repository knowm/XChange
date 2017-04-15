package org.knowm.xchange.bter.dto.trade;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import org.knowm.xchange.bter.dto.BTEROrderType;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({"id", "orderid", "pair", "type", "rate", "amount", "time", "time_unix"})
public class BTERTrade {

  @JsonProperty("id")
  private String id;
  @JsonProperty("orderid")
  private String orderid;
  @JsonProperty("pair")
  private String pair;
  @JsonProperty("type")
  private BTEROrderType type;
  @JsonProperty("rate")
  private BigDecimal rate;
  @JsonProperty("amount")
  private BigDecimal amount;
  @JsonProperty("time")
  private String time;
  @JsonProperty("time_unix")
  private long timeUnix;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  @JsonProperty("id")
  public String getId() {

    return id;
  }

  @JsonProperty("id")
  public void setId(String id) {

    this.id = id;
  }

  @JsonProperty("orderid")
  public String getOrderid() {

    return orderid;
  }

  @JsonProperty("orderid")
  public void setOrderid(String orderid) {

    this.orderid = orderid;
  }

  @JsonProperty("pair")
  public String getPair() {

    return pair;
  }

  @JsonProperty("pair")
  public void setPair(String pair) {

    this.pair = pair;
  }

  @JsonProperty("type")
  public BTEROrderType getType() {

    return type;
  }

  @JsonProperty("type")
  public void setType(BTEROrderType type) {

    this.type = type;
  }

  @JsonProperty("rate")
  public BigDecimal getRate() {

    return rate;
  }

  @JsonProperty("rate")
  public void setRate(BigDecimal rate) {

    this.rate = rate;
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
  public String getTime() {

    return time;
  }

  @JsonProperty("time")
  public void setTime(String time) {

    this.time = time;
  }

  @JsonProperty("time_unix")
  public long getTimeUnix() {

    return timeUnix;
  }

  @JsonProperty("time_unix")
  public void setTimeUnix(long timeUnix) {

    this.timeUnix = timeUnix;
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