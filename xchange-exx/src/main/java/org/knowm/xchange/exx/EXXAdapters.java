package org.knowm.xchange.exx;

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
import org.knowm.xchange.exx.dto.account.EXXAccountInformation;
import org.knowm.xchange.exx.dto.account.EXXBalance;
import org.knowm.xchange.exx.dto.marketdata.EXXOrderbook;
import org.knowm.xchange.exx.dto.marketdata.EXXTicker;
import org.knowm.xchange.exx.dto.marketdata.EXXTickerResponse;
import org.knowm.xchange.exx.dto.marketdata.EXXTransaction;
import org.knowm.xchange.exx.dto.trade.EXXOrder;
import org.knowm.xchange.exx.utils.CommonUtil;

public class EXXAdapters {

  private EXXAdapters() {}

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

  public static String toMarket(CurrencyPair currencyPair) {
    return currencyPair.base.getCurrencyCode() + currencyPair.counter.getCurrencyCode();
  }

  /**
   * @param ticker
   * @return
   */
  public static Ticker convertTicker(EXXTickerResponse exxTickerResponse) {
    EXXTicker ticker = exxTickerResponse.getTicker();

    return new Ticker.Builder()
        .ask(ticker.getSell())
        .bid(ticker.getBuy())
        .high(ticker.getHigh())
        .low(ticker.getLow())
        .volume(ticker.getVol())
        .last(ticker.getLast())
        .timestamp(CommonUtil.timeStampToDate(exxTickerResponse.getDate()))
        .build();
  }

  public static List<Ticker> convertTickerMap(Map<String, EXXTicker> exxTickers) {
    List<Ticker> tickers = new ArrayList<Ticker>();

    for (Map.Entry<String, EXXTicker> exxTickerMap : exxTickers.entrySet()) {
      String pair = exxTickerMap.getKey();
      if (pair != null) {
        tickers.add(
            new Ticker.Builder()
                .ask(exxTickerMap.getValue().getSell())
                .bid(exxTickerMap.getValue().getBuy())
                .high(exxTickerMap.getValue().getHigh())
                .low(exxTickerMap.getValue().getLow())
                .volume(exxTickerMap.getValue().getVol())
                .last(exxTickerMap.getValue().getLast())
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
   * Adapts a to a OrderBook Object
   *
   * @param currencyPair (e.g. BTC/USD)
   * @param timeScale polled order books provide a timestamp in seconds, stream in ms
   * @return The XChange OrderBook
   */
  public static OrderBook adaptOrderBook(EXXOrderbook exxOrderbook, CurrencyPair currencyPair) {
    List<LimitOrder> asks = new ArrayList<LimitOrder>();
    List<LimitOrder> bids = new ArrayList<LimitOrder>();

    for (BigDecimal[] exxAsk : exxOrderbook.getAsks()) {
      asks.add(new LimitOrder(OrderType.ASK, exxAsk[1], currencyPair, null, null, exxAsk[0]));
    }

    for (BigDecimal[] exxBid : exxOrderbook.getBids()) {
      bids.add(new LimitOrder(OrderType.BID, exxBid[1], currencyPair, null, null, exxBid[0]));
    }

    return new OrderBook(new Date(), asks, bids);
  }

  /**
   * Adapts a Transaction[] to a Trades Object
   *
   * @param transactions The Bitstamp transactions
   * @param currencyPair (e.g. BTC/USD)
   * @return The XChange Trades
   */
  public static Trades adaptTrades(EXXTransaction[] transactions, CurrencyPair currencyPair) {
    List<Trade> trades = new ArrayList<>();
    long lastTradeId = 0;
    for (EXXTransaction transaction : transactions) {
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
   * @param exxAccountInformation
   * @return
   */
  public static AccountInfo convertBalance(EXXAccountInformation exxAccountInformation) {
    List<Balance> balances = new ArrayList<Balance>();

    Map<String, EXXBalance> exxBalances = exxAccountInformation.getBalances();

    for (Map.Entry<String, EXXBalance> exxBalanceMap : exxBalances.entrySet()) {
      String pair = exxBalanceMap.getKey();
      if (pair != null) {
        balances.add(
            new Balance.Builder()
                .currency(Currency.getInstance(pair))
                .available(exxBalanceMap.getValue().getBalance())
                .total(exxBalanceMap.getValue().getTotal())
                .frozen(exxBalanceMap.getValue().getFreeze())
                .build());
      }
    }

    return new AccountInfo(Wallet.Builder.from(balances).build());
  }

  public static OpenOrders convertOpenOrders(
      List<EXXOrder> exxOpenOrders, CurrencyPair currencyPair) {
    List<LimitOrder> openOrders = new LinkedList<>();

    for (EXXOrder exxOrder : exxOpenOrders) {
      openOrders.add(
          new LimitOrder.Builder(convertType(exxOrder.getType()), currencyPair)
              .id(exxOrder.getId())
              .timestamp(new Date(exxOrder.getTradeDate()))
              .limitPrice(exxOrder.getPrice())
              .originalAmount(exxOrder.getTotalAmount())
              .build());
    }

    return new OpenOrders(openOrders);
  }

  public static OrderType convertType(String side) {
    return "SELL".equals(side) ? OrderType.ASK : OrderType.BID;
  }

  //  /**
  //   * There is no method to discern market versus limit order type - so this returns a generic
  //   * GenericOrder as a status
  //   *
  //   * @param
  //   * @return
  //   */
  //  public static CoinsuperGenericOrder adaptOrder(String orderId, OrderList orderList) {
  //    BigDecimal averagePrice = new BigDecimal(orderList.getPriceLimit());
  //    BigDecimal cumulativeAmount = new BigDecimal(orderList.getQuantity());
  //    BigDecimal totalFee = new BigDecimal(orderList.getFee());
  //
  //    BigDecimal amount = new BigDecimal(orderList.getQuantity());
  //    OrderType action = OrderType.ASK;
  //    if (orderList.getAction().equals("Buy")) {
  //      action = OrderType.BID;
  //    }
  //    // Order Status UNDEAL:Not Executed，PARTDEAL:Partially Executed，DEAL:Order Complete，CANCEL:
  //    // Canceled
  //    OrderStatus orderStatus = OrderStatus.PENDING_NEW;
  //    if (orderList.getState().equals("UNDEAL")) {
  //      orderStatus = OrderStatus.PENDING_NEW;
  //    } else if (orderList.getState().equals("Canceled")) {
  //      orderStatus = OrderStatus.CANCELED;
  //    }
  //
  //    CoinsuperGenericOrder coinsuperGenericOrder =
  //        new CoinsuperGenericOrder(
  //            action,
  //            amount,
  //            new CurrencyPair(orderList.getSymbol()),
  //            orderId,
  //            CommonUtil.timeStampToDate(orderList.getUtcCreate()),
  //            averagePrice,
  //            cumulativeAmount,
  //            totalFee,
  //            orderStatus);
  //
  //    return coinsuperGenericOrder;
  //  }

  public static String convertByType(OrderType orderType) {
    return OrderType.BID.equals(orderType) ? IConstants.BUY : IConstants.SELL;
  }
}
