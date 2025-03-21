package org.knowm.xchange.kraken.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class DepostitStatus {

  private final String method;
  private final String aclass;
  private final String asset;
  private final String refid;
  private final String txid;
  private final String info;
  private final BigDecimal amount;
  private final BigDecimal fee;
  private final Date timestamp;
  private final String status;
  private final String statusProp;
  private List<String> originators;

  public DepostitStatus(
      @JsonProperty("method") String method,
      @JsonProperty("aclass") String aclass,
      @JsonProperty("asset") String asset,
      @JsonProperty("refid") String refid,
      @JsonProperty("txid") String txid,
      @JsonProperty("info") String info,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("fee") BigDecimal fee,
      @JsonProperty("time") long unixTimestamp,
      @JsonProperty("status") String status,
      @JsonProperty("status-prop") String statusProp,
      @JsonProperty("originators") List<String> originators) {
    super();
    this.method = method;
    this.aclass = aclass;
    this.asset = asset;
    this.refid = refid;
    this.txid = txid;
    this.info = info;
    this.amount = amount;
    this.fee = fee;
    this.timestamp = new Date(unixTimestamp * 1000);
    this.status = status;
    this.statusProp = statusProp;
    this.originators = originators;
  }

  public String getMethod() {
    return method;
  }

  public String getAclass() {
    return aclass;
  }

  public String getAsset() {
    return asset;
  }

  public String getRefid() {
    return refid;
  }

  public String getTxid() {
    return txid;
  }

  public String getInfo() {
    return info;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public BigDecimal getFee() {
    return fee;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public String getStatus() {
    return status;
  }

  public String getStatusProp() {
    return statusProp;
  }

  public List<String> getOriginators() {
    return originators;
  }

  @Override
  public String toString() {
    return "DepostitStatus [method="
        + method
        + ", aclass="
        + aclass
        + ", asset="
        + asset
        + ", refid="
        + refid
        + ", txid="
        + txid
        + ", info="
        + info
        + ", amount="
        + amount
        + ", fee="
        + fee
        + ", timestamp="
        + timestamp
        + ", status="
        + status
        + ", statusProp="
        + statusProp
        + ", originators="
        + String.join(",", Optional.ofNullable(originators).orElse(new ArrayList<>()))
        + "]";
  }
}
