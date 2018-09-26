package org.knowm.xchange.getbtc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.getbtc.dto.account.GetbtcAccountInformation;
import org.knowm.xchange.getbtc.dto.marketdata.GetbtcOrderbook;
import org.knowm.xchange.getbtc.dto.marketdata.GetbtcTicker;
import org.knowm.xchange.getbtc.dto.marketdata.GetbtcTickerResponse;
import org.knowm.xchange.getbtc.dto.marketdata.GetbtcTransaction;
import org.knowm.xchange.getbtc.dto.trade.GetbtcOpenOrders;
import org.knowm.xchange.getbtc.utils.CommonUtil;

/**
 * kevinobamatheus@gmail.com
 *
 * @author kevingates
 */
public class GetbtcAdapters {

  private GetbtcAdapters() {}

  /**
   * @param pair
   * @return
   */
  public static String toSymbol(CurrencyPair pair) {
    if (pair.equals(CurrencyPair.IOTA_BTC)) {
      return "IOTABTC";
    }
    return pair.base.getCurrencyCode() + pair.counter.getCurrencyCode();
  }

  /**
   * @param currency
   * @return
   */
  public static String toSymbol(Currency currency) {
    if (Currency.IOT.equals(currency)) {
      return "IOTA";
    }
    return currency.getSymbol();
  }

  /**
   * @param pair
   * @return
   */
  public static String getCurrency(CurrencyPair pair) {
    if (pair.equals(CurrencyPair.IOTA_BTC)) {
      return "IOTABTC";
    }
    return pair.counter.getCurrencyCode();
  }

  public static String toMarket(CurrencyPair currencyPair) {
    return currencyPair.base.getCurrencyCode() + currencyPair.counter.getCurrencyCode();
  }

  /**
   *
   * @param getbtcTickerResponse
   * @return
   */
  public static Ticker convertTicker(GetbtcTickerResponse getbtcTickerResponse) {
    GetbtcTicker ticker = getbtcTickerResponse.getTicker();

    return new Ticker.Builder()
        .ask(ticker.getSell())
        .bid(ticker.getBuy())
        .high(ticker.getHigh())
        .low(ticker.getLow())
        .volume(ticker.getVol())
        .last(ticker.getLast())
        .timestamp(CommonUtil.timeStampToDate(getbtcTickerResponse.getDate()))
        .build();
  }

  /**
   *
   * @param getbtcTickers
   * @return
   */
  public static List<Ticker> convertTickerMap(Map<String, GetbtcTicker> getbtcTickers) {
    List<Ticker> tickers = new ArrayList<Ticker>();

    for (Map.Entry<String, GetbtcTicker> getbtcTickerMap : getbtcTickers.entrySet()) {
      String pair = getbtcTickerMap.getKey();
      if (pair != null) {
        tickers.add(
            new Ticker.Builder()
                .ask(getbtcTickerMap.getValue().getSell())
                .bid(getbtcTickerMap.getValue().getBuy())
                .high(getbtcTickerMap.getValue().getHigh())
                .low(getbtcTickerMap.getValue().getLow())
                .volume(getbtcTickerMap.getValue().getVol())
                .last(getbtcTickerMap.getValue().getLast())
                .timestamp(null)
                .currencyPair(convertTradingPair(pair))
                .build());
      }
    }

    return tickers;
  }

  public static CurrencyPair convertTradingPair(String pair) {
    return new CurrencyPair(pair);
  }

  /**
   *
   * @param getbtcOrderbook
   * @param currencyPair
   * @return
   */
  public static OrderBook adaptOrderBook(
      GetbtcOrderbook getbtcOrderbook, CurrencyPair currencyPair) {
    List<LimitOrder> asks = new ArrayList<LimitOrder>();
    List<LimitOrder> bids = new ArrayList<LimitOrder>();

    for (GetbtcOrderbook.OrderBook.Ask getbtcAsk : getbtcOrderbook.getOrderBook().getAsk()) {
      asks.add(
          new LimitOrder(
              OrderType.ASK,
              getbtcAsk.getOrderAmount(),
              currencyPair,
              null,
              CommonUtil.timeStampToDate(getbtcAsk.getTimestamp()),
              getbtcAsk.getPrice()));
    }

    for (GetbtcOrderbook.OrderBook.Bid getbtcBid : getbtcOrderbook.getOrderBook().getBid()) {
      bids.add(
          new LimitOrder(
              OrderType.BID,
              getbtcBid.getOrderAmount(),
              currencyPair,
              null,
              CommonUtil.timeStampToDate(getbtcBid.getTimestamp()),
              getbtcBid.getPrice()));
    }

    return new OrderBook(new Date(), asks, bids);
  }

  /**
   *
   * @param transactions
   * @param currencyPair
   * @return
   */
  public static Trades adaptTrades(GetbtcTransaction[] transactions, CurrencyPair currencyPair) {
    List<Trade> trades = new ArrayList<>();
    long lastTradeId = 0;
    for (GetbtcTransaction transaction : transactions) {
      final long tradeId = transaction.getTid();
      if (tradeId > lastTradeId) {
        lastTradeId = tradeId;
      }
      OrderType orderType = OrderType.BID;
      if (transaction.getType().equals("sell")) orderType = OrderType.ASK;
      trades.add(
          new Trade.Builder()
              .id(String.valueOf(transaction.getTid()))
              .originalAmount((transaction.getAmount()))
              .price(transaction.getPrice())
              .timestamp(new Date(transaction.getDate()))
              .currencyPair(currencyPair)
              .type(orderType)
              .build());
    }

    return new Trades(trades, lastTradeId, TradeSortType.SortByID);
  }

  /**
   *
   * @param getbtcAccountInformation
   * @return
   */
  public static AccountInfo convertBalance(GetbtcAccountInformation getbtcAccountInformation) {
    List<Balance> balances = new ArrayList<Balance>();

    Map<String, BigDecimal> getbtcBalances =
        getbtcAccountInformation.getBalancesAndInfo().getAvailable();

    for (Map.Entry<String, BigDecimal> getbtcBalance : getbtcBalances.entrySet()) {
      String pair = getbtcBalance.getKey();
      if (pair != null) {
        balances.add(
            new Balance.Builder()
                .currency(Currency.getInstance(pair))
                .available(getbtcBalance.getValue())
                .total(getbtcBalance.getValue())
                .frozen(new BigDecimal("0"))
                .build());
      }
    }

    return new AccountInfo(new Wallet(balances));
  }

  public static OpenOrders convertOpenOrders(
      GetbtcOpenOrders getbtcOpenOrders, CurrencyPair currencyPair) {
    List<LimitOrder> openOrders = new LinkedList<>();

    //	    for (Object getbtcOrder : getbtcOpenOrders.getOpenOrders().getAsk()) {
    //	      openOrders.add(
    //	          new LimitOrder.Builder(convertType(getbtcOrder.getType()), currencyPair)
    //	              .id(getbtcOrder.toString())
    //	              .timestamp(new Date(getbtcOrder.getTradeDate()))
    //	              .limitPrice(getbtcOrder.getPrice())
    //	              .originalAmount(getbtcOrder.getTotalAmount())
    //	              .build());
    //	    }
    //
    //	    for (Object getbtcOrder : getbtcOpenOrders.getOpenOrders().getBid()) {
    //		      openOrders.add(
    //		          new LimitOrder.Builder(convertType(getbtcOrder.getType()), currencyPair)
    //		              .id(getbtcOrder.toString())
    //		              .timestamp(new Date(getbtcOrder.getTradeDate()))
    //		              .limitPrice(getbtcOrder.getPrice())
    //		              .originalAmount(getbtcOrder.getTotalAmount())
    //		              .build());
    //		    }

    return new OpenOrders(openOrders);
  }

  public static OrderType convertType(String side) {
    return "SELL".equals(side) ? OrderType.ASK : OrderType.BID;
  }

  public static String convertByType(OrderType orderType) {
    return OrderType.BID.equals(orderType) ? IConstants.BUY : IConstants.SELL;
  }
}
