package org.knowm.xchange.bittrex.v1;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.bittrex.v1.dto.account.BittrexBalance;
import org.knowm.xchange.bittrex.v1.dto.marketdata.BittrexLevel;
import org.knowm.xchange.bittrex.v1.dto.marketdata.BittrexSymbol;
import org.knowm.xchange.bittrex.v1.dto.marketdata.BittrexTicker;
import org.knowm.xchange.bittrex.v1.dto.marketdata.BittrexTrade;
import org.knowm.xchange.bittrex.v1.dto.trade.BittrexLimitOrder;
import org.knowm.xchange.bittrex.v1.dto.trade.BittrexOpenOrder;
import org.knowm.xchange.bittrex.v1.dto.trade.BittrexUserTrade;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class BittrexAdapters {

  public static final Logger log = LoggerFactory.getLogger(BittrexAdapters.class);

  private BittrexAdapters() {

  }

  public static List<CurrencyPair> adaptCurrencyPairs(Collection<BittrexSymbol> bittrexSymbol) {

    List<CurrencyPair> currencyPairs = new ArrayList<>();
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

    List<LimitOrder> openOrders = new ArrayList<>();

    for (BittrexOpenOrder order : bittrexOpenOrders) {
      openOrders.add(adaptOpenOrder(order));
    }

    return openOrders;
  }

  public static BittrexLimitOrder adaptOpenOrder(BittrexOpenOrder bittrexOpenOrder) {

    OrderType type = bittrexOpenOrder.getOrderType().equalsIgnoreCase("LIMIT_SELL") ? OrderType.ASK : OrderType.BID;
    String[] currencies = bittrexOpenOrder.getExchange().split("-");
    CurrencyPair pair = new CurrencyPair(currencies[1], currencies[0]);

    return new BittrexLimitOrder(type, bittrexOpenOrder.getQuantityRemaining(), pair, bittrexOpenOrder.getOrderUuid(),
        BittrexUtils.toDate(bittrexOpenOrder.getOpened()), bittrexOpenOrder.getLimit(), bittrexOpenOrder.getQuantityRemaining(),
        bittrexOpenOrder.getPricePerUnit());
  }

  public static List<LimitOrder> adaptOrders(BittrexLevel[] orders, CurrencyPair currencyPair, String orderType, String id) {

    List<LimitOrder> limitOrders = new ArrayList<>(orders.length);

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

    List<Trade> tradesList = new ArrayList<>(trades.length);
    long lastTradeId = 0;
    for (BittrexTrade trade : trades) {
      long tradeId = Long.valueOf(trade.getId());
      if (tradeId > lastTradeId) {
        lastTradeId = tradeId;
      }
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

    return new Ticker.Builder().currencyPair(currencyPair).last(last).bid(bid).ask(ask).high(high).low(low).volume(volume).timestamp(timestamp)
        .build();
  }

  public static Wallet adaptWallet(List<BittrexBalance> balances) {

    List<Balance> wallets = new ArrayList<>(balances.size());

    for (BittrexBalance balance : balances) {
      wallets.add(new Balance(Currency.getInstance(balance.getCurrency().toUpperCase()), balance.getBalance(), balance.getAvailable(),
          balance.getBalance().subtract(balance.getAvailable()).subtract(balance.getPending()), BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
          balance.getPending()));
    }

    return new Wallet(wallets);
  }

  public static List<UserTrade> adaptUserTrades(List<BittrexUserTrade> bittrexUserTrades) {

    List<UserTrade> trades = new ArrayList<>();

    for (BittrexUserTrade bittrexTrade : bittrexUserTrades) {
      trades.add(adaptUserTrade(bittrexTrade));
    }
    return trades;
  }

  public static UserTrade adaptUserTrade(BittrexUserTrade trade) {

    String[] currencies = trade.getExchange().split("-");
    CurrencyPair currencyPair = new CurrencyPair(currencies[1], currencies[0]);

    OrderType orderType = trade.getOrderType().equalsIgnoreCase("LIMIT_BUY") ? OrderType.BID : OrderType.ASK;
    BigDecimal amount = trade.getQuantity().subtract(trade.getQuantityRemaining());
    Date date = BittrexUtils.toDate(trade.getClosed());
    String orderId = String.valueOf(trade.getOrderUuid());

    BigDecimal price = trade.getPricePerUnit();

    if (price == null) {
      price = trade.getLimit();
    }

    return new UserTrade(orderType, amount, currencyPair, price, date, orderId, orderId, trade.getCommission(), currencyPair.counter);
  }

  public static ExchangeMetaData adaptMetaData(List<BittrexSymbol> rawSymbols, ExchangeMetaData metaData) {

    List<CurrencyPair> currencyPairs = BittrexAdapters.adaptCurrencyPairs(rawSymbols);

    Map<CurrencyPair, CurrencyPairMetaData> pairsMap = metaData.getCurrencyPairs();
    Map<Currency, CurrencyMetaData> currenciesMap = metaData.getCurrencies();
    for (CurrencyPair c : currencyPairs) {
      if (!pairsMap.containsKey(c)) {
        pairsMap.put(c, null);
      }
      if (!currenciesMap.containsKey(c.base)) {
        currenciesMap.put(c.base, null);
      }
      if (!currenciesMap.containsKey(c.counter)) {
        currenciesMap.put(c.counter, null);
      }
    }

    return metaData;
  }

}
