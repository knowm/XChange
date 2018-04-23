package org.knowm.xchange.hitbtc.v2;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.FundingRecord.Builder;
import org.knowm.xchange.dto.account.FundingRecord.Status;
import org.knowm.xchange.dto.account.FundingRecord.Type;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcBalance;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcOrder;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcOrderBook;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcOrderLimit;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcOwnTrade;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcSide;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcSymbol;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcTicker;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcTrade;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcTransaction;

public class HitbtcAdapters {

  /** known counter currencies at HitBTC */
  private static final Set<String> counters =
      new HashSet<>(Arrays.asList("USD", "EUR", "BTC", "ETH", "USDT"));

  public static org.knowm.xchange.currency.CurrencyPair adaptSymbol(String symbol) {
    String counter =
        counters
            .stream()
            .filter(symbol::endsWith)
            .findAny()
            .orElseThrow(() -> new RuntimeException("Not supported HitBTC symbol: " + symbol));
    String base = symbol.substring(0, symbol.length() - counter.length());
    return org.knowm.xchange.currency.CurrencyPair.build(base, counter);
  }

  public static org.knowm.xchange.currency.CurrencyPair adaptSymbol(HitbtcSymbol hitbtcSymbol) {

    return org.knowm.xchange.currency.CurrencyPair.build(
        hitbtcSymbol.getBaseCurrency(), hitbtcSymbol.getQuoteCurrency());
  }

  public static Ticker adaptTicker(
      HitbtcTicker hitbtcTicker, org.knowm.xchange.currency.CurrencyPair currencyPair) {

    BigDecimal bid = hitbtcTicker.getBid();
    BigDecimal ask = hitbtcTicker.getAsk();
    BigDecimal high = hitbtcTicker.getHigh();
    BigDecimal low = hitbtcTicker.getLow();
    BigDecimal last = hitbtcTicker.getLast();
    BigDecimal volume = hitbtcTicker.getVolume();
    Date timestamp = hitbtcTicker.getTimestamp();

    return new Ticker.Builder()
        .currencyPair(currencyPair)
        .last(last)
        .bid(bid)
        .ask(ask)
        .high(high)
        .low(low)
        .volume(volume)
        .timestamp(timestamp)
        .build();
  }

  public static List<Ticker> adaptTickers(Map<String, HitbtcTicker> hitbtcTickers) {

    List<Ticker> tickers = new ArrayList<>(hitbtcTickers.size());

    for (Entry<String, HitbtcTicker> ticker : hitbtcTickers.entrySet()) {

      tickers.add(adaptTicker(ticker.getValue(), adaptSymbol(ticker.getKey())));
    }

    return tickers;
  }

  public static OrderBook adaptOrderBook(
      HitbtcOrderBook hitbtcOrderBook, org.knowm.xchange.currency.CurrencyPair currencyPair) {

    List<LimitOrder> asks =
        adaptMarketOrderToLimitOrder(hitbtcOrderBook.getAsks(), OrderType.ASK, currencyPair);
    List<LimitOrder> bids =
        adaptMarketOrderToLimitOrder(hitbtcOrderBook.getBids(), OrderType.BID, currencyPair);

    return new OrderBook(null, asks, bids);
  }

  private static List<LimitOrder> adaptMarketOrderToLimitOrder(
      HitbtcOrderLimit[] hitbtcOrders,
      OrderType orderType,
      org.knowm.xchange.currency.CurrencyPair currencyPair) {

    List<LimitOrder> orders = new ArrayList<>(hitbtcOrders.length);

    for (HitbtcOrderLimit hitbtcOrderLimit : hitbtcOrders) {
      LimitOrder limitOrder =
          new LimitOrder(
              orderType,
              hitbtcOrderLimit.getSize(),
              currencyPair,
              null,
              null,
              hitbtcOrderLimit.getPrice());
      orders.add(limitOrder);
    }

    return orders;
  }

  public static OrderType adaptSide(HitbtcSide side) {

    switch (side) {
      case BUY:
        return OrderType.BID;
      case SELL:
        return OrderType.ASK;
      default:
        return null;
    }
  }

  public static Trades adaptTrades(
      List<? extends HitbtcTrade> allHitbtcTrades,
      org.knowm.xchange.currency.CurrencyPair currencyPair) {

    List<Trade> trades = new ArrayList<>(allHitbtcTrades.size());
    long lastTradeId = 0;
    for (HitbtcTrade hitbtcTrade : allHitbtcTrades) {
      Date timestamp = hitbtcTrade.getTimestamp();
      BigDecimal price = hitbtcTrade.getPrice();
      BigDecimal amount = hitbtcTrade.getQuantity();
      String tid = hitbtcTrade.getId();
      long longTradeId = tid == null ? 0 : Long.parseLong(tid);
      if (longTradeId > lastTradeId) {
        lastTradeId = longTradeId;
      }
      OrderType orderType = adaptSide(hitbtcTrade.getSide());
      Trade trade = new Trade(orderType, amount, currencyPair, price, timestamp, tid);
      trades.add(trade);
    }

    return new Trades(trades, lastTradeId, TradeSortType.SortByTimestamp);
  }

  public static LimitOrder adaptOrder(HitbtcOrder hitbtcOrder) {
    OrderType type = adaptOrderType(hitbtcOrder.side);

    LimitOrder order =
        new LimitOrder(
            type,
            hitbtcOrder.quantity,
            adaptSymbol(hitbtcOrder.symbol),
            hitbtcOrder.clientOrderId,
            hitbtcOrder.getCreatedAt(),
            hitbtcOrder.price,
            null, // exchange does not provide average price
            hitbtcOrder.cumQuantity,
            null,
            convertOrderStatus(hitbtcOrder.status));

    return order;
  }

  public static List<LimitOrder> adaptOrders(List<HitbtcOrder> openOrdersRaw) {
    List<LimitOrder> openOrders = new ArrayList<>(openOrdersRaw.size());

    for (HitbtcOrder hitbtcOrder : openOrdersRaw) {
      openOrders.add(adaptOrder(hitbtcOrder));
    }

    return openOrders;
  }

  public static OpenOrders adaptOpenOrders(List<HitbtcOrder> openOrdersRaw) {
    return new OpenOrders(adaptOrders(openOrdersRaw));
  }

  public static OrderType adaptOrderType(String side) {

    return side.equals("buy") ? OrderType.BID : OrderType.ASK;
  }

  public static UserTrades adaptTradeHistory(
      List<HitbtcOwnTrade> tradeHistoryRaw, ExchangeMetaData metaData) {

    List<UserTrade> trades = new ArrayList<>(tradeHistoryRaw.size());
    for (HitbtcOwnTrade hitbtcOwnTrade : tradeHistoryRaw) {
      OrderType type = adaptOrderType(hitbtcOwnTrade.getSide().getValue());

      org.knowm.xchange.currency.CurrencyPair pair = adaptSymbol(hitbtcOwnTrade.symbol);

      BigDecimal originalAmount = hitbtcOwnTrade.getQuantity();
      Date timestamp = hitbtcOwnTrade.getTimestamp();

      String id = Long.toString(hitbtcOwnTrade.getId());

      UserTrade trade =
          new UserTrade(
              type,
              originalAmount,
              pair,
              hitbtcOwnTrade.getPrice(),
              timestamp,
              id,
              hitbtcOwnTrade.getClientOrderId(),
              hitbtcOwnTrade.getFee(),
              Currency.valueOf(pair.getCounter().getCurrencyCode()));

      trades.add(trade);
    }

    return new UserTrades(trades, TradeSortType.SortByTimestamp);
  }

  public static Wallet adaptWallet(String name, List<HitbtcBalance> hitbtcBalances) {

    List<Balance> balances = new ArrayList<>(hitbtcBalances.size());

    for (HitbtcBalance balanceRaw : hitbtcBalances) {
      Currency currency = Currency.valueOf(balanceRaw.getCurrency());
      org.knowm.xchange.dto.account.Balance balance =
          new org.knowm.xchange.dto.account.Balance.Builder()
              .setCurrency(currency)
              .setTotal(null)
              .setAvailable(balanceRaw.getAvailable())
              .setFrozen(balanceRaw.getReserved())
              .createBalance();
      balances.add(balance);
    }
    return Wallet.build(name, name, balances);
  }

  public static String adaptCurrencyPair(org.knowm.xchange.currency.CurrencyPair pair) {

    return pair == null
        ? null
        : pair.getBase().getCurrencyCode() + pair.getCounter().getCurrencyCode();
  }

  public static HitbtcSide getSide(OrderType type) {

    return type == OrderType.BID ? HitbtcSide.BUY : HitbtcSide.SELL;
  }

  public static ExchangeMetaData adaptToExchangeMetaData(
      List<HitbtcSymbol> symbols,
      Map<Currency, CurrencyMetaData> currencies,
      Map<org.knowm.xchange.currency.CurrencyPair, CurrencyPairMetaData> currencyPairs) {
    if (symbols != null) {
      for (HitbtcSymbol symbol : symbols) {
        org.knowm.xchange.currency.CurrencyPair pair = adaptSymbol(symbol);
        BigDecimal tickSize = symbol.getTickSize();
        int priceScale = tickSize.scale(); // not 100% sure this is correct
        // also, we need to take into account the quantityIncrement

        BigDecimal tradingFee = symbol.getTakeLiquidityRate();
        BigDecimal minimumAmount = null;
        BigDecimal maximumAmount = null;

        if (currencyPairs.containsKey(pair)) {
          CurrencyPairMetaData existing = currencyPairs.get(pair);
          minimumAmount = existing.getMinimumAmount();
          maximumAmount = existing.getMaximumAmount();
        }

        CurrencyPairMetaData meta =
            new CurrencyPairMetaData(tradingFee, minimumAmount, maximumAmount, priceScale);

        currencyPairs.put(pair, meta);
      }
    }

    return new ExchangeMetaData(currencyPairs, currencies, null, null, null);
  }

  public static FundingRecord adapt(HitbtcTransaction transaction) {

    String description = transaction.getType() + ' ' + transaction.getStatus();
    if (transaction.getIndex() != null) {
      description += ", index: " + transaction.getIndex();
    }
    if (transaction.getPaymentId() != null) {
      description += ", paymentId: " + transaction.getPaymentId();
    }

    return new Builder()
        .setAddress(transaction.getAddress())
        .setCurrency(Currency.valueOf(transaction.getCurrency()))
        .setAmount(transaction.getAmount())
        .setType(convertType(transaction.getType()))
        .setFee(transaction.getFee())
        .setDescription(description)
        .setStatus(convertStatus(transaction.getStatus()))
        .setInternalId(transaction.getId())
        .setBlockchainTransactionHash(transaction.getHash())
        .setDate(transaction.getCreatedAt())
        .build();
  }

  /**
   * @param type
   * @return
   * @see https://api.hitbtc.com/api/2/explore/ Transaction Model possible types: payout, payin,
   *     deposit, withdraw, bankToExchange, exchangeToBank
   */
  private static Type convertType(String type) {
    switch (type) {
      case "payout":
      case "withdraw":
      case "exchangeToBank":
        return Type.WITHDRAWAL;
      case "payin":
      case "deposit":
      case "bankToExchange":
        return Type.DEPOSIT;
      default:
        throw new RuntimeException("Unknown HitBTC transaction type: " + type);
    }
  }

  /**
   * @return
   * @see https://api.hitbtc.com/api/2/explore/ Transaction Model possible statusses: created,
   *     pending, failed, success
   */
  private static Status convertStatus(String status) {
    switch (status) {
      case "created":
      case "pending":
        return Status.PROCESSING;
      case "failed":
        return Status.FAILED;
      case "success":
        return Status.COMPLETE;
      default:
        throw new RuntimeException("Unknown HitBTC transaction status: " + status);
    }
  }

  /**
   * Decodes HitBTC Order status.
   *
   * @return
   * @see https://api.hitbtc.com/#order-model Order Model possible statuses: new, suspended,
   *     partiallyFilled, filled, canceled, expired
   */
  private static OrderStatus convertOrderStatus(String status) {
    switch (status) {
      case "new":
        return OrderStatus.NEW;
      case "suspended":
        return OrderStatus.STOPPED;
      case "partiallyFilled":
        return OrderStatus.PARTIALLY_FILLED;
      case "filled":
        return OrderStatus.FILLED;
      case "canceled":
        return OrderStatus.CANCELED;
      case "expired":
        return OrderStatus.EXPIRED;
      default:
        throw new RuntimeException("Unknown HitBTC transaction status: " + status);
    }
  }
}
