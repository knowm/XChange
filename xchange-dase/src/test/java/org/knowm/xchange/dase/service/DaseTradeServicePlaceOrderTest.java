package org.knowm.xchange.dase.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dase.DaseExchange;
import org.knowm.xchange.dase.dto.trade.DaseOrderFlags;
import org.knowm.xchange.dase.dto.trade.DasePlaceOrderInput;
import org.knowm.xchange.dase.dto.trade.DasePlaceOrderResponse;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class DaseTradeServicePlaceOrderTest {

  private DaseTradeService createSpyService() {
    Exchange exchange = Mockito.mock(Exchange.class);
    ExchangeSpecification spec = new ExchangeSpecification(DaseExchange.class);
    Mockito.when(exchange.getExchangeSpecification()).thenReturn(spec);
    return spy(new DaseTradeService(exchange));
  }

  @Test
  public void placeLimitOrder_buildsCorrectBody() throws Exception {
    DaseTradeService svc = createSpyService();

    // Skip remote precision checks
    doNothing().when(svc).validateOrderLimits(any());

    LimitOrder lo =
        new LimitOrder.Builder(Order.OrderType.BID, CurrencyPair.BTC_EUR)
            .originalAmount(new BigDecimal("1.23"))
            .limitPrice(new BigDecimal("456.78"))
            .build();
    lo.addOrderFlag(DaseOrderFlags.POST_ONLY);

    ArgumentCaptor<DasePlaceOrderInput> bodyCap =
        ArgumentCaptor.forClass(DasePlaceOrderInput.class);
    doAnswer(inv -> new DasePlaceOrderResponse("123e4567-e89b-12d3-a456-426614174000"))
        .when(svc)
        .placeOrder(any(DasePlaceOrderInput.class));

    String id = svc.placeLimitOrder(lo);
    assertThat(id).isEqualTo("123e4567-e89b-12d3-a456-426614174000");

    verify(svc).placeOrder(bodyCap.capture());
    DasePlaceOrderInput body = bodyCap.getValue();

    assertThat(body.market).isEqualTo("BTC-EUR");
    assertThat(body.type).isEqualTo("limit");
    assertThat(body.side).isEqualTo("buy");
    assertThat(body.size).isEqualTo("1.23");
    assertThat(body.price).isEqualTo("456.78");
    assertThat(body.postOnly).isTrue();
    assertThat(body.funds).isNull();
  }

  @Test
  public void placeMarketOrder_buy_setsSize() throws Exception {
    DaseTradeService svc = createSpyService();
    doNothing().when(svc).validateOrderLimits(any());

    // MarketOrder.originalAmount = 0.5 ADA (base currency)
    MarketOrder mo =
        new MarketOrder(Order.OrderType.BID, new BigDecimal("0.5"), CurrencyPair.ADA_EUR);

    ArgumentCaptor<DasePlaceOrderInput> bodyCap =
        ArgumentCaptor.forClass(DasePlaceOrderInput.class);
    doAnswer(inv -> new DasePlaceOrderResponse("123e4567-e89b-12d3-a456-426614174001"))
        .when(svc)
        .placeOrder(any(DasePlaceOrderInput.class));

    String id = svc.placeMarketOrder(mo);
    assertThat(id).isEqualTo("123e4567-e89b-12d3-a456-426614174001");

    verify(svc).placeOrder(bodyCap.capture());
    DasePlaceOrderInput body = bodyCap.getValue();

    assertThat(body.market).isEqualTo("ADA-EUR");
    assertThat(body.type).isEqualTo("market");
    assertThat(body.side).isEqualTo("buy");
    // Corrected: market buy uses size (base currency), matching XChange conventions
    assertThat(body.size).isEqualTo("0.5");
    assertThat(body.funds).isNull();
  }

  @Test
  public void placeMarketOrder_sell_setsSize() throws Exception {
    DaseTradeService svc = createSpyService();
    doNothing().when(svc).validateOrderLimits(any());

    MarketOrder mo =
        new MarketOrder(Order.OrderType.ASK, new BigDecimal("0.75"), CurrencyPair.ADA_EUR);

    ArgumentCaptor<DasePlaceOrderInput> bodyCap =
        ArgumentCaptor.forClass(DasePlaceOrderInput.class);
    doAnswer(inv -> new DasePlaceOrderResponse("123e4567-e89b-12d3-a456-426614174002"))
        .when(svc)
        .placeOrder(any(DasePlaceOrderInput.class));

    String id = svc.placeMarketOrder(mo);
    assertThat(id).isEqualTo("123e4567-e89b-12d3-a456-426614174002");

    verify(svc).placeOrder(bodyCap.capture());
    DasePlaceOrderInput body = bodyCap.getValue();

    assertThat(body.market).isEqualTo("ADA-EUR");
    assertThat(body.type).isEqualTo("market");
    assertThat(body.side).isEqualTo("sell");
    assertThat(body.size).isEqualTo("0.75");
    assertThat(body.funds).isNull();
  }
}
