package org.knowm.xchange.examples.okex.marketdata.v3;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.okcoin.FuturesContract;
import org.knowm.xchange.okcoin.OkexExchangeV3;
import org.knowm.xchange.okcoin.v3.service.OkexMarketDataService;
import org.knowm.xchange.okcoin.v3.service.OkexTradeService;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class OkexTradesDemo {

  public static void main(String[] args) throws IOException {

    ExchangeSpecification exSpec = new ExchangeSpecification(OkexExchangeV3.class);
    exSpec.setSecretKey("x");
    exSpec.setApiKey("x");

    // flag to set Use_Intl (USD) or China (default)
    Exchange okexExchange = ExchangeFactory.INSTANCE.createExchange(exSpec);

    // futures(OkexExchange);

    generic(okexExchange);
    raw(okexExchange);
  }

  private static void futures(Exchange okexExchange) throws IOException {

    AccountService accountService = okexExchange.getAccountService();

    AccountInfo accountInfo = accountService.getAccountInfo();
    System.out.println(accountInfo);

    OkexTradeService tradeService = (OkexTradeService) okexExchange.getTradeService();

    OpenOrders openOrders = tradeService.getOpenOrders();
    System.out.println(openOrders);

    /*
     * OkexPositionResult futuresPosition =
     * tradeService.getFuturesPosition(
     * OkexAdaptersV3.adaptSymbol(CurrencyPair.BTC_USD), FuturesContractV3.ThisWeek);
     * OkexPosition[] positions = futuresPosition.getPositions();
     * for (int i = 0; i < positions.length; i++) {
     * System.out.println(positions[i].getContractId());
     * }
     */

    String placeLimitOrder =
        tradeService.placeLimitOrder(
            new LimitOrder(
                OrderType.BID,
                new BigDecimal("1"),
                CurrencyPair.BTC_USD,
                "0",
                new Date(),
                new BigDecimal("200")));
    System.out.println(placeLimitOrder);

    boolean cancelOrder = tradeService.cancelOrder(placeLimitOrder);
    System.out.println("Cancelled " + cancelOrder);
  }

  private static void generic(Exchange okexExchange) throws IOException {

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = okexExchange.getMarketDataService();

    // Get the latest trade data for BTC_CNY
    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_USD, FuturesContract.ThisWeek);
    System.out.println(trades);
    System.out.println("Trades(0): " + trades.getTrades().get(0).toString());
    System.out.println("Trades size: " + trades.getTrades().size());

    // Get the latest trades data for BTC_CNY for the past couple of trades
    trades = marketDataService.getTrades(CurrencyPair.BTC_CNY, trades.getlastID() - 10);
    System.out.println(trades);
    System.out.println("Trades size: " + trades.getTrades().size());
  }

  private static void raw(Exchange okexExchange) throws IOException {

    // Interested in the public market data feed (no authentication)
    OkexMarketDataService okexMarketDataServiceRaw =
        (OkexMarketDataService) okexExchange.getMarketDataService();

    // Get the latest trade data for BTC_USD
  }
}
