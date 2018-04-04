package org.knowm.xchange.kucoin.dto.account;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
  "oid",
  "address",
  "context",
  "userOid",
  "coinType",
  "createdAt",
  "deletedAt",
  "updatedAt",
  "lastReceivedAt"
})
public class KucoinDepositAddress {

  @JsonProperty("oid")
  private String oid;

  @JsonProperty("address")
  private String address;

  @JsonProperty("context")
  private String context;

  @JsonProperty("userOid")
  private String userOid;

  @JsonProperty("coinType")
  private String coinType;

  @JsonProperty("createdAt")
  private Long createdAt;

  @JsonProperty("deletedAt")
  private Long deletedAt;

  @JsonProperty("updatedAt")
  private Long updatedAt;

  @JsonProperty("lastReceivedAt")
  private Long lastReceivedAt;

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /** No args constructor for use in serialization */
  public KucoinDepositAddress() {}

  /**
   * @param coinType
   * @param createdAt
   * @param deletedAt
   * @param address
   * @param userOid
   * @param lastReceivedAt
   * @param context
   * @param oid
   * @param updatedAt
   */
  public KucoinDepositAddress(
      String oid,
      String address,
      String context,
      String userOid,
      String coinType,
      Long createdAt,
      Long deletedAt,
      Long updatedAt,
      Long lastReceivedAt) {
    super();
    this.oid = oid;
    this.address = address;
    this.context = context;
    this.userOid = userOid;
    this.coinType = coinType;
    this.createdAt = createdAt;
    this.deletedAt = deletedAt;
    this.updatedAt = updatedAt;
    this.lastReceivedAt = lastReceivedAt;
  }

  /** @return The oid */
  @JsonProperty("oid")
  public String getOid() {
    return oid;
  }

  /** @param oid The oid */
  @JsonProperty("oid")
  public void setOid(String oid) {
    this.oid = oid;
  }

  /** @return The address */
  @JsonProperty("address")
  public String getAddress() {
    return address;
  }

  /** @param address The address */
  @JsonProperty("address")
  public void setAddress(String address) {
    this.address = address;
  }

  /** @return The context */
  @JsonProperty("context")
  public Object getContext() {
    return context;
  }

  /** @param context The context */
  @JsonProperty("context")
  public void setContext(String context) {
    this.context = context;
  }

  /** @return The userOid */
  @JsonProperty("userOid")
  public String getUserOid() {
    return userOid;
  }

  /** @param userOid The userOid */
  @JsonProperty("userOid")
  public void setUserOid(String userOid) {
    this.userOid = userOid;
  }

  /** @return The coinType */
  @JsonProperty("coinType")
  public String getCoinType() {
    return coinType;
  }

  /** @param coinType The coinType */
  @JsonProperty("coinType")
  public void setCoinType(String coinType) {
    this.coinType = coinType;
  }

  /** @return The createdAt */
  @JsonProperty("createdAt")
  public Long getCreatedAt() {
    return createdAt;
  }

  /** @param createdAt The createdAt */
  @JsonProperty("createdAt")
  public void setCreatedAt(Long createdAt) {
    this.createdAt = createdAt;
  }

  /** @return The deletedAt */
  @JsonProperty("deletedAt")
  public Object getDeletedAt() {
    return deletedAt;
  }

  /** @param deletedAt The deletedAt */
  @JsonProperty("deletedAt")
  public void setDeletedAt(Long deletedAt) {
    this.deletedAt = deletedAt;
  }

  /** @return The updatedAt */
  @JsonProperty("updatedAt")
  public Long getUpdatedAt() {
    return updatedAt;
  }

  /** @param updatedAt The updatedAt */
  @JsonProperty("updatedAt")
  public void setUpdatedAt(Long updatedAt) {
    this.updatedAt = updatedAt;
  }

  /** @return The lastReceivedAt */
  @JsonProperty("lastReceivedAt")
  public Long getLastReceivedAt() {
    return lastReceivedAt;
  }

  /** @param lastReceivedAt The lastReceivedAt */
  @JsonProperty("lastReceivedAt")
  public void setLastReceivedAt(Long lastReceivedAt) {
    this.lastReceivedAt = lastReceivedAt;
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
