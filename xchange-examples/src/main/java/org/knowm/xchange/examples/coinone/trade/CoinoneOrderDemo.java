package org.knowm.xchange.examples.coinone.trade;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinone.dto.trade.CoinoneTradeCancelRequest;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.examples.coinone.CoinoneDemoUtils;
import org.knowm.xchange.service.trade.TradeService;

public class CoinoneOrderDemo {

  public static void main(String[] args) throws IOException {

    Exchange coinone = CoinoneDemoUtils.createExchange();
    TradeService tradeService = coinone.getTradeService();
    generic(tradeService);
  }

  private static void generic(TradeService tradeService) throws IOException {

    // place a limit buy order
    LimitOrder limitOrder =
        new LimitOrder(
            (OrderType.BID),
            new BigDecimal("0.01"),
            new CurrencyPair(Currency.ETH, Currency.KRW),
            null,
            null,
            new BigDecimal("500000.00"));
    String limitOrderReturnValue = tradeService.placeLimitOrder(limitOrder);

    System.out.println("Limit Order return value: " + limitOrderReturnValue);

    // Cancel the added order
    CoinoneTradeCancelRequest orderParams =
        new CoinoneTradeCancelRequest(limitOrderReturnValue, limitOrder);
    boolean cancelResult = tradeService.cancelOrder(orderParams);
    System.out.println("Canceling returned " + cancelResult);

    // place a limit sell order
    limitOrder =
        new LimitOrder(
            (OrderType.ASK),
            new BigDecimal("0.01"),
            new CurrencyPair(Currency.ETH, Currency.KRW),
            null,
            null,
            new BigDecimal("1000000.00"));
    limitOrderReturnValue = tradeService.placeLimitOrder(limitOrder);

    System.out.println("Limit Order return value: " + limitOrderReturnValue);

    // Cancel the added order
    orderParams = new CoinoneTradeCancelRequest(limitOrderReturnValue, limitOrder);
    cancelResult = tradeService.cancelOrder(orderParams);
    System.out.println("Canceling returned " + cancelResult);
  }
}
