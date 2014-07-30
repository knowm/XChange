package com.xeiam.xchange.examples.kraken.trading;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.examples.kraken.KrakenExampleUtils;
import com.xeiam.xchange.kraken.dto.trade.KrakenOpenPosition;
import com.xeiam.xchange.kraken.dto.trade.KrakenOrder;
import com.xeiam.xchange.kraken.dto.trade.KrakenOrderResponse;
import com.xeiam.xchange.kraken.dto.trade.KrakenOrderType;
import com.xeiam.xchange.kraken.dto.trade.KrakenStandardOrder;
import com.xeiam.xchange.kraken.dto.trade.KrakenTrade;
import com.xeiam.xchange.kraken.dto.trade.KrakenType;
import com.xeiam.xchange.kraken.service.polling.KrakenTradeServiceRaw;

public class KrakenTradeRawSpecificDemo {

  public static void main(String[] args) throws IOException {

    Exchange krakenExchange = KrakenExampleUtils.createTestExchange();

    // Interested in the private trading functionality (authentication)
    KrakenTradeServiceRaw tradeService = (KrakenTradeServiceRaw) krakenExchange.getPollingTradeService();

    KrakenStandardOrder order = // stop at -5% loss, take profit at +$10 price increase.
        KrakenStandardOrder.getLimitOrderBuilder(CurrencyPair.BTC_USD, KrakenType.BUY, "100.00", new BigDecimal("2.12345678")).withCloseOrder(KrakenOrderType.STOP_LOSS_PROFIT, "#5%", "#10")
            .withValidateOnly(true) // validate only for demo purposes
            .buildOrder();

    KrakenOrderResponse orderResponse = tradeService.placeKrakenOrder(order);
    System.out.println(orderResponse);

    Map<String, KrakenOrder> openOrders = tradeService.getKrakenOpenOrders();
    System.out.println(openOrders);

    limitRate();

    Map<String, KrakenOrder> closedOrders = tradeService.getKrakenClosedOrders();
    System.out.println(closedOrders);

    Set<String> closedOrderIds = closedOrders.keySet();
    System.out.println(tradeService.queryKrakenOrders(closedOrderIds.toArray(new String[closedOrderIds.size()])));

    Map<String, KrakenTrade> trades = tradeService.getKrakenTradeHistory();
    System.out.println(trades);

    Set<String> tradeIds = trades.keySet();
    System.out.println(tradeService.queryKrakenTrades(tradeIds.toArray(new String[tradeIds.size()])));

    Map<String, KrakenOpenPosition> openPositions = tradeService.getOpenPositions();
    System.out.println(openPositions);
  }

  private static void limitRate() {

    try { // respect API rate limit
      Thread.sleep(500);
    } catch (InterruptedException e) {
    }
  }
}
