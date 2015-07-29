package com.xeiam.xchange.ripple.dto.account.trade;

import static org.fest.assertions.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Test;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ripple.RippleExchange;
import com.xeiam.xchange.ripple.service.polling.RippleTradeService;

public class RippleTransactioFeeIntegration {

  @Test
  public void getTransactionFeeTest() {
    final Exchange exchange = ExchangeFactory.INSTANCE.createExchange(RippleExchange.class.getName());
    final RippleTradeService tradeService = (RippleTradeService) exchange.getPollingTradeService();

    final BigDecimal transactionFee = tradeService.getTransactionFee();
    assertThat(transactionFee).isGreaterThan(BigDecimal.ZERO);

    System.out.println(String.format("Ripple network transaction fee is currently %s XRP", transactionFee));
  }
}
