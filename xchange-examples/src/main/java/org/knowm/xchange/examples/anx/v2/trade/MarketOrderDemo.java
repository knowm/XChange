package org.knowm.xchange.examples.anx.v2.trade;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.examples.anx.v2.ANXExamplesUtils;
import org.knowm.xchange.service.trade.TradeService;

/** Test placing a market order at MtGox */
public class MarketOrderDemo {

  public static void main(String[] args) throws IOException {

    Exchange anx = ANXExamplesUtils.createExchange();

    // Interested in the private trading functionality (authentication)
    TradeService tradeService = anx.getTradeService();

    // place a market order for 1 Bitcoin at market price
    OrderType orderType = (OrderType.ASK);
    BigDecimal tradeableAmount = new BigDecimal("0.01");

    MarketOrder marketOrder =
        new MarketOrder(orderType, tradeableAmount, CurrencyPair.BTC_USD, new Date());

    String orderID = tradeService.placeMarketOrder(marketOrder);
    System.out.println("Market Order return value: " + orderID);
  }
}
