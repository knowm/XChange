package org.knowm.xchange.examples.hitbtc.trade;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.examples.hitbtc.HitbtcExampleUtils;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.hitbtc.dto.trade.HitbtcExecutionReport;
import org.knowm.xchange.hitbtc.dto.trade.HitbtcOrder;
import org.knowm.xchange.hitbtc.dto.trade.HitbtcOwnTrade;
import org.knowm.xchange.hitbtc.service.HitbtcTradeServiceRaw;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamPaging;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HitbtcTradingDemo {

  private static Logger logger = LoggerFactory.getLogger(HitbtcTradingDemo.class);

  public static void main(String[] args) throws IOException {

    Exchange exchange = HitbtcExampleUtils.createSecureExchange();
    TradeService tradeService = exchange.getTradeService();

    System.out.println("--------------------- RAW Data ----------------------");
    raw((HitbtcTradeServiceRaw) tradeService);

    System.out.println("--------------------- Generic Data ------------------");
    generic(tradeService);
  }

  private static void generic(TradeService tradeService) throws IOException {

    DefaultTradeHistoryParamPaging params = new DefaultTradeHistoryParamPaging();
    UserTrades accountInfo = tradeService.getTradeHistory(params);
    System.out.println(accountInfo);

    OpenOrdersParams openOrdersParams = new DefaultOpenOrdersParamCurrencyPair();
    OpenOrders openOrders = tradeService.getOpenOrders(openOrdersParams);
    System.out.println("openOrders =" + openOrders );

    //No funds will cause an exception
    try {
      org.knowm.xchange.dto.trade.MarketOrder marketOrder = new org.knowm.xchange.dto.trade.MarketOrder(
          Order.OrderType.BID, new BigDecimal(0.01), new CurrencyPair("BTC/USD")
      );
      String response = tradeService.placeMarketOrder(marketOrder);
      System.out.println("MarketOrder = " + response);
    }
    catch (ExchangeException ee) {
      logger.error("Error while placing order", ee);
    }
  }

  private static void raw(HitbtcTradeServiceRaw tradeService) throws IOException {

    List<HitbtcOwnTrade> trades = tradeService.getTradeHistoryRaw(0, 100, null);
    System.out.println(trades);

    List<HitbtcOrder> openOrders = tradeService.getOpenOrdersRaw();
    System.out.println("openOrders = " + openOrders );

    //No funds will cause an exception
    try {
      MarketOrder marketOrder = new MarketOrder(Order.OrderType.BID, new BigDecimal(0.01), new CurrencyPair("BTC/USD"));
      HitbtcExecutionReport hitbtcExecutionReport = tradeService.placeMarketOrderRaw(marketOrder);
      System.out.println("hitbtcExecutionReport = " + hitbtcExecutionReport);
    }
    catch (ExchangeException ee) {
      logger.error("Error while placing order", ee);
    }
  }
}
