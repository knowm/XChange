package org.knowm.xchange.examples.btctrade.trade;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.btctrade.BTCTradeExchange;
import org.knowm.xchange.btctrade.dto.trade.BTCTradeOrder;
import org.knowm.xchange.btctrade.service.BTCTradeTradeServiceRaw;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.service.trade.TradeService;

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

    TradeService tradeService = exchange.getTradeService();

    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println("Open orders: " + openOrders);

    Trades trades = tradeService.getTradeHistory(tradeService.createTradeHistoryParams());
    System.out.println("Trades: " + trades);
  }

  private static void raw(Exchange exchange) throws IOException {

    BTCTradeTradeServiceRaw tradeService = (BTCTradeTradeServiceRaw) exchange.getTradeService();

    BTCTradeOrder[] orders = tradeService.getBTCTradeOrders(0, "open");
    System.out.println("Open orders: " + orders.length);

    String orderId = orders.length > 0 ? orders[0].getId() : "1";
    BTCTradeOrder order = tradeService.getBTCTradeOrder(orderId);
    System.out.println("Order status: " + order.getStatus());
  }
}
