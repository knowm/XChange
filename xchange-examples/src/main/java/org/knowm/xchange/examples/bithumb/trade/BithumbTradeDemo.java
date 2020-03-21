package org.knowm.xchange.examples.bithumb.trade;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bithumb.BithumbException;
import org.knowm.xchange.bithumb.dto.account.BithumbOrder;
import org.knowm.xchange.bithumb.service.BithumbTradeServiceRaw;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.examples.bithumb.BithumbDemoUtils;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByPairAndIdParams;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BithumbTradeDemo {

  private static final Logger log = LoggerFactory.getLogger(BithumbTradeDemo.class);

  public static final CurrencyPair CURRENCY_PAIR = new CurrencyPair(Currency.XRP, Currency.KRW);

  public static void main(String[] args) throws IOException, InterruptedException {

    final Exchange exchange = BithumbDemoUtils.createExchange();
    final TradeService tradeService = exchange.getTradeService();

    generic(tradeService);
    raw((BithumbTradeServiceRaw) tradeService);
  }

  private static void generic(TradeService tradeService) throws IOException, InterruptedException {

    log.debug("{}", "----------GENERIC----------");

    final OpenOrdersParams openOrders = new DefaultOpenOrdersParamCurrencyPair(CURRENCY_PAIR);
    try {
      log.debug("{}", tradeService.getOpenOrders(openOrders));
    } catch (ExchangeException e) {
      log.debug("", e);
    }

    final TradeHistoryParams tradeHistoryParams = new DefaultTradeHistoryParamCurrencyPair();
    log.debug("{}", tradeService.getTradeHistory(tradeHistoryParams));

    final LimitOrder limitOrderBuy =
        new LimitOrder.Builder(Order.OrderType.BID, CURRENCY_PAIR)
            .originalAmount(BigDecimal.valueOf(10))
            .limitPrice(BigDecimal.valueOf(100))
            .build();
    log.debug("{}", tradeService.placeLimitOrder(limitOrderBuy));

    Thread.sleep(3000); // wait for order to propagate

    final LimitOrder limitOrderSell =
        new LimitOrder.Builder(Order.OrderType.ASK, CURRENCY_PAIR)
            .originalAmount(BigDecimal.valueOf(10))
            .limitPrice(BigDecimal.valueOf(600))
            .build();
    log.debug("{}", tradeService.placeLimitOrder(limitOrderSell));

    Thread.sleep(3000); // wait for order to propagate

    tradeService.getOpenOrders(openOrders).getOpenOrders().stream()
        .map(Order::getId)
        .map(
            orderId ->
                new CancelOrderByPairAndIdParams() {
                  @Override
                  public String getOrderId() {
                    return orderId;
                  }

                  @Override
                  public CurrencyPair getCurrencyPair() {
                    return CURRENCY_PAIR;
                  }
                })
        .forEach(
            param -> {
              try {
                log.debug("{}", tradeService.cancelOrder(param));
              } catch (IOException ignored) {
              }
            });
    //        log.debug("{}",tradeService.placeMarketOrder(new MarketOrder(Order.OrderType.ASK,
    // BigDecimal.valueOf(10), CURRENCY_PAIR)));
  }

  private static void raw(BithumbTradeServiceRaw tradeServiceRaw)
      throws IOException, InterruptedException {

    log.debug("{}", "----------RAW----------");
    final OpenOrdersParams openOrders = new DefaultOpenOrdersParamCurrencyPair(CURRENCY_PAIR);
    try {
      log.debug("{}", tradeServiceRaw.getBithumbOrders(CURRENCY_PAIR));
    } catch (BithumbException e) {
      log.debug("", e);
    }

    log.debug("{}", tradeServiceRaw.getBithumbUserTransactions(CURRENCY_PAIR));

    final LimitOrder limitOrderBuy =
        new LimitOrder.Builder(Order.OrderType.BID, CURRENCY_PAIR)
            .originalAmount(BigDecimal.valueOf(10))
            .limitPrice(BigDecimal.valueOf(100))
            .build();
    log.debug("{}", tradeServiceRaw.placeBithumbLimitOrder(limitOrderBuy));

    Thread.sleep(3000); // wait for order to propagate

    final LimitOrder limitOrderSell =
        new LimitOrder.Builder(Order.OrderType.ASK, CURRENCY_PAIR)
            .originalAmount(BigDecimal.valueOf(10))
            .limitPrice(BigDecimal.valueOf(600))
            .build();
    log.debug("{}", tradeServiceRaw.placeBithumbLimitOrder(limitOrderSell));

    Thread.sleep(3000); // wait for order to propagate

    tradeServiceRaw.getBithumbOrders(CURRENCY_PAIR).getData().stream()
        .map(BithumbOrder::getOrderId)
        .forEach(
            orderId -> {
              try {
                log.debug("{}", tradeServiceRaw.cancelBithumbOrder(orderId, CURRENCY_PAIR));
              } catch (IOException ignored) {
              }
            });
    //        log.debug("{}", tradeServiceRaw.placeBithumbMarketOrder(new
    // MarketOrder(Order.OrderType.ASK, BigDecimal.valueOf(10), CURRENCY_PAIR)));
  }
}
