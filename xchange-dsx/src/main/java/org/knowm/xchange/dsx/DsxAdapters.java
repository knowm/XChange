package org.knowm.xchange.dsx;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dsx.dto.*;
import org.knowm.xchange.dsx.service.DsxMarketDataServiceRaw;
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

public class DsxAdapters {

  private static Map<String, CurrencyPair> symbols = new HashMap<>();

  public static CurrencyPair adaptSymbol(String symbol) {
    if (symbols.isEmpty()) {
      try {
        DsxExchange exchange = ExchangeFactory.INSTANCE.createExchange(DsxExchange.class);
        symbols =
            new DsxMarketDataServiceRaw(exchange)
                .getDsxSymbols().stream()
                    .collect(
                        Collectors.toMap(
                            dsxSymbol -> dsxSymbol.getBaseCurrency() + dsxSymbol.getQuoteCurrency(),
                            dsxSymbol ->
                                new CurrencyPair(
                                    dsxSymbol.getBaseCurrency(), dsxSymbol.getQuoteCurrency())));
      } catch (Exception ignored) {
      }
    }

    return symbols.containsKey(symbol) ? symbols.get(symbol) : guessSymbol(symbol);
  }

  static CurrencyPair guessSymbol(String symbol) {
    int splitIndex = symbol.endsWith("USDT") ? symbol.lastIndexOf("USDT") : symbol.length() - 3;
    return new CurrencyPair(symbol.substring(0, splitIndex), symbol.substring(splitIndex));
  }

  public static CurrencyPair adaptSymbol(DsxSymbol dsxSymbol) {

    return new CurrencyPair(dsxSymbol.getBaseCurrency(), dsxSymbol.getQuoteCurrency());
  }

  public static Ticker adaptTicker(DsxTicker dsxTicker, CurrencyPair currencyPair) {

    BigDecimal bid = dsxTicker.getBid();
    BigDecimal ask = dsxTicker.getAsk();
    BigDecimal high = dsxTicker.getHigh();
    BigDecimal low = dsxTicker.getLow();
    BigDecimal last = dsxTicker.getLast();
    BigDecimal volume = dsxTicker.getVolume();
    Date timestamp = dsxTicker.getTimestamp();

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

  public static List<Ticker> adaptTickers(Map<String, DsxTicker> dsxTickers) {

    List<Ticker> tickers = new ArrayList<>(dsxTickers.size());

    for (Map.Entry<String, DsxTicker> ticker : dsxTickers.entrySet()) {

      tickers.add(adaptTicker(ticker.getValue(), adaptSymbol(ticker.getKey())));
    }

    return tickers;
  }

  public static OrderBook adaptOrderBook(DsxOrderBook dsxOrderBook, CurrencyPair currencyPair) {

    List<LimitOrder> asks =
        adaptMarketOrderToLimitOrder(dsxOrderBook.getAsks(), OrderType.ASK, currencyPair);
    List<LimitOrder> bids =
        adaptMarketOrderToLimitOrder(dsxOrderBook.getBids(), OrderType.BID, currencyPair);

    return new OrderBook(null, asks, bids);
  }

  private static List<LimitOrder> adaptMarketOrderToLimitOrder(
      DsxOrderLimit[] dsxOrders, OrderType orderType, CurrencyPair currencyPair) {

    List<LimitOrder> orders = new ArrayList<>(dsxOrders.length);

    for (DsxOrderLimit dsxOrderLimit : dsxOrders) {
      LimitOrder limitOrder =
          new LimitOrder(
              orderType,
              dsxOrderLimit.getSize(),
              currencyPair,
              null,
              null,
              dsxOrderLimit.getPrice());
      orders.add(limitOrder);
    }

    return orders;
  }

  public static OrderType adaptSide(DsxSide side) {

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
      List<? extends DsxTrade> allDsxTrades, CurrencyPair currencyPair, DsxTradesSortBy sortBy) {

    List<Trade> trades = new ArrayList<>(allDsxTrades.size());
    long lastTradeId = 0;
    for (int i = 0; i < allDsxTrades.size(); i++) {
      DsxTrade dsxTrade = allDsxTrades.get(i);

      Date timestamp = dsxTrade.getTimestamp();
      BigDecimal price = dsxTrade.getPrice();
      BigDecimal amount = dsxTrade.getQuantity();
      String tid = dsxTrade.getId();
      long longTradeId = tid == null ? 0 : Long.parseLong(tid);
      if (longTradeId > lastTradeId) {
        lastTradeId = longTradeId;
      }
      OrderType orderType = adaptSide(dsxTrade.getSide());
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

    return new Trades(trades, lastTradeId, sortBy.sortType);
  }

  public static LimitOrder adaptOrder(DsxOrder dsxOrder) {
    OrderType type = adaptOrderType(dsxOrder.side);

    return new LimitOrder(
        type,
        dsxOrder.quantity,
        adaptSymbol(dsxOrder.symbol),
        dsxOrder.id,
        dsxOrder.getCreatedAt(),
        dsxOrder.price,
        null, // exchange does not provide average price
        dsxOrder.cumQuantity,
        null,
        convertOrderStatus(dsxOrder.status),
        dsxOrder.clientOrderId);
  }

  public static List<LimitOrder> adaptOrders(List<DsxOrder> openOrdersRaw) {
    List<LimitOrder> openOrders = new ArrayList<>(openOrdersRaw.size());

    for (DsxOrder dsxOrder : openOrdersRaw) {
      openOrders.add(adaptOrder(dsxOrder));
    }

    return openOrders;
  }

  public static OpenOrders adaptOpenOrders(List<DsxOrder> openOrdersRaw) {
    return new OpenOrders(adaptOrders(openOrdersRaw));
  }

  public static OrderType adaptOrderType(String side) {

    return side.equals("buy") ? OrderType.BID : OrderType.ASK;
  }

  public static UserTrades adaptTradeHistory(List<DsxOwnTrade> tradeHistoryRaw) {

    List<UserTrade> trades = new ArrayList<>(tradeHistoryRaw.size());
    for (DsxOwnTrade dsxOwnTrade : tradeHistoryRaw) {

      OrderType type = adaptOrderType(dsxOwnTrade.getSide().getValue());
      CurrencyPair pair = adaptSymbol(dsxOwnTrade.symbol);
      BigDecimal originalAmount = dsxOwnTrade.getQuantity();
      Date timestamp = dsxOwnTrade.getTimestamp();
      String id = Long.toString(dsxOwnTrade.getId());
      String orderId = String.valueOf(dsxOwnTrade.getOrderId());
      String clientOrderId = dsxOwnTrade.getClientOrderId();

      UserTrade trade =
          new DsxUserTrade(
              type,
              originalAmount,
              pair,
              dsxOwnTrade.getPrice(),
              timestamp,
              id,
              orderId,
              dsxOwnTrade.getFee(),
              pair.counter,
              clientOrderId);

      trades.add(trade);
    }

    return new UserTrades(trades, Trades.TradeSortType.SortByTimestamp);
  }

  public static Wallet adaptWallet(String name, List<DsxBalance> dsxBalances) {

    List<Balance> balances = new ArrayList<>(dsxBalances.size());

    for (DsxBalance balanceRaw : dsxBalances) {
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

  public static DsxSide getSide(OrderType type) {

    return type == OrderType.BID ? DsxSide.BUY : DsxSide.SELL;
  }

  public static ExchangeMetaData adaptToExchangeMetaData(
      List<DsxSymbol> symbols,
      Map<Currency, CurrencyMetaData> currencies,
      Map<CurrencyPair, CurrencyPairMetaData> currencyPairs) {
    if (symbols != null) {
      for (DsxSymbol symbol : symbols) {
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

  public static FundingRecord adapt(DsxTransaction transaction) {

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
   * @param type string
   * @see <a href="https://api.dsxglobal.com/api/2/explore/">Dsx Global API</a> Transaction Model
   *     possible types: payout, payin, deposit, withdraw, bankToExchange, exchangeToBank
   * @return type enum value
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
        throw new RuntimeException("Unknown Dsx transaction type: " + type);
    }
  }

  /**
   * @see <a href="https://api.dsxglobal.com/api/2/explore/">Dsx Global API</a> Transaction Model
   *     possible statusses: created, pending, failed, success
   * @return status enum value
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
        throw new RuntimeException("Unknown Dsx transaction status: " + status);
    }
  }

  /**
   * Decodes Dsx Order status.
   *
   * @see <a href="https://api.dsxglobal.com/#order-model">Dsx Global API Order model</a> Order
   *     Model possible statuses: new, suspended, partiallyFilled, filled, canceled, expired
   * @return order status enum
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
        throw new RuntimeException("Unknown Dsx transaction status: " + status);
    }
  }
}
