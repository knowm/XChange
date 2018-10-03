package org.knowm.xchange.ripple.dto.account.trade;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ripple.RippleExchange;
import org.knowm.xchange.ripple.service.RippleTradeService;

public class RippleTransactioFeeIntegration {

  @Test
  public void getTransactionFeeTest() {
    final Exchange exchange =
        ExchangeFactory.INSTANCE.createExchange(RippleExchange.class.getName());
    final RippleTradeService tradeService = (RippleTradeService) exchange.getTradeService();

    final BigDecimal transactionFee = tradeService.getTransactionFee();
    assertThat(transactionFee).isGreaterThan(BigDecimal.ZERO);

    System.out.println(
        String.format("Ripple network transaction fee is currently %s XRP", transactionFee));
  }
}
