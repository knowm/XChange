package org.knowm.xchange.kucoin.dto.account;

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
@JsonPropertyOrder({
  "fee",
  "oid",
  "type",
  "amount",
  "remark",
  "status",
  "address",
  "context",
  "userOid",
  "coinType",
  "createdAt",
  "deletedAt",
  "updatedAt",
  "outerWalletTxid"
})
public class KucoinWalletRecord {

  @JsonProperty("fee")
  private BigDecimal fee;

  @JsonProperty("oid")
  private String oid;

  @JsonProperty("type")
  private KucoinWalletOperation type;

  @JsonProperty("amount")
  private BigDecimal amount;

  @JsonProperty("remark")
  private String remark;

  @JsonProperty("status")
  private KucoinWalletOperationStatus status;

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

  @JsonProperty("outerWalletTxid")
  private String outerWalletTxid;

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /** No args constructor for use in serialization */
  public KucoinWalletRecord() {}

  /**
   * @param coinType
   * @param amount
   * @param address
   * @param userOid
   * @param fee
   * @param outerWalletTxid
   * @param remark
   * @param oid
   * @param type
   * @param createdAt
   * @param deletedAt
   * @param context
   * @param status
   * @param updatedAt
   */
  public KucoinWalletRecord(
      BigDecimal fee,
      String oid,
      KucoinWalletOperation type,
      BigDecimal amount,
      String remark,
      KucoinWalletOperationStatus status,
      String address,
      String context,
      String userOid,
      String coinType,
      Long createdAt,
      Long deletedAt,
      Long updatedAt,
      String outerWalletTxid) {
    super();
    this.fee = fee;
    this.oid = oid;
    this.type = type;
    this.amount = amount;
    this.remark = remark;
    this.status = status;
    this.address = address;
    this.context = context;
    this.userOid = userOid;
    this.coinType = coinType;
    this.createdAt = createdAt;
    this.deletedAt = deletedAt;
    this.updatedAt = updatedAt;
    this.outerWalletTxid = outerWalletTxid;
  }

  /** @return The fee */
  @JsonProperty("fee")
  public BigDecimal getFee() {
    return fee;
  }

  /** @param fee The fee */
  @JsonProperty("fee")
  public void setFee(BigDecimal fee) {
    this.fee = fee;
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

  /** @return The type */
  @JsonProperty("type")
  public KucoinWalletOperation getType() {
    return type;
  }

  /** @param type The type */
  @JsonProperty("type")
  public void setType(KucoinWalletOperation type) {
    this.type = type;
  }

  /** @return The amount */
  @JsonProperty("amount")
  public BigDecimal getAmount() {
    return amount;
  }

  /** @param amount The amount */
  @JsonProperty("amount")
  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  /** @return The remark */
  @JsonProperty("remark")
  public String getRemark() {
    return remark;
  }

  /** @param remark The remark */
  @JsonProperty("remark")
  public void setRemark(String remark) {
    this.remark = remark;
  }

  /** @return The status */
  @JsonProperty("status")
  public KucoinWalletOperationStatus getStatus() {
    return status;
  }

  /** @param status The status */
  @JsonProperty("status")
  public void setStatus(KucoinWalletOperationStatus status) {
    this.status = status;
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
  public String getContext() {
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

  /** @return The outerWalletTxid */
  @JsonProperty("outerWalletTxid")
  public String getOuterWalletTxid() {
    return outerWalletTxid;
  }

  /** @param outerWalletTxid The outerWalletTxid */
  @JsonProperty("outerWalletTxid")
  public void setOuterWalletTxid(String outerWalletTxid) {
    this.outerWalletTxid = outerWalletTxid;
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
