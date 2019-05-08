package org.knowm.xchange.enigma.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.enigma.dto.BaseResponse;

public class EnigmaWithdrawalResponse extends BaseResponse {

  private String withdrawalType;
  private BigDecimal amount;
  private String currency;
  private Date sentAt;
  private String withdrawalKey;

  public EnigmaWithdrawalResponse(
      @JsonProperty("code") Integer code,
      @JsonProperty("message") String message,
      @JsonProperty("result") Boolean result,
      @JsonProperty("withdrawal_type") String withdrawalType,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("currency") String currency,
      @JsonProperty("sent_at") Date sentAt,
      @JsonProperty("withdrawal_key") String withdrawalKey) {
    super(code, message, result);
    this.withdrawalType = withdrawalType;
    this.amount = amount;
    this.currency = currency;
    this.sentAt = sentAt;
    this.withdrawalKey = withdrawalKey;
  }

  public String getWithdrawalType() {
    return this.withdrawalType;
  }

  public Date getSentAt() {
    return this.sentAt;
  }

  public String getWithdrawalKey() {
    return this.withdrawalKey;
  }

  public BigDecimal getAmount() {
    return this.amount;
  }

  public String getCurrency() {
    return this.currency;
  }
}
