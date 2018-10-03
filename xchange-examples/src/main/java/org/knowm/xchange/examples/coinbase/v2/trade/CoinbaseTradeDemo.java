package org.knowm.xchange.examples.coinbase.v2.trade;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbase.v2.dto.account.CoinbaseSellData.CoinbaseSell;
import org.knowm.xchange.coinbase.v2.service.CoinbaseAccountService;
import org.knowm.xchange.coinbase.v2.service.CoinbaseTradeService;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.examples.coinbase.v2.CoinbaseDemoUtils;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamPaging;

public class CoinbaseTradeDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = CoinbaseDemoUtils.createExchange();
    TradeService tradeService = exchange.getTradeService();

    // [TODO] generic(tradeService);

    raw(exchange, (CoinbaseTradeService) tradeService);
  }

  public static void generic(TradeService tradeService) throws IOException {

    // MarketOrder marketOrder = new MarketOrder(OrderType.BID, new BigDecimal(".01"), Currency.BTC,
    // Currency.USD);
    // String orderId = tradeService.placeMarketOrder(marketOrder);
    // System.out.println("Order Id: " + orderId);

    int page = 1; // optional
    int limit = 3; // optional
    Trades trades = tradeService.getTradeHistory(new DefaultTradeHistoryParamPaging(page, limit));
    System.out.println(trades);
  }

  public static void raw(Exchange exchange, CoinbaseTradeService tradeService) throws IOException {

    CoinbaseAccountService accountService = (CoinbaseAccountService) exchange.getAccountService();
    String accountId = accountService.getCoinbaseAccount(Currency.BTC).getId();

    BigDecimal amount = new BigDecimal("0.0001");
    CoinbaseSell res = tradeService.sell(accountId, amount, Currency.BTC, false);
    System.out.println(res);
  }
}
