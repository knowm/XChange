package org.knowm.xchange.bitcoinde.dto.account;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"available_amount", "reserved_amount", "total_amount"})
public class BitcoindeBalance {

  @JsonProperty("available_amount")
  private BigDecimal availableAmount;

  @JsonProperty("reserved_amount")
  private BigDecimal reservedAmount;

  @JsonProperty("total_amount")
  private BigDecimal totalAmount;

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /** No args constructor for use in serialization */
  public BitcoindeBalance() {}

  /**
   * @param availableAmount
   * @param reservedAmount
   * @param totalAmount
   */
  public BitcoindeBalance(
      BigDecimal availableAmount, BigDecimal reservedAmount, BigDecimal totalAmount) {
    super();
    this.availableAmount = availableAmount;
    this.reservedAmount = reservedAmount;
    this.totalAmount = totalAmount;
  }

  @JsonProperty("available_amount")
  public BigDecimal getAvailableAmount() {
    return availableAmount;
  }

  @JsonProperty("available_amount")
  public void setAvailableAmount(BigDecimal availableAmount) {
    this.availableAmount = availableAmount;
  }

  @JsonProperty("reserved_amount")
  public BigDecimal getReservedAmount() {
    return reservedAmount;
  }

  @JsonProperty("reserved_amount")
  public void setReservedAmount(BigDecimal reservedAmount) {
    this.reservedAmount = reservedAmount;
  }

  @JsonProperty("total_amount")
  public BigDecimal getTotalAmount() {
    return totalAmount;
  }

  @JsonProperty("total_amount")
  public void setTotalAmount(BigDecimal totalAmount) {
    this.totalAmount = totalAmount;
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
