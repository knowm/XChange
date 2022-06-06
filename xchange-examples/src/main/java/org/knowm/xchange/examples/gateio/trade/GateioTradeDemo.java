package org.knowm.xchange.examples.gateio.trade;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.examples.gateio.GateioDemoUtils;
import org.knowm.xchange.gateio.dto.GateioOrderType;
import org.knowm.xchange.gateio.dto.trade.GateioOpenOrder;
import org.knowm.xchange.gateio.dto.trade.GateioOpenOrders;
import org.knowm.xchange.gateio.dto.trade.GateioOrderStatus;
import org.knowm.xchange.gateio.dto.trade.GateioTrade;
import org.knowm.xchange.gateio.service.GateioTradeServiceRaw;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamCurrencyPair;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;

public class GateioTradeDemo {

  public static void main(String[] args) throws IOException, InterruptedException {

    Exchange exchange = GateioDemoUtils.createExchange();
    TradeService tradeService = exchange.getTradeService();

    generic(tradeService);
    raw((GateioTradeServiceRaw) tradeService);
  }

  private static void generic(TradeService tradeService) throws IOException, InterruptedException {

    LimitOrder limitOrder =
        new LimitOrder(
            OrderType.ASK,
            new BigDecimal("0.384"),
            CurrencyPair.LTC_BTC,
            "",
            null,
            new BigDecimal("0.0265"));
    String orderId = tradeService.placeLimitOrder(limitOrder);
    System.out.println(
        orderId); // Returned order id is currently broken for Gateio, rely on open orders instead//
    // for demo :(

    Thread.sleep(2000); // wait for Gateio's back-end to propagate the order

    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println(openOrders);

    List<LimitOrder> openOrdersList = openOrders.getOpenOrders();
    if (!openOrdersList.isEmpty()) {
      String existingOrderId = openOrdersList.get(0).getId();

      boolean isCancelled = tradeService.cancelOrder(existingOrderId);
      System.out.println(isCancelled);
    }

    Thread.sleep(2000); // wait for Gateio's back-end to propagate the cancelled order

    openOrders = tradeService.getOpenOrders();
    System.out.println(openOrders);

    Thread.sleep(2000);

    Trades tradeHistory =
        tradeService.getTradeHistory(
            new DefaultTradeHistoryParamCurrencyPair(CurrencyPair.LTC_BTC));
    System.out.println(tradeHistory);
  }

  private static void raw(GateioTradeServiceRaw tradeService)
      throws IOException, InterruptedException {

    String placedOrderId =
        tradeService.placeGateioLimitOrder(
            CurrencyPair.LTC_BTC,
            GateioOrderType.SELL,
            new BigDecimal("0.0265"),
            new BigDecimal("0.384"));
    System.out.println(placedOrderId);

    Thread.sleep(2000); // wait for Gateio's back-end to propagate the order

    GateioOpenOrders openOrders = tradeService.getGateioOpenOrders();
    System.out.println(openOrders);

    List<GateioOpenOrder> openOrdersList = openOrders.getOrders();
    if (!openOrdersList.isEmpty()) {
      String existingOrderId = openOrdersList.get(0).getId();
      GateioOrderStatus orderStatus =
          tradeService.getGateioOrderStatus(existingOrderId, CurrencyPair.LTC_BTC);
      System.out.println(orderStatus);

      boolean isCancelled =
          tradeService.cancelOrder(
              existingOrderId,
              CurrencyPairDeserializer.getCurrencyPairFromString(
                  openOrdersList.get(0).getCurrencyPair()));
      System.out.println(isCancelled);
    }

    Thread.sleep(2000); // wait for Gateio's back-end to propagate the cancelled order

    openOrders = tradeService.getGateioOpenOrders();
    System.out.println(openOrders);

    List<GateioTrade> tradeHistory =
        tradeService.getGateioTradeHistory(CurrencyPair.LTC_BTC).getTrades();
    System.out.println(tradeHistory);
  }
}
