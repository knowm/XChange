package org.knowm.xchange.bitstamp.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;
import org.knowm.xchange.currency.Currency;

@Value
@Builder
public class WithdrawalFee {

  String network;

  BigDecimal fee;

  Currency currency;

  @JsonCreator
  public WithdrawalFee(
      @JsonProperty("network") String network,
      @JsonProperty("fee") BigDecimal fee,
      @JsonProperty("currency") Currency currency) {
    this.network = network;
    this.fee = fee;
    this.currency = currency;
  }
}
