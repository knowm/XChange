package org.knowm.xchange.gateio.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.gateio.GateioExchangeWiremock;
import org.knowm.xchange.gateio.dto.account.GateioOrder;

class GateioTradeServiceRawTest extends GateioExchangeWiremock {

  GateioTradeServiceRaw gateioTradeServiceRaw = (GateioTradeServiceRaw) exchange.getTradeService();

  GateioOrder sampleMarketOrder = GateioOrder.builder()
      .id("342251629898")
      .currencyPair(CurrencyPair.BTC_USDT)
      .clientOrderId("t-valid-market-buy-order")
      .amendText("-")
      .type("market")
      .account("spot")
      .side(OrderType.BID)
      .timeInForce("ioc")
      .amount(BigDecimal.valueOf(20))
      .createdAt(Instant.parse("2023-06-03T22:07:38.451Z"))
      .updatedAt(Instant.parse("2023-06-03T22:07:38.451Z"))
      .status("closed")
      .icebergAmount(BigDecimal.ZERO)
      .amountLeftToFill(new BigDecimal("1.07319"))
      .filledTotalQuote(new BigDecimal("18.92681"))
      .avgDealPrice(new BigDecimal("27038.3"))
      .fee(new BigDecimal("0.0000014"))
      .price(BigDecimal.ZERO)
      .feeCurrency("BTC")
      .pointFee(BigDecimal.ZERO)
      .gtFee(BigDecimal.ZERO)
      .gtMakerFee(BigDecimal.ZERO)
      .gtTakerFee(BigDecimal.ZERO)
      .rebatedFee(BigDecimal.ZERO)
      .gtDiscount(false)
      .rebatedFeeCurrency("USDT")
      .finishAs("filled")
      .build();



  @Test
  void listOrders() throws IOException {
    List<GateioOrder> orders = gateioTradeServiceRaw.listOrders(CurrencyPair.BTC_USDT, OrderStatus.OPEN);

    assertThat(orders).hasSize(1);
    assertThat(orders.get(0).getId()).isEqualTo("339440374909");
    assertThat(orders.get(0).getStatus()).isEqualTo("open");

  }


  @Test
  void valid_market_buy_order() throws IOException {
    GateioOrder gateioOrder = GateioOrder.builder()
        .currencyPair(CurrencyPair.BTC_USDT)
        .clientOrderId("t-valid-market-buy-order")
        .type("market")
        .account("spot")
        .side(OrderType.BID)
        .timeInForce("ioc")
        .amount(BigDecimal.valueOf(20))
        .build();

    GateioOrder actualResponse = gateioTradeServiceRaw.createOrder(gateioOrder);
    assertThat(actualResponse).usingRecursiveComparison().isEqualTo(sampleMarketOrder);

  }


  @Test
  void valid_market_sell_order() throws IOException {
    GateioOrder gateioOrder = GateioOrder.builder()
        .currencyPair(CurrencyPair.BTC_USDT)
        .clientOrderId("t-valid-market-sell-order")
        .type("market")
        .account("spot")
        .side(OrderType.ASK)
        .timeInForce("ioc")
        .amount(new BigDecimal("0.0007"))
        .build();


    GateioOrder actualResponse = gateioTradeServiceRaw.createOrder(gateioOrder);

    GateioOrder expectedResponse = GateioOrder.builder()
        .id("342260949533")
        .currencyPair(CurrencyPair.BTC_USDT)
        .clientOrderId("t-valid-market-sell-order")
        .amendText("-")
        .type("market")
        .account("spot")
        .side(OrderType.ASK)
        .timeInForce("ioc")
        .amount(new BigDecimal("0.0007"))
        .createdAt(Instant.parse("2023-06-03T22:33:21.743Z"))
        .updatedAt(Instant.parse("2023-06-03T22:33:21.743Z"))
        .status("closed")
        .icebergAmount(BigDecimal.ZERO)
        .amountLeftToFill(BigDecimal.ZERO)
        .filledTotalQuote(new BigDecimal("18.94382"))
        .avgDealPrice(new BigDecimal("27062.6"))
        .fee(new BigDecimal("0.03788764"))
        .price(BigDecimal.ZERO)
        .feeCurrency("USDT")
        .pointFee(BigDecimal.ZERO)
        .gtFee(BigDecimal.ZERO)
        .gtMakerFee(BigDecimal.ZERO)
        .gtTakerFee(BigDecimal.ZERO)
        .rebatedFee(BigDecimal.ZERO)
        .gtDiscount(false)
        .rebatedFeeCurrency("BTC")
        .finishAs("filled")
        .build();

    assertThat(actualResponse).usingRecursiveComparison().isEqualTo(expectedResponse);

  }


  @Test
  void order_details() throws IOException {
    GateioOrder actualResponse = gateioTradeServiceRaw.getOrder("342251629898", CurrencyPair.BTC_USDT);

    assertThat(actualResponse).usingRecursiveComparison().isEqualTo(sampleMarketOrder);
  }


}