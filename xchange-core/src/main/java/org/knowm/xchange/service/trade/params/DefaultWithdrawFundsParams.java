package org.knowm.xchange.service.trade.params;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;
import org.knowm.xchange.currency.Currency;

@Value
@NonFinal
@SuperBuilder
@AllArgsConstructor
public class DefaultWithdrawFundsParams implements WithdrawFundsParams {

  String address;

  String addressTag;

  Currency currency;

  BigDecimal amount;

  BigDecimal commission;

}
