package com.xeiam.xchange.examples.mercadobitcoin.trade;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.examples.mercadobitcoin.InteractiveAuthenticatedExchange;
import com.xeiam.xchange.mercadobitcoin.dto.MercadoBitcoinBaseTradeApiResult;
import com.xeiam.xchange.mercadobitcoin.dto.trade.MercadoBitcoinCancelOrderResult;
import com.xeiam.xchange.mercadobitcoin.dto.trade.MercadoBitcoinPlaceLimitOrderResult;
import com.xeiam.xchange.mercadobitcoin.dto.trade.MercadoBitcoinUserOrders;
import com.xeiam.xchange.mercadobitcoin.dto.trade.MercadoBitcoinUserOrdersEntry;
import com.xeiam.xchange.mercadobitcoin.service.polling.MercadoBitcoinTradeServiceRaw;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;

/**
 * <p>
 * Example showing the following:
 * </p>
 * <ul>
 * <li>Connect to Mercado Bitcoin exchange with authentication</li>
 * <li>Enter, review and cancel limit orders</li>
 * </ul>
 *
 * @author Copied from Bitstamp and adapted by Felipe Micaroni Lalli
 */
public class MercadoBitcoinTradeDemo {

  public static void main(String[] args) throws IOException, InterruptedException {

    Exchange mercadoBitcoin = InteractiveAuthenticatedExchange.createInstanceFromDefaultInput();
    PollingTradeService tradeService = mercadoBitcoin.getPollingTradeService();

    generic(tradeService);
    raw((MercadoBitcoinTradeServiceRaw) tradeService);
  }

  private static void generic(PollingTradeService tradeService) throws IOException, InterruptedException {

    printOpenOrders(tradeService);

    // place a limit buy order
    LimitOrder limitOrder = new LimitOrder((Order.OrderType.ASK), new BigDecimal("0.01"), CurrencyPair.BTC_BRL, "", null, new BigDecimal("9000.00"));
    String limitOrderReturnValue = tradeService.placeLimitOrder(limitOrder);
    System.out.println("Limit Order return value: " + limitOrderReturnValue);

    printOpenOrders(tradeService);

    // Cancel the added order
    boolean cancelResult = tradeService.cancelOrder(limitOrderReturnValue);
    System.out.println("Canceling returned " + cancelResult);

    printOpenOrders(tradeService);
  }

  private static void printOpenOrders(PollingTradeService tradeService) throws IOException, InterruptedException {

    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println("Open Orders: " + openOrders.toString());
  }

  private static void raw(MercadoBitcoinTradeServiceRaw tradeService) throws IOException, InterruptedException {

    printRawOpenOrders(tradeService);

    // place a limit buy order
    MercadoBitcoinBaseTradeApiResult<MercadoBitcoinPlaceLimitOrderResult> order = tradeService.mercadoBitcoinPlaceLimitOrder("ltc_brl", "buy",
        new BigDecimal("0.01"), new BigDecimal("1"));
    System.out.println("MercadoBitcoinBaseTradeApiResult<MercadoBitcoinPlaceLimitOrderResult> return value: " + order);

    printRawOpenOrders(tradeService);

    // Cancel the added order
    MercadoBitcoinBaseTradeApiResult<MercadoBitcoinCancelOrderResult> cancelResult = tradeService.mercadoBitcoinCancelOrder("ltc_brl", order
        .getTheReturn().keySet().iterator().next());
    System.out.println("Canceling returned " + cancelResult);

    printRawOpenOrders(tradeService);
  }

  private static void printRawOpenOrders(MercadoBitcoinTradeServiceRaw tradeService) throws IOException, InterruptedException {

    MercadoBitcoinBaseTradeApiResult<MercadoBitcoinUserOrders> openOrdersResult = tradeService.getMercadoBitcoinUserOrders("btc_brl", null, "active",
        null, null, null, null);

    MercadoBitcoinUserOrders openOrders = openOrdersResult.getTheReturn();

    System.out.println("Open Orders for BTC: " + openOrders.size());
    for (Map.Entry<String, MercadoBitcoinUserOrdersEntry> order : openOrders.entrySet()) {
      System.out.println(order.toString());
    }

    openOrdersResult = tradeService.getMercadoBitcoinUserOrders("ltc_brl", null, "active", null, null, null, null);

    openOrders = openOrdersResult.getTheReturn();

    System.out.println("Open Orders for LTC: " + openOrders.size());
    for (Map.Entry<String, MercadoBitcoinUserOrdersEntry> order : openOrders.entrySet()) {
      System.out.println(order.toString());
    }
  }
}
