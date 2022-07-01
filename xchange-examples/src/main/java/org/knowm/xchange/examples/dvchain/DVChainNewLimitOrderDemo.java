package org.knowm.xchange.examples.dvchain;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dvchain.DVChainExchange;
import org.knowm.xchange.dvchain.dto.trade.DVChainNewLimitOrder;
import org.knowm.xchange.dvchain.dto.trade.DVChainTrade;
import org.knowm.xchange.dvchain.service.DVChainTradeServiceRaw;
import org.knowm.xchange.service.trade.TradeService;

public class DVChainNewLimitOrderDemo {
  public static void main(String[] args) throws IOException {

    // Use the factory to get DVChain exchange API using default settings
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(DVChainExchange.class);

    // Interested in the public market data feed (no authentication)
    TradeService tradeService = exchange.getTradeService();

    generic(tradeService);
    raw((DVChainTradeServiceRaw) tradeService);
  }

  private static void generic(TradeService tradeService) throws IOException {

    // Send out a limit order
    LimitOrder order =
        new LimitOrder(
            Order.OrderType.BID,
            new BigDecimal(1),
            CurrencyPair.BTC_USD,
            "",
            null,
            new BigDecimal("1000"));
    System.out.println("Placing limit order 1@1000 for BTC / USD: ");

    String orderResponse = tradeService.placeLimitOrder(order);

    System.out.println("Received response: " + orderResponse);
  }

  private static void raw(DVChainTradeServiceRaw tradeServiceRaw) throws IOException {

    // Send out a limit order
    DVChainNewLimitOrder order =
        new DVChainNewLimitOrder("Buy", new BigDecimal("1000"), new BigDecimal("1"), "USD");
    System.out.println("Placing limit order 1@1000 for BTC / USD: ");

    DVChainTrade orderResponse = tradeServiceRaw.newDVChainLimitOrder(order);

    System.out.println("Received response: " + orderResponse.toString());
  }
}
