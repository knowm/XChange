package org.knowm.xchange.gatecoin;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Ticker.Builder;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.gatecoin.dto.account.GatecoinBalance;
import org.knowm.xchange.gatecoin.dto.marketdata.GatecoinDepth;
import org.knowm.xchange.gatecoin.dto.marketdata.GatecoinTicker;
import org.knowm.xchange.gatecoin.dto.marketdata.GatecoinTransaction;
import org.knowm.xchange.gatecoin.dto.marketdata.Results.GatecoinDepthResult;
import org.knowm.xchange.gatecoin.dto.trade.GatecoinTradeHistory;
import org.knowm.xchange.gatecoin.dto.trade.Results.GatecoinTradeHistoryResult;
import org.knowm.xchange.utils.DateUtils;

/** Various adapters for converting from Gatecoin DTOs to XChange DTOs */
public final class GatecoinAdapters {

  /** private Constructor */
  private GatecoinAdapters() {}

  /**
   * Adapts a GatecoinBalance to a Wallet
   *
   * @param gatecoinBalances
   * @return The account info
   */
  public static Wallet adaptWallet(GatecoinBalance[] gatecoinBalances) {

    ArrayList<Balance> balances = new ArrayList<>();

    for (GatecoinBalance balance : gatecoinBalances) {
      Currency ccy = Currency.valueOf(balance.getCurrency());
      balances.add(
          new Balance.Builder()
              .setCurrency(ccy)
              .setTotal(balance.getBalance())
              .setAvailable(balance.getAvailableBalance())
              .setFrozen(balance.getOpenOrder().negate())
              .setWithdrawing(balance.getPendingOutgoing().negate())
              .setDepositing(balance.getPendingIncoming().negate())
              .createBalance());
    }
    return Wallet.build(balances);
  }

  /**
   * Adapts a org.knowm.xchange.gatecoin.api.model.OrderBook to a OrderBook Object
   *
   * @param gatecoinDepthResult
   * @param currencyPair (e.g. BTC/USD)
   * @param timeScale polled order books provide a timestamp in seconds, stream in ms
   * @return The XChange OrderBook
   */
  public static OrderBook adaptOrderBook(
      GatecoinDepthResult gatecoinDepthResult, CurrencyPair currencyPair, int timeScale) {

    List<LimitOrder> asks =
        createOrders(currencyPair, OrderType.ASK, gatecoinDepthResult.getAsks());
    List<LimitOrder> bids =
        createOrders(currencyPair, OrderType.BID, gatecoinDepthResult.getBids());
    Date date = new Date();
    return new OrderBook(date, asks, bids);
  }

  public static List<LimitOrder> createOrders(
      CurrencyPair currencyPair, OrderType orderType, GatecoinDepth[] orders) {

    List<LimitOrder> limitOrders = new ArrayList<>();
    for (GatecoinDepth priceVolume : orders) {

      limitOrders.add((createOrder(currencyPair, priceVolume, orderType)));
    }
    return limitOrders;
  }

  public static LimitOrder createOrder(
      CurrencyPair currencyPair, GatecoinDepth priceAndAmount, OrderType orderType) {

    return new LimitOrder(
        orderType, priceAndAmount.getVolume(), currencyPair, "", null, priceAndAmount.getPrice());
  }

  public static void checkArgument(boolean argument, String msgPattern, Object... msgArgs) {

    if (!argument) {
      throw new IllegalArgumentException(MessageFormat.format(msgPattern, msgArgs));
    }
  }

  public static Trades adaptTrades(GatecoinTransaction[] transactions, CurrencyPair currencyPair) {

    List<Trade> trades = new ArrayList<>();
    long lastTradeId = 0;
    for (GatecoinTransaction tx : transactions) {
      final long tradeId = tx.getTransactionId();
      if (tradeId > lastTradeId) {
        lastTradeId = tradeId;
      }
      trades.add(
          new Trade(
              null,
              tx.getQuantity(),
              currencyPair,
              tx.getPrice(),
              DateUtils.fromMillisUtc(tx.getTransacationTime() * 1000L),
              String.valueOf(tradeId)));
    }
    return new Trades(trades, lastTradeId, TradeSortType.SortByID);
  }

  public static Ticker adaptTicker(GatecoinTicker[] gatecoinTickers, CurrencyPair currencyPair) {

    String ccyPair = currencyPair.toString().replace('/', ' ').replaceAll("\\s", "");
    for (GatecoinTicker ticker : gatecoinTickers) {
      String responseCcyPair = ticker.getCurrencyPair();
      if (responseCcyPair.compareTo(ccyPair) == 0) {
        BigDecimal last = ticker.getLast();
        BigDecimal bid = ticker.getBid();
        BigDecimal ask = ticker.getAsk();
        BigDecimal high = ticker.getHigh();
        BigDecimal low = ticker.getLow();
        BigDecimal vwap = ticker.getVwap();
        BigDecimal volume = ticker.getVolume();
        Date timestamp = new Date(ticker.getTimestamp() * 1000L);

        return new Builder()
            .currencyPair(currencyPair)
            .last(last)
            .bid(bid)
            .ask(ask)
            .high(high)
            .low(low)
            .vwap(vwap)
            .volume(volume)
            .timestamp(timestamp)
            .build();
      }
    }
    return null;
  }

  /**
   * Adapt the user's trades
   *
   * @param gatecoinUserTrades
   * @return
   */
  public static UserTrades adaptTradeHistory(GatecoinTradeHistoryResult gatecoinUserTrades) {

    List<UserTrade> trades = new ArrayList<>();
    long lastTradeId = 0;
    if (gatecoinUserTrades != null) {
      GatecoinTradeHistory[] tradeHistory = gatecoinUserTrades.getTransactions();
      for (GatecoinTradeHistory gatecoinUserTrade : tradeHistory) {
        final boolean isAsk = Objects.equals(gatecoinUserTrade.getWay().toLowerCase(), "ask");
        OrderType orderType = isAsk ? OrderType.ASK : OrderType.BID;
        BigDecimal originalAmount = gatecoinUserTrade.getQuantity();
        BigDecimal price = gatecoinUserTrade.getPrice();
        Date timestamp =
            GatecoinUtils.parseUnixTSToDateTime(gatecoinUserTrade.getTransactionTime());
        long transactionId = gatecoinUserTrade.getTransactionId();
        if (transactionId > lastTradeId) {
          lastTradeId = transactionId;
        }
        final String tradeId = String.valueOf(transactionId);
        final String orderId =
            isAsk ? gatecoinUserTrade.getAskOrderID() : gatecoinUserTrade.getBidOrderID();
        final BigDecimal feeRate = gatecoinUserTrade.getFeeRate();
        final BigDecimal feeAmount =
            feeRate.multiply(originalAmount).multiply(price).setScale(8, RoundingMode.CEILING);

        final CurrencyPair currencyPair =
            CurrencyPair.build(
                gatecoinUserTrade.getCurrencyPair().substring(0, 3),
                gatecoinUserTrade.getCurrencyPair().substring(3, 6));
        UserTrade trade =
            new UserTrade(
                orderType,
                originalAmount,
                currencyPair,
                price,
                timestamp,
                tradeId,
                orderId,
                feeAmount,
                Currency.valueOf(currencyPair.getCounter().getCurrencyCode()));
        trades.add(trade);
      }
    }
    return new UserTrades(trades, lastTradeId, TradeSortType.SortByID);
  }
}
