package org.knowm.xchange.bitstamp.dto.account;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;
import org.knowm.xchange.currency.Currency;

@Value
@Builder
public class WithdrawalFee {

  private String network;

  private BigDecimal fee;

  private Currency currency;
}
