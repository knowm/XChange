package org.knowm.xchange.dase.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dase.DaseExchange;
import org.knowm.xchange.dase.dto.trade.DaseOrder;
import org.knowm.xchange.dase.dto.trade.DaseOrdersListResponse;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.mockito.Mockito;

public class DaseTradeServiceOpenOrdersTest {

  private DaseTradeService createSpyService() {
    Exchange exchange = Mockito.mock(Exchange.class);
    ExchangeSpecification spec = new ExchangeSpecification(DaseExchange.class);
    Mockito.when(exchange.getExchangeSpecification()).thenReturn(spec);
    return spy(new DaseTradeService(exchange));
  }

  @Test
  public void getOpenOrders_filtersToLimitOrders() throws Exception {
    DaseTradeService svc = createSpyService();

    DaseOrder limit =
        new DaseOrder(
            "11111111-1111-1111-1111-111111111111",
            "pf",
            "BTC-EUR",
            "limit",
            "buy",
            "1.0",
            "100.0",
            null,
            "0",
            "0",
            "0",
            "open",
            null,
            1750000000000L,
            null,
            null);
    DaseOrder market =
        new DaseOrder(
            "22222222-2222-2222-2222-222222222222",
            "pf",
            "BTC-EUR",
            "market",
            "sell",
            "0.5",
            null,
            null,
            "0.5",
            "50",
            "100",
            "open",
            null,
            1750000001000L,
            null,
            null);

    DaseOrdersListResponse resp = new DaseOrdersListResponse(Arrays.asList(limit, market));
    doReturn(resp).when(svc).getOrders(any(), any(), any(), any());

    OpenOrders oo =
        svc.getOpenOrders(
            new org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair(
                CurrencyPair.BTC_EUR));

    assertThat(oo.getOpenOrders()).hasSize(1);
    assertThat(oo.getOpenOrders().get(0).getId()).isEqualTo("11111111-1111-1111-1111-111111111111");
  }
}
