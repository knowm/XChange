package org.knowm.xchange.examples.kraken.trade;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.examples.kraken.KrakenExampleUtils;
import org.knowm.xchange.kraken.dto.trade.KrakenOrderResponse;
import org.knowm.xchange.kraken.service.KrakenTradeServiceRaw;
import org.knowm.xchange.service.trade.TradeService;

public class KrakenMarketOrderDemo {

  public static void main(String[] args) throws IOException {

    Exchange krakenExchange = KrakenExampleUtils.createTestExchange();

    generic(krakenExchange);
    raw(krakenExchange);
  }

  private static void generic(Exchange krakenExchange) throws IOException {

    // Interested in the private trading functionality (authentication)
    TradeService tradeService = krakenExchange.getTradeService();

    // place a marketOrder with volume 0.01
    OrderType orderType = (OrderType.BID);
    BigDecimal tradeableAmount = new BigDecimal("0.01");

    MarketOrder marketOrder = new MarketOrder(orderType, tradeableAmount, CurrencyPair.BTC_EUR);

    String orderID = tradeService.placeMarketOrder(marketOrder);
    System.out.println("Market Order ID: " + orderID);
  }

  private static void raw(Exchange krakenExchange) throws IOException {

    // Interested in the private trading functionality (authentication)
    KrakenTradeServiceRaw tradeService = (KrakenTradeServiceRaw) krakenExchange.getTradeService();

    // place a marketOrder with volume 0.01
    OrderType orderType = (OrderType.BID);
    BigDecimal tradeableAmount = new BigDecimal("0.01");

    MarketOrder marketOrder = new MarketOrder(orderType, tradeableAmount, CurrencyPair.BTC_EUR);

    KrakenOrderResponse orderID = tradeService.placeKrakenMarketOrder(marketOrder);
    System.out.println("Market Order ID: " + orderID);
  }
}
