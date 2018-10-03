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
@JsonPropertyOrder({"total_amount", "available_amount", "reserved_at", "valid_until", "allocation"})
public class BitcoindeFidorReservation {

  @JsonProperty("total_amount")
  private BigDecimal totalAmount;

  @JsonProperty("available_amount")
  private BigDecimal availableAmount;

  @JsonProperty("reserved_at")
  private String reservedAt;

  @JsonProperty("valid_until")
  private String validUntil;

  @JsonProperty("allocation")
  private BitcoindeAllocations allocation;

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /** No args constructor for use in serialization */
  public BitcoindeFidorReservation() {}

  /**
   * @param availableAmount
   * @param allocation
   * @param reservedAt
   * @param totalAmount
   * @param validUntil
   */
  public BitcoindeFidorReservation(
      BigDecimal totalAmount,
      BigDecimal availableAmount,
      String reservedAt,
      String validUntil,
      BitcoindeAllocations allocation) {
    super();
    this.totalAmount = totalAmount;
    this.availableAmount = availableAmount;
    this.reservedAt = reservedAt;
    this.validUntil = validUntil;
    this.allocation = allocation;
  }

  @JsonProperty("total_amount")
  public BigDecimal getTotalAmount() {
    return totalAmount;
  }

  @JsonProperty("total_amount")
  public void setTotalAmount(BigDecimal totalAmount) {
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

  @JsonProperty("reserved_at")
  public String getReservedAt() {
    return reservedAt;
  }

  @JsonProperty("reserved_at")
  public void setReservedAt(String reservedAt) {
    this.reservedAt = reservedAt;
  }

  @JsonProperty("valid_until")
  public String getValidUntil() {
    return validUntil;
  }

  @JsonProperty("valid_until")
  public void setValidUntil(String validUntil) {
    this.validUntil = validUntil;
  }

  @JsonProperty("allocation")
  public BitcoindeAllocations getAllocation() {
    return allocation;
  }

  @JsonProperty("allocation")
  public void setAllocation(BitcoindeAllocations allocation) {
    this.allocation = allocation;
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
