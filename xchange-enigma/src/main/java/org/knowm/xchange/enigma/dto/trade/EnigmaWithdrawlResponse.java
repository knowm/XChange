package org.knowm.xchange.enigma.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.enigma.dto.BaseResponse;

public class EnigmaWithdrawlResponse extends BaseResponse {

  private String withdrawlType;
  private BigDecimal amount;
  private String currency;
  private Date sentAt;
  private String withdrawlKey;

  public EnigmaWithdrawlResponse(
      @JsonProperty("code") Integer code,
      @JsonProperty("message") String message,
      @JsonProperty("result") Boolean result,
      @JsonProperty("withdrawal_type") String withdrawlType,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("currency") String currency,
      @JsonProperty("sent_at") Date sentAt,
      @JsonProperty("withdrawal_key") String withdrawlKey) {
    super(code, message, result);
    this.withdrawlType = withdrawlType;
    this.amount = amount;
    this.currency = currency;
    this.sentAt = sentAt;
    this.withdrawlKey = withdrawlKey;
  }

  public String getWithdrawlType() {
    return this.withdrawlType;
  }

  public Date getSentAt() {
    return this.sentAt;
  }

  public String getWithdrawlKey() {
    return this.withdrawlKey;
  }

  public BigDecimal getAmount() {
    return this.amount;
  }

  public String getCurrency() {
    return this.currency;
  }
}
