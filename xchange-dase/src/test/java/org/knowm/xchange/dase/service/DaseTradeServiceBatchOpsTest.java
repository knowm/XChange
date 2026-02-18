package org.knowm.xchange.dase.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dase.DaseExchange;
import org.knowm.xchange.dase.dto.trade.DaseBatchGetOrdersResponse;
import org.knowm.xchange.dase.dto.trade.DaseOrder;
import org.knowm.xchange.dto.Order;
import org.mockito.Mockito;

public class DaseTradeServiceBatchOpsTest {

  private DaseTradeService createSpyService() {
    Exchange exchange = Mockito.mock(Exchange.class);
    ExchangeSpecification spec = new ExchangeSpecification(DaseExchange.class);
    Mockito.when(exchange.getExchangeSpecification()).thenReturn(spec);
    return spy(new DaseTradeService(exchange));
  }

  @Test
  public void batchGetOrders_adaptsOrders() throws Exception {
    DaseTradeService svc = createSpyService();

    DaseOrder o1 =
        new DaseOrder(
            "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa",
            "pf",
            "ADA-EUR",
            "limit",
            "buy",
            "10",
            "0.25",
            null,
            "0",
            "0",
            "0",
            "open",
            null,
            1750000000000L,
            null,
            null);
    DaseOrder o2 =
        new DaseOrder(
            "bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb",
            "pf",
            "ADA-EUR",
            "market",
            "sell",
            "5",
            null,
            null,
            "5",
            "1.25",
            "0.25",
            "closed",
            null,
            1750000001000L,
            null,
            null);

    doReturn(new DaseBatchGetOrdersResponse(Arrays.asList(o1, o2)))
        .when(svc)
        .batchGetOrdersRaw(anyList());

    List<Order> out =
        svc.batchGetOrders(
            Arrays.asList(
                "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa", "bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"));
    assertThat(out).hasSize(2);
    assertThat(out.get(0).getId()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
    assertThat(out.get(1).getId()).isEqualTo("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb");
  }

  @Test
  public void batchCancelOrders_forwardsIds() throws Exception {
    DaseTradeService svc = createSpyService();
    doNothing().when(svc).batchCancelOrdersRaw(anyList());

    List<String> ids = Arrays.asList("1", "2", "3");
    svc.batchCancelOrders(ids);
    verify(svc).batchCancelOrdersRaw(eq(ids));
  }

  @Test
  public void cancelAll_forwardsMarket() throws Exception {
    DaseTradeService svc = createSpyService();
    doNothing().when(svc).cancelAllOrdersRaw(eq("ADA-EUR"));

    svc.cancelAll(CurrencyPair.ADA_EUR);
    verify(svc).cancelAllOrdersRaw(eq("ADA-EUR"));
  }
}
