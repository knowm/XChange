package com.xeiam.xchange.examples.anx.v2.trade;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.examples.anx.v2.ANXExamplesUtils;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;

/**
 * Test placing a market order at MtGox
 */
public class MarketOrderDemo {

  public static void main(String[] args) throws IOException {

    Exchange anx = ANXExamplesUtils.createExchange();

    // Interested in the private trading functionality (authentication)
    PollingTradeService tradeService = anx.getPollingTradeService();

    // place a market order for 1 Bitcoin at market price
    OrderType orderType = (OrderType.ASK);
    BigDecimal tradeableAmount = new BigDecimal("0.01");

    MarketOrder marketOrder = new MarketOrder(orderType, tradeableAmount, CurrencyPair.BTC_USD, new Date());

    String orderID = tradeService.placeMarketOrder(marketOrder);
    System.out.println("Market Order return value: " + orderID);

  }
}
