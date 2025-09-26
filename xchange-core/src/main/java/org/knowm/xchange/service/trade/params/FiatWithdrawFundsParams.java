package org.knowm.xchange.service.trade.params;

import java.math.BigDecimal;
import java.util.Map;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.service.trade.params.withdrawals.Beneficiary;

@Value
@NonFinal
@SuperBuilder
public class FiatWithdrawFundsParams implements WithdrawFundsParams {
  BigDecimal amount;
  Currency currency;
  String userReference;
  Beneficiary beneficiary;
  Map<String, Object> customParameters;
}
