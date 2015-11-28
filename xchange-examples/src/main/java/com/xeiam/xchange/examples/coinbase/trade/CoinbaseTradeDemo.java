package com.xeiam.xchange.examples.coinbase.trade;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.coinbase.dto.trade.CoinbaseTransfers;
import com.xeiam.xchange.coinbase.service.polling.CoinbaseTradeService;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.examples.coinbase.CoinbaseDemoUtils;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.DefaultTradeHistoryParamPaging;

/**
 * @author jamespedwards42
 */
public class CoinbaseTradeDemo {

  public static void main(String[] args) throws IOException {

    Exchange coinbase = CoinbaseDemoUtils.createExchange();
    PollingTradeService tradeService = coinbase.getPollingTradeService();

    generic(tradeService);
    raw((CoinbaseTradeService) tradeService);
  }

  public static void generic(PollingTradeService tradeService) throws IOException {

    // MarketOrder marketOrder = new MarketOrder(OrderType.BID, new BigDecimal(".01"), Currency.BTC, Currency.USD);
    // String orderId = tradeService.placeMarketOrder(marketOrder);
    // System.out.println("Order Id: " + orderId);

    int page = 1; // optional
    int limit = 3; // optional
    Trades trades = tradeService.getTradeHistory(new DefaultTradeHistoryParamPaging(page, limit));
    System.out.println(trades);
  }

  public static void raw(CoinbaseTradeService tradeService) throws IOException {

    // CoinbaseTransfer buyTransfer = tradeService.buy(new BigDecimal(".01"));
    // System.out.println(buyTransfer);

    CoinbaseTransfers transfers = tradeService.getCoinbaseTransfers();
    System.out.println(transfers);
  }
}
