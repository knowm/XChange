package com.xeiam.xchange.examples.btctrade.trade;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btctrade.BTCTradeExchange;
import com.xeiam.xchange.btctrade.dto.trade.BTCTradeOrder;
import com.xeiam.xchange.btctrade.service.polling.BTCTradeTradeServiceRaw;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;

public class TradeHistoryDemo {

  public static void main(String[] args) throws IOException {

    // API key with Read Only Permission or All Permissions.
    String publicKey = args[0];
    String privateKey = args[1];

    ExchangeSpecification spec = new ExchangeSpecification(BTCTradeExchange.class);
    spec.setApiKey(publicKey);
    spec.setSecretKey(privateKey);

    Exchange btcTrade = ExchangeFactory.INSTANCE.createExchange(spec);
    generic(btcTrade);
    raw(btcTrade);
  }

  private static void generic(Exchange exchange) throws IOException {

    PollingTradeService tradeService = exchange.getPollingTradeService();

    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println("Open orders: " + openOrders);

    Trades trades = tradeService.getTradeHistory();
    System.out.println("Trades: " + trades);
  }

  private static void raw(Exchange exchange) throws IOException {

    BTCTradeTradeServiceRaw tradeService = (BTCTradeTradeServiceRaw) exchange.getPollingTradeService();

    BTCTradeOrder[] orders = tradeService.getBTCTradeOrders(0, "open");
    System.out.println("Open orders: " + orders.length);

    String orderId = orders.length > 0 ? orders[0].getId() : "1";
    BTCTradeOrder order = tradeService.getBTCTradeOrder(orderId);
    System.out.println("Order status: " + order.getStatus());
  }

}
