package org.knowm.xchange.okex.v5;

import static org.assertj.core.api.Assertions.assertThat;
import static org.knowm.xchange.currency.CurrencyPair.TRX_USDT;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.okex.v5.dto.trade.OkexTradeParams;
import org.knowm.xchange.okex.v5.service.OkexTradeService;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.orders.DefaultQueryOrderParamInstrument;

@Slf4j
public class OkexExchangeIntegration {
  // Enter your authentication details here to run private endpoint tests
  private static final String API_KEY = "";
  private static final String SECRET_KEY = "";
  private static final String PASSPHRASE = "";

  @Test
  public void testCreateExchangeShouldApplyDefaultSpecification() {
    ExchangeSpecification spec = new OkexExchange().getDefaultExchangeSpecification();
    final Exchange exchange = ExchangeFactory.INSTANCE.createExchange(spec);

    assertThat(exchange.getExchangeSpecification().getSslUri()).isEqualTo("https://www.okex.com");
    assertThat(exchange.getExchangeSpecification().getHost()).isEqualTo("okex.com");
    assertThat(exchange.getExchangeSpecification().getResilience().isRateLimiterEnabled())
        .isEqualTo(false);
    assertThat(exchange.getExchangeSpecification().getResilience().isRetryEnabled())
        .isEqualTo(false);
  }

  @Test
  public void testCreateExchangeShouldApplyResilience() {
    ExchangeSpecification spec = new OkexExchange().getDefaultExchangeSpecification();
    ExchangeSpecification.ResilienceSpecification resilienceSpecification =
        new ExchangeSpecification.ResilienceSpecification();
    resilienceSpecification.setRateLimiterEnabled(true);
    resilienceSpecification.setRetryEnabled(true);
    spec.setResilience(resilienceSpecification);

    final Exchange exchange = ExchangeFactory.INSTANCE.createExchange(spec);

    assertThat(exchange.getExchangeSpecification().getResilience().isRateLimiterEnabled())
        .isEqualTo(true);
    assertThat(exchange.getExchangeSpecification().getResilience().isRetryEnabled())
        .isEqualTo(true);
  }

  @Test
  public void testOrderActions() throws Exception {
    if (!API_KEY.isEmpty() && !SECRET_KEY.isEmpty() && !PASSPHRASE.isEmpty()) {
      ExchangeSpecification spec =
          ExchangeFactory.INSTANCE
              .createExchange(OkexExchange.class)
              .getDefaultExchangeSpecification();
      spec.setApiKey(API_KEY);
      spec.setSecretKey(SECRET_KEY);
      spec.setExchangeSpecificParametersItem("passphrase", PASSPHRASE);

      final Exchange exchange = ExchangeFactory.INSTANCE.createExchange(spec);
      final OkexTradeService okexTradeService = (OkexTradeService) exchange.getTradeService();

      assertThat(exchange.getExchangeSpecification().getSslUri()).isEqualTo("https://www.okex.com");
      assertThat(exchange.getExchangeSpecification().getHost()).isEqualTo("okex.com");

      // Place a single order
      LimitOrder limitOrder =
          new LimitOrder(
              Order.OrderType.ASK, BigDecimal.TEN, TRX_USDT, null, new Date(), new BigDecimal(100));

      String orderId = okexTradeService.placeLimitOrder(limitOrder);
      log.info("Placed orderId: {}", orderId);

      // Amend the above order
      LimitOrder limitOrder2 =
          new LimitOrder(
              Order.OrderType.ASK,
              BigDecimal.TEN,
              TRX_USDT,
              orderId,
              new Date(),
              new BigDecimal(1000));
      String orderId2 = okexTradeService.changeOrder(limitOrder2);
      log.info("Amended orderId: {}", orderId2);

      // Get non-existent Order Detail
      Order failOrder =
          okexTradeService.getOrder(new DefaultQueryOrderParamInstrument(TRX_USDT, "2132465465"));
      log.info("Null Order: {}", failOrder);

      // Get Order Detail
      Order amendedOrder =
          okexTradeService.getOrder(new DefaultQueryOrderParamInstrument(TRX_USDT, orderId2));
      log.info("Amended Order: {}", amendedOrder);

      // Cancel that order
      boolean result =
          exchange
              .getTradeService()
              .cancelOrder(new OkexTradeParams.OkexCancelOrderParams(TRX_USDT, orderId2));
      log.info("Cancellation result: {}", result);

      // Place batch orders
      List<String> orderIds =
          okexTradeService.placeLimitOrder(Arrays.asList(limitOrder, limitOrder, limitOrder));
      log.info("Placed batch orderIds: {}", orderIds);

      // Amend batch orders
      List<LimitOrder> amendOrders = new ArrayList<>();
      for (String id : orderIds) {
        amendOrders.add(
            new LimitOrder(
                Order.OrderType.ASK,
                BigDecimal.TEN,
                TRX_USDT,
                id,
                new Date(),
                new BigDecimal(1000)));
      }
      List<String> amendedOrderIds = okexTradeService.changeOrder(amendOrders);
      log.info("Amended batch orderIds: {}", amendedOrderIds);

      OpenOrders openOrders = okexTradeService.getOpenOrders();
      log.info("Open Orders: {}", openOrders);

      // Cancel batch orders
      List<CancelOrderParams> cancelOrderParams = new ArrayList<>();
      for (String id : orderIds) {
        cancelOrderParams.add(new OkexTradeParams.OkexCancelOrderParams(TRX_USDT, id));
      }
      List<Boolean> results = okexTradeService.cancelOrder(cancelOrderParams);
      log.info("Cancelled order results: {}", results);
    }
  }
}
