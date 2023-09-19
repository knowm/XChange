package org.knowm.xchange.bybit;

import lombok.Getter;
import lombok.Setter;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitAccountType;

public class BybitExchangeSpecification extends ExchangeSpecification {

  @Getter @Setter private BybitAccountType accountType = BybitAccountType.UNIFIED;

  public BybitExchangeSpecification(Class<? extends Exchange> exchangeClass) {
    super(exchangeClass);
  }
}
