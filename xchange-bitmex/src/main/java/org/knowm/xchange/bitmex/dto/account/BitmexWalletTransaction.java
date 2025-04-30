package org.knowm.xchange.bitmex.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.knowm.xchange.bitmex.AbstractHttpResponseAware;
import org.knowm.xchange.bitmex.BitmexAdapters;
import org.knowm.xchange.currency.Currency;

@Data
@Builder
@AllArgsConstructor
public final class BitmexWalletTransaction extends AbstractHttpResponseAware {

  @JsonProperty("transactID")
  private String transactionId;

  @JsonProperty("account")
  private Integer account;

  private Currency currency;

  @JsonProperty("transactType")
  private String transactionType;

  private BigDecimal amount;

  private BigDecimal feeAmount;

  @JsonProperty("transactStatus")
  private String transactionStatus;

  @JsonProperty("address")
  private String address;

  @JsonProperty("tx")
  private String tx;

  @JsonProperty("text")
  private String text;

  private BigDecimal walletBalance;

  private BigDecimal marginBalance;

  @JsonProperty("transactTime")
  private ZonedDateTime createdAt;

  @JsonProperty("timestamp")
  private ZonedDateTime updatedAt;

  @JsonCreator
  public BitmexWalletTransaction(
      @JsonProperty("currency") String currency,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("fee") BigDecimal feeAmount,
      @JsonProperty("marginBalance") BigDecimal marginBalance,
      @JsonProperty("walletBalance") BigDecimal walletBalance) {
    // scale values
    this.currency = BitmexAdapters.bitmexCodeToCurrency(currency);
    this.amount = BitmexAdapters.scaleToLocalAmount(amount, this.currency);
    this.marginBalance = BitmexAdapters.scaleToLocalAmount(marginBalance, this.currency);
    this.walletBalance = BitmexAdapters.scaleToLocalAmount(walletBalance, this.currency);
    this.feeAmount = BitmexAdapters.scaleToLocalAmount(feeAmount, this.currency);
  }
}
