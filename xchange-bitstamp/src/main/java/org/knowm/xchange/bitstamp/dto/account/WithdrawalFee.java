package org.knowm.xchange.bitstamp.dto.account;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.currency.Currency;

@Jacksonized
@Value
@Builder
public class WithdrawalFee {

  String network;

  BigDecimal fee;

  Currency currency;
}
