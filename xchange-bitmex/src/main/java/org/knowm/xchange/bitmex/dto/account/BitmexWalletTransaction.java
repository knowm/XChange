package org.knowm.xchange.bitmex.dto.account;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.knowm.xchange.bitmex.AbstractHttpResponseAware;
import org.knowm.xchange.bitmex.dto.BitmexDecimalDeserializer;

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
  "walletBalance",
  "marginBalance",
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
  @JsonDeserialize(using = BitmexDecimalDeserializer.class)
  private BigDecimal amount;

  @JsonProperty("fee")
  @JsonDeserialize(using = BitmexDecimalDeserializer.class)
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
  private Date transactTime;

  @JsonProperty("walletBalance")
  @JsonDeserialize(using = BitmexDecimalDeserializer.class)
  private BigDecimal walletBalance;

  @JsonProperty("marginBalance")
  @JsonDeserialize(using = BitmexDecimalDeserializer.class)
  private BigDecimal marginBalance;

  @JsonProperty("timestamp")
  private Date timestamp;

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

  public Date getTransactTime() {
    return transactTime;
  }

  public BigDecimal getWalletBalance() {
    return walletBalance;
  }

  public BigDecimal getMarginBalance() {
    return marginBalance;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public Map<String, Object> getAdditionalProperties() {
    return additionalProperties;
  }
}
