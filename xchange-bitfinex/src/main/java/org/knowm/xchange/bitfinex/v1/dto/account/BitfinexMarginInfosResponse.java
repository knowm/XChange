package org.knowm.xchange.bitfinex.v1.dto.account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({"margin_balance", "tradable_balance", "unrealized_pl", "unrealized_swap", "net_value", "required_margin", "leverage",
                       "margin_requirement", "margin_limits", "message"})
public class BitfinexMarginInfosResponse {

  @JsonProperty("margin_balance")
  private BigDecimal marginBalance;
  @JsonProperty("tradable_balance")
  private BigDecimal tradableBalance;
  @JsonProperty("unrealized_pl")
  private BigDecimal unrealizedPl;
  @JsonProperty("unrealized_swap")
  private BigDecimal unrealizedSwap;
  @JsonProperty("net_value")
  private BigDecimal netValue;
  @JsonProperty("required_margin")
  private BigDecimal requiredMargin;
  @JsonProperty("leverage")
  private BigDecimal leverage;
  @JsonProperty("margin_requirement")
  private BigDecimal marginRequirement;
  @JsonProperty("margin_limits")
  private List<BitfinexMarginLimit> marginLimits = new ArrayList<BitfinexMarginLimit>();
  @JsonProperty("message")
  private String message;
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  @JsonProperty("margin_balance")
  public BigDecimal getMarginBalance() {

    return marginBalance;
  }

  @JsonProperty("margin_balance")
  public void setMarginBalance(BigDecimal marginBalance) {

    this.marginBalance = marginBalance;
  }

  @JsonProperty("tradable_balance")
  public BigDecimal getTradableBalance() {

    return tradableBalance;
  }

  @JsonProperty("tradable_balance")
  public void setTradableBalance(BigDecimal tradableBalance) {

    this.tradableBalance = tradableBalance;
  }

  @JsonProperty("unrealized_pl")
  public BigDecimal getUnrealizedPl() {

    return unrealizedPl;
  }

  @JsonProperty("unrealized_pl")
  public void setUnrealizedPl(BigDecimal unrealizedPl) {

    this.unrealizedPl = unrealizedPl;
  }

  @JsonProperty("unrealized_swap")
  public BigDecimal getUnrealizedSwap() {

    return unrealizedSwap;
  }

  @JsonProperty("unrealized_swap")
  public void setUnrealizedSwap(BigDecimal unrealizedSwap) {

    this.unrealizedSwap = unrealizedSwap;
  }

  @JsonProperty("net_value")
  public BigDecimal getNetValue() {

    return netValue;
  }

  @JsonProperty("net_value")
  public void setNetValue(BigDecimal netValue) {

    this.netValue = netValue;
  }

  @JsonProperty("required_margin")
  public BigDecimal getRequiredMargin() {

    return requiredMargin;
  }

  @JsonProperty("required_margin")
  public void setRequiredMargin(BigDecimal requiredMargin) {

    this.requiredMargin = requiredMargin;
  }

  @JsonProperty("leverage")
  public BigDecimal getLeverage() {

    return leverage;
  }

  @JsonProperty("leverage")
  public void setLeverage(BigDecimal leverage) {

    this.leverage = leverage;
  }

  @JsonProperty("margin_requirement")
  public BigDecimal getMarginRequirement() {

    return marginRequirement;
  }

  @JsonProperty("margin_requirement")
  public void setMarginRequirement(BigDecimal marginRequirement) {

    this.marginRequirement = marginRequirement;
  }

  @JsonProperty("margin_limits")
  public List<BitfinexMarginLimit> getMarginLimits() {

    return marginLimits;
  }

  @JsonProperty("margin_limits")
  public void setMarginLimits(List<BitfinexMarginLimit> marginLimits) {

    this.marginLimits = marginLimits;
  }

  @JsonProperty("message")
  public String getMessage() {

    return message;
  }

  @JsonProperty("message")
  public void setMessage(String message) {

    this.message = message;
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
