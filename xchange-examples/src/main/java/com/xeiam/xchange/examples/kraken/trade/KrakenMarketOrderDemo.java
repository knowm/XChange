package com.xeiam.xchange.examples.kraken.trade;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.examples.kraken.KrakenExampleUtils;
import com.xeiam.xchange.kraken.dto.trade.KrakenOrderResponse;
import com.xeiam.xchange.kraken.service.polling.KrakenTradeServiceRaw;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;

public class KrakenMarketOrderDemo {

  public static void main(String[] args) throws IOException {

    Exchange krakenExchange = KrakenExampleUtils.createTestExchange();

    generic(krakenExchange);
    raw(krakenExchange);

  }

  private static void generic(Exchange krakenExchange) throws IOException {

    // Interested in the private trading functionality (authentication)
    PollingTradeService tradeService = krakenExchange.getPollingTradeService();

    // place a marketOrder with volume 0.01
    OrderType orderType = (OrderType.BID);
    BigDecimal tradeableAmount = new BigDecimal("0.01");

    MarketOrder marketOrder = new MarketOrder(orderType, tradeableAmount, CurrencyPair.BTC_EUR);

    String orderID = tradeService.placeMarketOrder(marketOrder);
    System.out.println("Market Order ID: " + orderID);
  }

  private static void raw(Exchange krakenExchange) throws IOException {

    // Interested in the private trading functionality (authentication)
    KrakenTradeServiceRaw tradeService = (KrakenTradeServiceRaw) krakenExchange.getPollingTradeService();

    // place a marketOrder with volume 0.01
    OrderType orderType = (OrderType.BID);
    BigDecimal tradeableAmount = new BigDecimal("0.01");

    MarketOrder marketOrder = new MarketOrder(orderType, tradeableAmount, CurrencyPair.BTC_EUR);

    KrakenOrderResponse orderID = tradeService.placeKrakenMarketOrder(marketOrder);
    System.out.println("Market Order ID: " + orderID);
  }
}
