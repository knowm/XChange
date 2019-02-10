package org.knowm.xchange.bitmex.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.knowm.xchange.bitmex.AbstractHttpResponseAware;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "transactID",
  "account",
  "currency",
  "transactType",
  "amount",
  "fee",
  "transactStatus",
  "address",
  "tx",
  "text",
  "transactTime",
  "timestamp"
})
public final class BitmexWalletTransaction extends AbstractHttpResponseAware {

  @JsonProperty("transactID")
  private String transactID;

  @JsonProperty("account")
  private Integer account;

  @JsonProperty("currency")
  private String currency;

  @JsonProperty("transactType")
  private String transactType;

  @JsonProperty("amount")
  private BigDecimal amount;

  @JsonProperty("fee")
  private BigDecimal fee;

  @JsonProperty("transactStatus")
  private String transactStatus;

  @JsonProperty("address")
  private String address;

  @JsonProperty("tx")
  private String tx;

  @JsonProperty("text")
  private String text;

  @JsonProperty("transactTime")
  private String transactTime;

  @JsonProperty("timestamp")
  private String timestamp;

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<>();

  public String getTransactID() {
    return transactID;
  }

  public Integer getAccount() {
    return account;
  }

  public String getCurrency() {
    return currency;
  }

  public String getTransactType() {
    return transactType;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public BigDecimal getFee() {
    return fee;
  }

  public String getTransactStatus() {
    return transactStatus;
  }

  public String getAddress() {
    return address;
  }

  public String getTx() {
    return tx;
  }

  public String getText() {
    return text;
  }

  public String getTransactTime() {
    return transactTime;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public Map<String, Object> getAdditionalProperties() {
    return additionalProperties;
  }
}
