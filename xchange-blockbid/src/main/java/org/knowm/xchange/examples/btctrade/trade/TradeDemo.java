package org.knowm.xchange.examples.btctrade.trade;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.btctrade.BTCTradeExchange;
import org.knowm.xchange.btctrade.service.BTCTradeTradeServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.service.trade.TradeService;

public class TradeDemo {

  private static final BigDecimal MIN_AMOUNT_PER_ORDER = new BigDecimal("0.001");
  private static final BigDecimal MIN_PRICE_IN_CNY = new BigDecimal("0.1");

  public static void main(String[] args) throws IOException {

    // API key with All Permissions.
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

    // Bid.
    String orderId =
        tradeService.placeLimitOrder(
            new LimitOrder(
                OrderType.BID,
                MIN_AMOUNT_PER_ORDER.multiply(new BigDecimal("1000")),
                CurrencyPair.BTC_CNY,
                null,
                null,
                MIN_PRICE_IN_CNY));

    // Cancel.
    tradeService.cancelOrder(orderId);

    // Ask.
    orderId =
        tradeService.placeLimitOrder(
            new LimitOrder(
                OrderType.ASK,
                MIN_AMOUNT_PER_ORDER,
                CurrencyPair.BTC_CNY,
                null,
                null,
                new BigDecimal(Integer.MAX_VALUE)));

    // Cancel.
    tradeService.cancelOrder(orderId);
  }

  private static void raw(Exchange exchange) throws IOException {

    BTCTradeTradeServiceRaw tradeService = (BTCTradeTradeServiceRaw) exchange.getTradeService();

    // Buy.
    String orderId =
        tradeService
            .buy(MIN_AMOUNT_PER_ORDER.multiply(new BigDecimal("1000")), MIN_PRICE_IN_CNY)
            .getId();

    // Cancel.
    tradeService.cancelBTCTradeOrder(orderId);

    // Sell.
    orderId = tradeService.sell(MIN_AMOUNT_PER_ORDER, new BigDecimal(Integer.MAX_VALUE)).getId();

    // Cancel.
    tradeService.cancelBTCTradeOrder(orderId);
  }
}
