package org.knowm.xchange.examples.coingi.trade;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.examples.coingi.CoingiDemoUtils;
import org.knowm.xchange.service.trade.TradeService;

public class CoingBuyBtcForEurDemo {
  public static void main(String[] args) throws IOException {
    Exchange coingi = CoingiDemoUtils.createExchange();

    CurrencyPair currencyPair = CurrencyPair.BTC_EUR;
    BigDecimal volume = BigDecimal.valueOf(0.02); // buy 0.02BTC
    BigDecimal maxPrice = BigDecimal.valueOf(5000); // max price of 1 BTC

    // OrderType.BID = Buy, OrderType.ASK = Sell
    LimitOrder limitOrder =
        new LimitOrder(Order.OrderType.BID, volume, currencyPair, null, null, maxPrice);
    TradeService tradeService = coingi.getTradeService();
    String orderId = tradeService.placeLimitOrder(limitOrder);
    System.out.printf(
        "Order ID: %s. (Buy %.8f BTC when 1 BTC costs no more than %.2f EUR)\n",
        orderId, volume, maxPrice);
  }
}
