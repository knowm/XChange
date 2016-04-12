package org.knowm.xchange.examples.mexbt.marketdata;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.mexbt.MeXBTExchange;
import org.knowm.xchange.service.polling.marketdata.PollingMarketDataService;

public class OrderBookDemo {

  public static void main(String[] args) throws ExchangeException, IOException {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(MeXBTExchange.class.getName());
    PollingMarketDataService md = exchange.getPollingMarketDataService();
    OrderBook orderBook = md.getOrderBook(CurrencyPair.BTC_MXN);

    if (orderBook.getBids().size() >= 2) {
      BigDecimal bid0 = orderBook.getBids().get(0).getLimitPrice();
      BigDecimal bid1 = orderBook.getBids().get(1).getLimitPrice();
      if (bid0.compareTo(bid1) <= 0) {
        throw new RuntimeException("bids in depth should be ordered from highest to lowest.");
      }
    }
    if (orderBook.getAsks().size() >= 2) {
      BigDecimal ask0 = orderBook.getAsks().get(0).getLimitPrice();
      BigDecimal ask1 = orderBook.getAsks().get(1).getLimitPrice();
      if (ask0.compareTo(ask1) >= 0) {
        throw new RuntimeException("asks in depth should be ordered from lowest to highest.");
      }
    }

    for (LimitOrder o : orderBook.getBids()) {
      System.out.println(o);
    }
    for (LimitOrder o : orderBook.getAsks()) {
      System.out.println(o);
    }
  }

}
