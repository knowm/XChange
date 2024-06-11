package org.knowm.xchange.bitstamp.dto.account;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.currency.Currency;

@Getter
@ToString
@Builder
@AllArgsConstructor
public class WithdrawalFee {

  private String network;

  private BigDecimal fee;

  private Currency currency;

  public WithdrawalFee() {
    super();
  }
}
