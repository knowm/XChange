package org.knowm.xchange.cryptofacilities.dto.account;

import java.math.BigDecimal;
import java.util.Map;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @author Panchen
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({"balances", "auxiliary", "marginRequirements", "triggerEstimates"})
public class CryptoFacilitiesAccountInfo {

  @JsonProperty("balances")
  private Map<String, BigDecimal> balances;
  @JsonProperty("auxiliary")
  private Map<String, BigDecimal> auxiliary;
  @JsonProperty("marginRequirements")
  private Map<String, BigDecimal> marginRequirements;
  @JsonProperty("triggerEstimates")
  private Map<String, BigDecimal> triggerEstimates;

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
