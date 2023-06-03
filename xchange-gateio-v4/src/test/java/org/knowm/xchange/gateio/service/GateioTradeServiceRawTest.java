package org.knowm.xchange.gateio.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.gateio.GateioExchangeWiremock;
import org.knowm.xchange.gateio.dto.account.GateioOrder;

class GateioTradeServiceRawTest extends GateioExchangeWiremock {

  GateioTradeServiceRaw gateioTradeServiceRaw = (GateioTradeServiceRaw) exchange.getTradeService();


  @Test
  void listOrders() throws IOException {
    List<GateioOrder> orders = gateioTradeServiceRaw.listOrders(CurrencyPair.BTC_USDT, OrderStatus.OPEN);

    assertThat(orders).hasSize(1);
    assertThat(orders.get(0).getId()).isEqualTo("339440374909");
    assertThat(orders.get(0).getStatus()).isEqualTo("open");

  }

}