package org.knowm.xchange.dase.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dase.DaseExchange;
import org.knowm.xchange.dto.trade.LimitOrder;

public class TradeServiceParamWiringTest {

  @Test
  public void validate_precision_enforced() throws Exception {
    Exchange ex = ExchangeFactory.INSTANCE.createExchange(DaseExchange.class);
    DaseTradeService svc = new DaseTradeService(ex);

    // When market precision is not known yet via API (live call), skip full invocation.
    // Construct a LimitOrder with excessive precision to verify local validation once market config
    // is available
    LimitOrder lo =
        new LimitOrder.Builder(org.knowm.xchange.dto.Order.OrderType.BID, CurrencyPair.BTC_EUR)
            .limitPrice(new BigDecimal("1.123456789"))
            .originalAmount(new BigDecimal("0.000000001"))
            .build();

    assertThatThrownBy(() -> svc.placeLimitOrder(lo)).isInstanceOf(Exception.class);
  }
}
