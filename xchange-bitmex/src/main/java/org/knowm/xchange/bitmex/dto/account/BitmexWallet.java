package org.knowm.xchange.bitmex.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.Value;
import org.knowm.xchange.bitmex.AbstractHttpResponseAware;
import org.knowm.xchange.bitmex.BitmexAdapters;
import org.knowm.xchange.currency.Currency;

@Value
public class BitmexWallet extends AbstractHttpResponseAware {

  Integer account;

  Currency currency;

  BigDecimal deposited;

  BigDecimal withdrawn;

  BigDecimal transferIn;

  BigDecimal transferOut;

  BigDecimal amount;

  BigDecimal pendingCredit;

  BigDecimal pendingDebit;

  BigDecimal confirmedDebit;

  ZonedDateTime timestamp;

  @JsonCreator
  public BitmexWallet(@JsonProperty("account") Integer account,
      @JsonProperty("currency") String currencyCode,
      @JsonProperty("deposited") BigDecimal deposited,
      @JsonProperty("withdrawn") BigDecimal withdrawn,
      @JsonProperty("transferIn") BigDecimal transferIn,
      @JsonProperty("transferOut") BigDecimal transferOut,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("pendingCredit") BigDecimal pendingCredit,
      @JsonProperty("pendingDebit") BigDecimal pendingDebit,
      @JsonProperty("confirmedDebit") BigDecimal confirmedDebit,
      @JsonProperty("timestamp") ZonedDateTime timestamp) {

    this.account = account;
    this.currency = BitmexAdapters.bitmexCodeToCurrency(currencyCode);
    this.timestamp = timestamp;

    // scale values
    this.deposited = BitmexAdapters.scaleAmount(deposited, currency);
    this.withdrawn = BitmexAdapters.scaleAmount(withdrawn, currency);
    this.transferIn = BitmexAdapters.scaleAmount(transferIn, currency);
    this.transferOut = BitmexAdapters.scaleAmount(transferOut, currency);
    this.amount = BitmexAdapters.scaleAmount(amount, currency);
    this.pendingCredit = BitmexAdapters.scaleAmount(pendingCredit, currency);
    this.pendingDebit = BitmexAdapters.scaleAmount(pendingDebit, currency);
    this.confirmedDebit = BitmexAdapters.scaleAmount(confirmedDebit, currency);
  }
}
