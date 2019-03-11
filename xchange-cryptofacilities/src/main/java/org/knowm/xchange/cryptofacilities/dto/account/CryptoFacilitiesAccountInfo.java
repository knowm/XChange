package org.knowm.xchange.cryptofacilities.dto.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;
import java.util.Map;
import javax.annotation.Generated;

/** @author Panchen */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
  "type",
  "currency",
  "balances",
  "auxiliary",
  "marginRequirements",
  "triggerEstimates"
})
public class CryptoFacilitiesAccountInfo {

  @JsonProperty("type")
  private String type;

  @JsonProperty("currency")
  private String currency;

  @JsonProperty("balances")
  private Map<String, BigDecimal> balances;

  @JsonProperty("auxiliary")
  private Map<String, BigDecimal> auxiliary;

  @JsonProperty("marginRequirements")
  private Map<String, BigDecimal> marginRequirements;

  @JsonProperty("triggerEstimates")
  private Map<String, BigDecimal> triggerEstimates;

  @JsonProperty("type")
  public String getType() {

    return type;
  }

  @JsonProperty("type")
  public void setType(String type) {

    this.type = type;
  }

  @JsonProperty("currency")
  public String getCurrency() {

    return currency;
  }

  @JsonProperty("currency")
  public void setCurrency(String currency) {

    this.currency = currency;
  }

  @JsonProperty("balances")
  public Map<String, BigDecimal> getBalances() {

    return balances;
  }

  @JsonProperty("balances")
  public void setBalances(Map<String, BigDecimal> balances) {

    this.balances = balances;
  }

  @JsonProperty("auxiliary")
  public Map<String, BigDecimal> getAuxiliary() {

    return auxiliary;
  }

  @JsonProperty("auxiliary")
  public void setAuxiliary(Map<String, BigDecimal> auxiliary) {

    this.auxiliary = auxiliary;
  }

  @JsonProperty("marginRequirements")
  public Map<String, BigDecimal> getMarginRequirements() {

    return marginRequirements;
  }

  @JsonProperty("marginRequirements")
  public void setMarginRequirements(Map<String, BigDecimal> marginRequirements) {

    this.marginRequirements = marginRequirements;
  }

  @JsonProperty("triggerEstimates")
  public Map<String, BigDecimal> getTriggerEstimates() {

    return triggerEstimates;
  }

  @JsonProperty("triggerEstimates")
  public void setTriggerEstimates(Map<String, BigDecimal> triggerEstimates) {

    this.triggerEstimates = triggerEstimates;
  }
}
