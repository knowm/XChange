package org.knowm.xchange.gateio.service;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.exceptions.FundsExceededException;
import org.knowm.xchange.gateio.GateioExchangeWiremock;

class GateioTradeServiceTest extends GateioExchangeWiremock {

  GateioTradeService gateioTradeService = (GateioTradeService) exchange.getTradeService();


  @Test
  void place_order_not_enough_balance() {
    MarketOrder marketOrder = new MarketOrder.Builder(OrderType.BID, CurrencyPair.BTC_USDT)
        .userReference("t-balance-test")
        .originalAmount(BigDecimal.valueOf(100))
        .build();

    assertThatExceptionOfType(FundsExceededException.class)
        .isThrownBy(() -> gateioTradeService.placeMarketOrder(marketOrder));
  }


}