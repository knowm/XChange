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
public class BitmexWallet extends AbstractHttpResponseAware {

  @JsonProperty("account")
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

  @JsonProperty("timestamp")
  ZonedDateTime timestamp;

  @JsonCreator
  public BitmexWallet(
      @JsonProperty("currency") String currencyCode,
      @JsonProperty("deposited") BigDecimal deposited,
      @JsonProperty("withdrawn") BigDecimal withdrawn,
      @JsonProperty("transferIn") BigDecimal transferIn,
      @JsonProperty("transferOut") BigDecimal transferOut,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("pendingCredit") BigDecimal pendingCredit,
      @JsonProperty("pendingDebit") BigDecimal pendingDebit,
      @JsonProperty("confirmedDebit") BigDecimal confirmedDebit) {

    this.currency = BitmexAdapters.bitmexCodeToCurrency(currencyCode);

    // scale values
    this.deposited = BitmexAdapters.scaleToLocalAmount(deposited, currency);
    this.withdrawn = BitmexAdapters.scaleToLocalAmount(withdrawn, currency);
    this.transferIn = BitmexAdapters.scaleToLocalAmount(transferIn, currency);
    this.transferOut = BitmexAdapters.scaleToLocalAmount(transferOut, currency);
    this.amount = BitmexAdapters.scaleToLocalAmount(amount, currency);
    this.pendingCredit = BitmexAdapters.scaleToLocalAmount(pendingCredit, currency);
    this.pendingDebit = BitmexAdapters.scaleToLocalAmount(pendingDebit, currency);
    this.confirmedDebit = BitmexAdapters.scaleToLocalAmount(confirmedDebit, currency);
  }
}
