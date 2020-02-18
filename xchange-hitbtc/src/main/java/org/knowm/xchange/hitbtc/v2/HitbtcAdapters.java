package org.knowm.xchange.hitbtc.v2;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.FundingRecord.Type;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.meta.FeeTier;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.hitbtc.v2.dto.*;
import org.knowm.xchange.hitbtc.v2.service.HitbtcMarketDataServiceRaw;

public class HitbtcAdapters {

  private static Map<String, CurrencyPair> symbols = new HashMap<>();

  public static CurrencyPair adaptSymbol(String symbol) {
    if (symbols.isEmpty()) {
      try {
        HitbtcExchange exchange = ExchangeFactory.INSTANCE.createExchange(HitbtcExchange.class);
        symbols =
            new HitbtcMarketDataServiceRaw(exchange)
                .getHitbtcSymbols().stream()
                    .collect(
                        Collectors.toMap(
                            hitbtcSymbol ->
                                hitbtcSymbol.getBaseCurrency() + hitbtcSymbol.getQuoteCurrency(),
                            hitbtcSymbol ->
                                new CurrencyPair(
                                    hitbtcSymbol.getBaseCurrency(),
                                    hitbtcSymbol.getQuoteCurrency())));
      } catch (Exception ignored) {
      }
    }

    return symbols.containsKey(symbol) ? symbols.get(symbol) : guessSymbol(symbol);
  }

  static CurrencyPair guessSymbol(String symbol) {
    int splitIndex = symbol.endsWith("USDT") ? symbol.lastIndexOf("USDT") : symbol.length() - 3;
    return new CurrencyPair(symbol.substring(0, splitIndex), symbol.substring(splitIndex));
  }

  public static CurrencyPair adaptSymbol(HitbtcSymbol hitbtcSymbol) {

    return new CurrencyPair(hitbtcSymbol.getBaseCurrency(), hitbtcSymbol.getQuoteCurrency());
  }

  public static Ticker adaptTicker(HitbtcTicker hitbtcTicker, CurrencyPair currencyPair) {

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

    for (Map.Entry<String, HitbtcTicker> ticker : hitbtcTickers.entrySet()) {

      tickers.add(adaptTicker(ticker.getValue(), adaptSymbol(ticker.getKey())));
    }

    return tickers;
  }

  public static OrderBook adaptOrderBook(
      HitbtcOrderBook hitbtcOrderBook, CurrencyPair currencyPair) {

    List<LimitOrder> asks =
        adaptMarketOrderToLimitOrder(hitbtcOrderBook.getAsks(), OrderType.ASK, currencyPair);
    List<LimitOrder> bids =
        adaptMarketOrderToLimitOrder(hitbtcOrderBook.getBids(), OrderType.BID, currencyPair);

    return new OrderBook(null, asks, bids);
  }

  private static List<LimitOrder> adaptMarketOrderToLimitOrder(
      HitbtcOrderLimit[] hitbtcOrders, OrderType orderType, CurrencyPair currencyPair) {

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
      List<? extends HitbtcTrade> allHitbtcTrades, CurrencyPair currencyPair) {

    List<Trade> trades = new ArrayList<>(allHitbtcTrades.size());
    long lastTradeId = 0;
    for (int i = 0; i < allHitbtcTrades.size(); i++) {
      HitbtcTrade hitbtcTrade = allHitbtcTrades.get(i);

      Date timestamp = hitbtcTrade.getTimestamp();
      BigDecimal price = hitbtcTrade.getPrice();
      BigDecimal amount = hitbtcTrade.getQuantity();
      String tid = hitbtcTrade.getId();
      long longTradeId = tid == null ? 0 : Long.parseLong(tid);
      if (longTradeId > lastTradeId) {
        lastTradeId = longTradeId;
      }
      OrderType orderType = adaptSide(hitbtcTrade.getSide());
      Trade trade =
          new Trade.Builder()
              .type(orderType)
              .originalAmount(amount)
              .currencyPair(currencyPair)
              .price(price)
              .timestamp(timestamp)
              .id(tid)
              .build();
      trades.add(trade);
    }

    return new Trades(trades, lastTradeId, Trades.TradeSortType.SortByTimestamp);
  }

  public static LimitOrder adaptOrder(HitbtcOrder hitbtcOrder) {
    OrderType type = adaptOrderType(hitbtcOrder.side);

    return new HitbtcLimitOrder(
        type,
        hitbtcOrder.quantity,
        adaptSymbol(hitbtcOrder.symbol),
        hitbtcOrder.id,
        hitbtcOrder.getCreatedAt(),
        hitbtcOrder.price,
        null, // exchange does not provide average price
        hitbtcOrder.cumQuantity,
        null,
        convertOrderStatus(hitbtcOrder.status),
        hitbtcOrder.clientOrderId);
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

  public static UserTrades adaptTradeHistory(List<HitbtcOwnTrade> tradeHistoryRaw) {

    List<UserTrade> trades = new ArrayList<>(tradeHistoryRaw.size());
    for (HitbtcOwnTrade hitbtcOwnTrade : tradeHistoryRaw) {

      OrderType type = adaptOrderType(hitbtcOwnTrade.getSide().getValue());
      CurrencyPair pair = adaptSymbol(hitbtcOwnTrade.symbol);
      BigDecimal originalAmount = hitbtcOwnTrade.getQuantity();
      Date timestamp = hitbtcOwnTrade.getTimestamp();
      String id = Long.toString(hitbtcOwnTrade.getId());
      String orderId = String.valueOf(hitbtcOwnTrade.getOrderId());
      String clientOrderId = hitbtcOwnTrade.getClientOrderId();

      UserTrade trade =
          new HitbtcUserTrade(
              type,
              originalAmount,
              pair,
              hitbtcOwnTrade.getPrice(),
              timestamp,
              id,
              orderId,
              hitbtcOwnTrade.getFee(),
              pair.counter,
              clientOrderId);

      trades.add(trade);
    }

    return new UserTrades(trades, Trades.TradeSortType.SortByTimestamp);
  }

  public static Wallet adaptWallet(String name, List<HitbtcBalance> hitbtcBalances) {

    List<Balance> balances = new ArrayList<>(hitbtcBalances.size());

    for (HitbtcBalance balanceRaw : hitbtcBalances) {
      Currency currency = Currency.getInstance(balanceRaw.getCurrency());
      Balance balance =
          new Balance(currency, null, balanceRaw.getAvailable(), balanceRaw.getReserved());
      balances.add(balance);
    }
    return Wallet.Builder.from(balances).id(name).name(name).build();
  }

  public static String adaptCurrencyPair(CurrencyPair pair) {

    return pair == null ? null : pair.base.getCurrencyCode() + pair.counter.getCurrencyCode();
  }

  public static HitbtcSide getSide(OrderType type) {

    return type == OrderType.BID ? HitbtcSide.BUY : HitbtcSide.SELL;
  }

  public static ExchangeMetaData adaptToExchangeMetaData(
      List<HitbtcSymbol> symbols,
      Map<Currency, CurrencyMetaData> currencies,
      Map<CurrencyPair, CurrencyPairMetaData> currencyPairs) {
    if (symbols != null) {
      for (HitbtcSymbol symbol : symbols) {
        CurrencyPair pair = adaptSymbol(symbol);
        BigDecimal tickSize = symbol.getTickSize();
        int priceScale = tickSize.scale(); // not 100% sure this is correct

        BigDecimal tradingFee = symbol.getTakeLiquidityRate();
        BigDecimal minimumAmount = symbol.getQuantityIncrement();
        BigDecimal maximumAmount = null;

        FeeTier[] feeTiers = null;
        if (currencyPairs.containsKey(pair)) {
          CurrencyPairMetaData existing = currencyPairs.get(pair);
          minimumAmount = existing.getMinimumAmount();
          maximumAmount = existing.getMaximumAmount();
          feeTiers = existing.getFeeTiers();
        }

        CurrencyPairMetaData meta =
            new CurrencyPairMetaData(
                tradingFee, minimumAmount, maximumAmount, priceScale, feeTiers);

        currencyPairs.put(pair, meta);
      }
    }

    return new ExchangeMetaData(currencyPairs, currencies, null, null, null);
  }

  public static FundingRecord adapt(HitbtcTransaction transaction) {

    String description = transaction.getType() + " " + transaction.getStatus();
    if (transaction.getIndex() != null) {
      description += ", index: " + transaction.getIndex();
    }
    if (transaction.getPaymentId() != null) {
      description += ", paymentId: " + transaction.getPaymentId();
    }

    return new FundingRecord.Builder()
        .setAddress(transaction.getAddress())
        .setCurrency(Currency.getInstance(transaction.getCurrency()))
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
  private static FundingRecord.Status convertStatus(String status) {
    switch (status) {
      case "created":
      case "pending":
        return FundingRecord.Status.PROCESSING;
      case "failed":
        return FundingRecord.Status.FAILED;
      case "success":
        return FundingRecord.Status.COMPLETE;
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
  private static Order.OrderStatus convertOrderStatus(String status) {
    switch (status) {
      case "new":
        return Order.OrderStatus.NEW;
      case "suspended":
        return Order.OrderStatus.STOPPED;
      case "partiallyFilled":
        return Order.OrderStatus.PARTIALLY_FILLED;
      case "filled":
        return Order.OrderStatus.FILLED;
      case "canceled":
        return Order.OrderStatus.CANCELED;
      case "expired":
        return Order.OrderStatus.EXPIRED;
      default:
        throw new RuntimeException("Unknown HitBTC transaction status: " + status);
    }
  }
}
