package com.xeiam.xchange.examples.okcoin.marketdata;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.okcoin.FuturesContract;
import com.xeiam.xchange.okcoin.OkCoinAdapters;
import com.xeiam.xchange.okcoin.OkCoinExchange;
import com.xeiam.xchange.okcoin.dto.marketdata.OkCoinTrade;
import com.xeiam.xchange.okcoin.dto.trade.OkCoinPosition;
import com.xeiam.xchange.okcoin.dto.trade.OkCoinPositionResult;
import com.xeiam.xchange.okcoin.service.polling.OkCoinFuturesTradeService;
import com.xeiam.xchange.okcoin.service.polling.OkCoinMarketDataServiceRaw;
import com.xeiam.xchange.service.polling.account.PollingAccountService;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

public class OkCoinTradesDemo {

  public static void main(String[] args) throws IOException {

    ExchangeSpecification exSpec = new ExchangeSpecification(OkCoinExchange.class);
    exSpec.setSecretKey("x");
    exSpec.setApiKey("x");

    // flag to set Use_Intl (USD) or China (default)
    exSpec.setExchangeSpecificParametersItem("Use_Intl", true);
    exSpec.setExchangeSpecificParametersItem("Use_Futures", false);
    exSpec.setExchangeSpecificParametersItem("Futures_Contract", FuturesContract.ThisWeek);
    Exchange okcoinExchange = ExchangeFactory.INSTANCE.createExchange(exSpec);

    //		futures(okcoinExchange);

    generic(okcoinExchange);
    raw(okcoinExchange);
  }

  private static void futures(Exchange okcoinExchange) throws IOException {
    PollingAccountService pollingAccountService = okcoinExchange.getPollingAccountService();

    AccountInfo accountInfo = pollingAccountService.getAccountInfo();
    System.out.println(accountInfo);

    OkCoinFuturesTradeService pollingTradeService = (OkCoinFuturesTradeService) okcoinExchange.getPollingTradeService();

    OpenOrders openOrders = pollingTradeService.getOpenOrders();
    System.out.println(openOrders);

    OkCoinPositionResult futuresPosition = pollingTradeService.getFuturesPosition(OkCoinAdapters.adaptSymbol(CurrencyPair.BTC_USD),
        FuturesContract.ThisWeek);
    OkCoinPosition[] positions = futuresPosition.getPositions();

    for (int i = 0; i < positions.length; i++) {
      System.out.println(positions[i].getContractId());
    }

    String placeLimitOrder = pollingTradeService
        .placeLimitOrder(new LimitOrder(OrderType.BID, new BigDecimal("1"), CurrencyPair.BTC_USD, "0", new Date(), new BigDecimal("200")));
    System.out.println(placeLimitOrder);

    boolean cancelOrder = pollingTradeService.cancelOrder(placeLimitOrder);
    System.out.println("Cancelled " + cancelOrder);
  }

  private static void generic(Exchange okcoinExchange) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = okcoinExchange.getPollingMarketDataService();

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

  private static void raw(Exchange okcoinExchange) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    OkCoinMarketDataServiceRaw okCoinMarketDataServiceRaw = (OkCoinMarketDataServiceRaw) okcoinExchange.getPollingMarketDataService();

    // Get the latest trade data for BTC_USD
    OkCoinTrade[] trades = okCoinMarketDataServiceRaw.getTrades(CurrencyPair.BTC_CNY);

    System.out.println("Trades size: " + trades.length);
    System.out.println("newest trade: " + trades[trades.length - 1].toString());

    // Poll for any new trades since last id
    trades = okCoinMarketDataServiceRaw.getTrades(CurrencyPair.BTC_CNY, trades[trades.length - 1].getTid() - 10);
    for (int i = 0; i < trades.length; i++) {
      OkCoinTrade okCoinTrade = trades[i];
      System.out.println(okCoinTrade.toString());
    }
    System.out.println("Trades size: " + trades.length);
  }
}
