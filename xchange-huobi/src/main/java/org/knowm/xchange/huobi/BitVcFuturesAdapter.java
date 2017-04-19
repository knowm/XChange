package org.knowm.xchange.huobi;

import static org.knowm.xchange.currency.Currency.BTC;
import static org.knowm.xchange.currency.Currency.LTC;
import static org.knowm.xchange.currency.Currency.USD;
import static org.knowm.xchange.dto.Order.OrderType.ASK;
import static org.knowm.xchange.dto.Order.OrderType.BID;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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
import org.knowm.xchange.huobi.dto.account.BitVcFuturesAccountInfo;
import org.knowm.xchange.huobi.dto.marketdata.futures.BitVcFuturesDepth;
import org.knowm.xchange.huobi.dto.marketdata.futures.BitVcFuturesTicker;
import org.knowm.xchange.huobi.dto.marketdata.futures.BitVcFuturesTrade;
import org.knowm.xchange.huobi.dto.trade.BitVcFuturesPosition;
import org.knowm.xchange.huobi.dto.trade.BitVcFuturesPositionByContract;

public class BitVcFuturesAdapter {
  private static final OpenOrders NO_OPEN_ORDERS = new OpenOrders(Collections.EMPTY_LIST);
  private static final Balance ZERO_USD_BALANCE = new Balance(USD, BigDecimal.ZERO);
  private static final Balance ZERO_LTC_BALANCE = new Balance(LTC, BigDecimal.ZERO);

  public static Ticker adaptTicker(BitVcFuturesTicker ticker, CurrencyPair currencyPair) {

    return new Ticker.Builder().currencyPair(currencyPair).last(ticker.getLast()).bid(ticker.getBuy()).ask(ticker.getSell()).high(ticker.getHigh())
        .low(ticker.getLow()).volume(ticker.getVol()).build();
  }

  public static OrderBook adaptOrderBook(BitVcFuturesDepth depth, CurrencyPair currencyPair) {

    List<LimitOrder> asks = adaptOrderBook(depth.getAsks(), ASK, currencyPair);
    List<LimitOrder> bids = adaptOrderBook(depth.getBids(), BID, currencyPair);

    // ask side is flipped
    Collections.sort(asks);

    return new OrderBook(null, asks, bids);
  }

  private static List<LimitOrder> adaptOrderBook(BigDecimal[][] orders, OrderType type, CurrencyPair currencyPair) {

    List<LimitOrder> limitOrders = new ArrayList<>(orders.length);

    for (int i = 0; i < orders.length; i++) {
      BigDecimal[] order = orders[i];

      LimitOrder limitOrder = new LimitOrder(type, order[1], currencyPair, null, null, order[0]);
      limitOrders.add(limitOrder);
    }
    return limitOrders;
  }

  public static Trades adaptTrades(BitVcFuturesTrade[] trades, CurrencyPair currencyPair) {

    List<Trade> tradeList = new ArrayList<>(trades.length);
    for (BitVcFuturesTrade trade : trades) {
      tradeList.add(adaptTrade(trade, currencyPair));
    }

    return new Trades(tradeList, TradeSortType.SortByID);
  }

  private static Trade adaptTrade(BitVcFuturesTrade trade, CurrencyPair currencyPair) {

    OrderType type = trade.getType().equals("buy") ? BID : ASK;
    return new Trade(type, trade.getAmount(), currencyPair, trade.getPrice(), trade.getDate(), trade.getId());
  }

  public static AccountInfo adaptAccountInfo(final BitVcFuturesAccountInfo accountInfo) {
    Balance btcBalance = new Balance(BTC, accountInfo.getAvailableMargin());

    return new AccountInfo(new Wallet(btcBalance, ZERO_USD_BALANCE, ZERO_LTC_BALANCE));
  }

  public static OpenOrders adaptOpenOrders(final BitVcFuturesPositionByContract positions) {
    final BitVcFuturesPosition[] weekPositions = positions.getWeekPositions();
    if (weekPositions.length <= 0) {
      return NO_OPEN_ORDERS;
    }

    List<LimitOrder> orders = new ArrayList<>(weekPositions.length);
    for (BitVcFuturesPosition position : weekPositions) {
      orders.add(new LimitOrder(position.getTradeType() == 1 ? OrderType.BID : OrderType.ASK, position.getAmount(), CurrencyPair.BTC_CNY,
          String.valueOf(position.getId()), new Date(), position.getPrice()));
    }

    return new OpenOrders(orders);
  }
}
