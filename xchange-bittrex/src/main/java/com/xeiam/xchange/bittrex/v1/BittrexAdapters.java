package com.xeiam.xchange.bittrex.v1;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.bittrex.v1.dto.account.BittrexBalance;
import com.xeiam.xchange.bittrex.v1.dto.marketdata.BittrexLevel;
import com.xeiam.xchange.bittrex.v1.dto.marketdata.BittrexSymbol;
import com.xeiam.xchange.bittrex.v1.dto.marketdata.BittrexTicker;
import com.xeiam.xchange.bittrex.v1.dto.marketdata.BittrexTrade;
import com.xeiam.xchange.bittrex.v1.dto.trade.BittrexOpenOrder;
import com.xeiam.xchange.bittrex.v1.dto.trade.BittrexUserTrade;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Ticker.TickerBuilder;
import com.xeiam.xchange.dto.marketdata.Trade;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.marketdata.Trades.TradeSortType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.Wallet;

public final class BittrexAdapters {

  public static final Logger log = LoggerFactory.getLogger(BittrexAdapters.class);

  private BittrexAdapters() {

  }

  public static List<CurrencyPair> adaptCurrencyPairs(Collection<BittrexSymbol> bittrexSymbol) {

    List<CurrencyPair> currencyPairs = new ArrayList<CurrencyPair>();
    for (BittrexSymbol symbol : bittrexSymbol) {
      currencyPairs.add(adaptCurrencyPair(symbol));
    }
    return currencyPairs;
  }

  public static CurrencyPair adaptCurrencyPair(BittrexSymbol bittrexSymbol) {

    String baseSymbol = bittrexSymbol.getMarketCurrency();
    String counterSymbol = bittrexSymbol.getBaseCurrency();
    return new CurrencyPair(baseSymbol, counterSymbol);
  }

  public static List<LimitOrder> adaptOpenOrders(List<BittrexOpenOrder> bittrexOpenOrders) {

    List<LimitOrder> openOrders = new ArrayList<LimitOrder>();

    for (BittrexOpenOrder order : bittrexOpenOrders) {
      openOrders.add(adaptOpenOrder(order));
    }

    return openOrders;
  }

  public static LimitOrder adaptOpenOrder(BittrexOpenOrder bittrexOpenOrder) {

    OrderType type = bittrexOpenOrder.getOrderType().equalsIgnoreCase("LIMIT_SELL") ? OrderType.ASK : OrderType.BID;
    String[] currencies = bittrexOpenOrder.getExchange().split("-");
    CurrencyPair pair = new CurrencyPair(currencies[1], currencies[0]);

    return new LimitOrder(type, bittrexOpenOrder.getQuantityRemaining(), pair, bittrexOpenOrder.getOrderUuid(), null, bittrexOpenOrder.getLimit());
  }

  public static List<LimitOrder> adaptOrders(BittrexLevel[] orders, CurrencyPair currencyPair, String orderType, String id) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>(orders.length);

    for (BittrexLevel order : orders) {
      limitOrders.add(adaptOrder(order.getAmount(), order.getPrice(), currencyPair, orderType, id));
    }

    return limitOrders;
  }

  public static LimitOrder adaptOrder(BigDecimal amount, BigDecimal price, CurrencyPair currencyPair, String orderTypeString, String id) {

    OrderType orderType = orderTypeString.equalsIgnoreCase("bid") ? OrderType.BID : OrderType.ASK;

    return new LimitOrder(orderType, amount, currencyPair, id, null, price);
  }

  public static Trade adaptTrade(BittrexTrade trade, CurrencyPair currencyPair) {

    OrderType orderType = trade.getOrderType().equalsIgnoreCase("BUY") ? OrderType.BID : OrderType.ASK;
    BigDecimal amount = trade.getQuantity();
    BigDecimal price = trade.getPrice();
    Date date = BittrexUtils.toDate(trade.getTimeStamp());
    final String tradeId = String.valueOf(trade.getId());
    return new Trade(orderType, amount, currencyPair, price, date, tradeId);
  }

  public static Trades adaptTrades(BittrexTrade[] trades, CurrencyPair currencyPair) {

    List<Trade> tradesList = new ArrayList<Trade>(trades.length);
    long lastTradeId = 0;
    for (BittrexTrade trade : trades) {
      long tradeId = Long.valueOf(trade.getId());
      if (tradeId > lastTradeId)
        lastTradeId = tradeId;
      tradesList.add(adaptTrade(trade, currencyPair));
    }
    return new Trades(tradesList, lastTradeId, TradeSortType.SortByID);
  }

  public static Ticker adaptTicker(BittrexTicker bittrexTicker, CurrencyPair currencyPair) {

    BigDecimal last = bittrexTicker.getLast();
    BigDecimal bid = bittrexTicker.getBid();
    BigDecimal ask = bittrexTicker.getAsk();
    BigDecimal high = bittrexTicker.getHigh();
    BigDecimal low = bittrexTicker.getLow();
    BigDecimal volume = bittrexTicker.getVolume();

    Date timestamp = BittrexUtils.toDate(bittrexTicker.getTimeStamp());

    return TickerBuilder.newInstance().withCurrencyPair(currencyPair).withLast(last).withBid(bid).withAsk(ask).withHigh(high).withLow(low).withVolume(volume).withTimestamp(timestamp).build();
  }

  public static AccountInfo adaptAccountInfo(List<BittrexBalance> balances) {

    List<Wallet> wallets = new ArrayList<Wallet>(balances.size());

    for (BittrexBalance balance : balances) {
      wallets.add(new Wallet(balance.getCurrency().toUpperCase(), balance.getAvailable()));
    }

    return new AccountInfo(null, wallets);
  }

  public static List<Trade> adaptUserTrades(List<BittrexUserTrade> bittrexUserTrades) {

    List<Trade> trades = new ArrayList<Trade>();

    for (BittrexUserTrade bittrexTrade : bittrexUserTrades) {
      trades.add(adaptUserTrade(bittrexTrade));
    }
    return trades;
  }

  public static Trade adaptUserTrade(BittrexUserTrade trade) {

    String[] currencies = trade.getExchange().split("-");
    CurrencyPair currencyPair = new CurrencyPair(currencies[1], currencies[0]);

    OrderType orderType = trade.getOrderType().equalsIgnoreCase("LIMIT_BUY") ? OrderType.BID : OrderType.ASK;
    BigDecimal amount = trade.getQuantity().subtract(trade.getQuantityRemaining());
    BigDecimal price = trade.getLimit();
    Date date = BittrexUtils.toDate(trade.getTimeStamp());
    String tradeId = String.valueOf(trade.getOrderUuid());

    return new Trade(orderType, amount, currencyPair, price, date, tradeId);
  }

}
