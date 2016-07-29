package org.knowm.xchange.examples.mexbt.trade;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.examples.mexbt.MeXBTDemoUtils;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.mexbt.service.polling.MeXBTTradeService.MeXBTTradeHistoryParams;
import org.knowm.xchange.service.polling.trade.PollingTradeService;

public class TradeServiceDemo {

  public static void main(String[] args) throws ExchangeException, IOException {
    Exchange exchange = MeXBTDemoUtils.createExchange(args);
    PollingTradeService tradeService = exchange.getPollingTradeService();

    // String orderId = tradeService.placeMarketOrder(new MarketOrder.Builder(OrderType.BID, CurrencyPair.BTC_MXN).tradableAmount(BigDecimal.ONE).build());
    // System.out.println(orderId);

    // String orderId = tradeService.placeLimitOrder(new LimitOrder.Builder(OrderType.BID, CurrencyPair.BTC_MXN).tradableAmount(BigDecimal.ONE).limitPrice(BigDecimal.TEN).build());
    // System.out.println(orderId);

    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println(openOrders);

    MeXBTTradeHistoryParams params = (MeXBTTradeHistoryParams) tradeService.createTradeHistoryParams();
    params.setCurrencyPair(CurrencyPair.BTC_MXN);
    UserTrades userTrades = tradeService.getTradeHistory(params);
    System.out.println(userTrades);
  }

}
