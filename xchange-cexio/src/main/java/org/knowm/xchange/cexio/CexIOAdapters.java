package org.knowm.xchange.cexio;

import static org.knowm.xchange.utils.DateUtils.fromISODateString;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.cexio.dto.account.CexIOBalance;
import org.knowm.xchange.cexio.dto.account.CexIOBalanceInfo;
import org.knowm.xchange.cexio.dto.account.CexIOFeeInfo.FeeDetails;
import org.knowm.xchange.cexio.dto.marketdata.CexIODepth;
import org.knowm.xchange.cexio.dto.marketdata.CexIOTicker;
import org.knowm.xchange.cexio.dto.marketdata.CexIOTrade;
import org.knowm.xchange.cexio.dto.trade.*;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Fee;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.utils.DateUtils;

/** Author: brox Since: 2/6/14 */
public class CexIOAdapters {

  private static final String ORDER_TYPE_BUY = "buy";

  /**
   * Adapts a CexIOTrade to a Trade Object
   *
   * @param trade CexIO trade object
   * @param currencyPair trade currencies
   * @return The XChange Trade
   */
  public static Trade adaptTrade(CexIOTrade trade, CurrencyPair currencyPair) {

    BigDecimal amount = trade.getAmount();
    BigDecimal price = trade.getPrice();
    Date date = DateUtils.fromMillisUtc(trade.getDate() * 1000L);
    OrderType type = trade.getType().equals(ORDER_TYPE_BUY) ? OrderType.BID : OrderType.ASK;
    return new Trade.Builder()
        .type(type)
        .originalAmount(amount)
        .currencyPair(currencyPair)
        .price(price)
        .timestamp(date)
        .id(String.valueOf(trade.getTid()))
        .build();
  }

  /**
   * Adapts a CexIOTrade[] to a Trades Object
   *
   * @param cexioTrades The CexIO trade data returned by API
   * @param currencyPair trade currencies
   * @return The trades
   */
  public static Trades adaptTrades(CexIOTrade[] cexioTrades, CurrencyPair currencyPair) {

    List<Trade> tradesList = new ArrayList<>();
    long lastTradeId = 0;
    for (CexIOTrade trade : cexioTrades) {
      long tradeId = trade.getTid();
      if (tradeId > lastTradeId) {
        lastTradeId = tradeId;
      }
      // Date is reversed order. Insert at index 0 instead of appending
      tradesList.add(0, adaptTrade(trade, currencyPair));
    }
    return new Trades(tradesList, lastTradeId, TradeSortType.SortByID);
  }

  /**
   * Adapts a CexIOTicker to a Ticker Object
   *
   * @param ticker The exchange specific ticker
   * @return The ticker
   */
  public static Ticker adaptTicker(CexIOTicker ticker) {
    if (ticker.getPair() == null) {
      throw new IllegalArgumentException("Missing currency pair in ticker: " + ticker);
    }
    return adaptTicker(ticker, adaptCurrencyPair(ticker.getPair()));
  }

  /**
   * Adapts a CexIOTicker to a Ticker Object
   *
   * @param ticker The exchange specific ticker
   * @param currencyPair The currency pair (e.g. BTC/USD)
   * @return The ticker
   */
  public static Ticker adaptTicker(CexIOTicker ticker, CurrencyPair currencyPair) {

    BigDecimal last = ticker.getLast();
    BigDecimal bid = ticker.getBid();
    BigDecimal ask = ticker.getAsk();
    BigDecimal high = ticker.getHigh();
    BigDecimal low = ticker.getLow();
    BigDecimal volume = ticker.getVolume();
    Date timestamp = new Date(ticker.getTimestamp() * 1000L);

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

  /**
   * Adapts Cex.IO Depth to OrderBook Object
   *
   * @param depth Cex.IO order book
   * @param currencyPair The currency pair (e.g. BTC/USD)
   * @return The XChange OrderBook
   */
  public static OrderBook adaptOrderBook(CexIODepth depth, CurrencyPair currencyPair) {

    List<LimitOrder> asks = createOrders(currencyPair, OrderType.ASK, depth.getAsks());
    List<LimitOrder> bids = createOrders(currencyPair, OrderType.BID, depth.getBids());
    Date date = new Date(depth.getTimestamp() * 1000);
    return new OrderBook(date, asks, bids);
  }

  /**
   * Adapts CexIOBalanceInfo to Wallet
   *
   * @param cexIOBalanceInfo CexIOBalanceInfo balance
   * @return The account info
   */
  public static Wallet adaptWallet(CexIOBalanceInfo cexIOBalanceInfo) {

    List<Balance> balances = new ArrayList<>();
    for (String ccyName : cexIOBalanceInfo.getBalances().keySet()) {
      CexIOBalance cexIOBalance = cexIOBalanceInfo.getBalances().get(ccyName);
      balances.add(adaptBalance(Currency.getInstance(ccyName), cexIOBalance));
    }

    return Wallet.Builder.from(balances).build();
  }

  public static Balance adaptBalance(Currency currency, CexIOBalance balance) {
    BigDecimal inOrders = balance.getOrders();
    BigDecimal frozen = inOrders == null ? BigDecimal.ZERO : inOrders;
    return new Balance(currency, null, balance.getAvailable(), frozen);
  }

  public static List<LimitOrder> createOrders(
      CurrencyPair currencyPair, OrderType orderType, List<List<BigDecimal>> orders) {

    List<LimitOrder> limitOrders = new ArrayList<>();
    if (orders == null) return limitOrders;

    for (List<BigDecimal> o : orders) {
      checkArgument(
          o.size() == 2, "Expected a pair (price, amount) but got {0} elements.", o.size());
      limitOrders.add(createOrder(currencyPair, o, orderType));
    }
    return limitOrders;
  }

  public static LimitOrder createOrder(
      CurrencyPair currencyPair, List<BigDecimal> priceAndAmount, OrderType orderType) {

    return new LimitOrder(
        orderType, priceAndAmount.get(1), currencyPair, "", null, priceAndAmount.get(0));
  }

  public static void checkArgument(boolean argument, String msgPattern, Object... msgArgs) {

    if (!argument) {
      throw new IllegalArgumentException(MessageFormat.format(msgPattern, msgArgs));
    }
  }

  public static OpenOrders adaptOpenOrders(List<CexIOOrder> cexIOOrderList) {

    List<LimitOrder> limitOrders = new ArrayList<>();

    for (CexIOOrder cexIOOrder : cexIOOrderList) {
      OrderType orderType =
          cexIOOrder.getType() == CexIOOrder.Type.buy ? OrderType.BID : OrderType.ASK;
      String id = Long.toString(cexIOOrder.getId());
      limitOrders.add(
          new LimitOrder(
              orderType,
              cexIOOrder.getAmount(),
              cexIOOrder.getAmount().subtract(cexIOOrder.getPending()),
              new CurrencyPair(cexIOOrder.getSymbol1(), cexIOOrder.getSymbol2()),
              id,
              DateUtils.fromMillisUtc(cexIOOrder.getTime()),
              cexIOOrder.getPrice()));
    }

    return new OpenOrders(limitOrders);
  }

  public static UserTrade adaptArchivedOrder(CexIOArchivedOrder cexIOArchivedOrder) {
    try {
      Date timestamp = fromISODateString(cexIOArchivedOrder.time);

      OrderType orderType = cexIOArchivedOrder.type.equals("sell") ? OrderType.ASK : OrderType.BID;
      BigDecimal originalAmount = cexIOArchivedOrder.amount;
      CurrencyPair currencyPair =
          new CurrencyPair(cexIOArchivedOrder.symbol1, cexIOArchivedOrder.symbol2);
      BigDecimal price = cexIOArchivedOrder.averageExecutionPrice;
      String id = cexIOArchivedOrder.id;
      String orderId = cexIOArchivedOrder.orderId;

      Currency feeCcy =
          cexIOArchivedOrder.feeCcy == null
              ? null
              : Currency.getInstance(cexIOArchivedOrder.feeCcy);
      BigDecimal fee = cexIOArchivedOrder.feeValue;

      return new UserTrade.Builder()
          .type(orderType)
          .originalAmount(originalAmount)
          .currencyPair(currencyPair)
          .price(price)
          .timestamp(timestamp)
          .id(id)
          .orderId(orderId)
          .feeAmount(fee)
          .feeCurrency(feeCcy)
          .build();
    } catch (InvalidFormatException e) {
      throw new IllegalStateException("Cannot format date " + cexIOArchivedOrder.time, e);
    }
  }

  public static Order adaptOrder(CexIOOpenOrder cexIOOrder) {
    OrderType orderType = cexIOOrder.type.equals("sell") ? OrderType.ASK : OrderType.BID;
    BigDecimal originalAmount = new BigDecimal(cexIOOrder.amount);
    CurrencyPair currencyPair = new CurrencyPair(cexIOOrder.symbol1, cexIOOrder.symbol2);
    Date timestamp = new Date(cexIOOrder.time);
    BigDecimal limitPrice = new BigDecimal(cexIOOrder.price);
    Order.OrderStatus status = adaptOrderStatus(cexIOOrder);
    BigDecimal cumulativeAmount = null;
    try {
      BigDecimal remains = new BigDecimal(cexIOOrder.remains);
      cumulativeAmount = originalAmount.subtract(remains);
    } catch (Exception e) {

    }

    return new LimitOrder(
        orderType,
        originalAmount,
        currencyPair,
        cexIOOrder.orderId,
        timestamp,
        limitPrice,
        null,
        cumulativeAmount,
        null,
        status);
  }

  public static LimitOrder adaptOrder(CexIOFullOrder cexIOOrder) {
    OrderType orderType = cexIOOrder.type.equals("sell") ? OrderType.ASK : OrderType.BID;
    BigDecimal originalAmount = new BigDecimal(cexIOOrder.amount);
    CurrencyPair currencyPair = new CurrencyPair(cexIOOrder.symbol1, cexIOOrder.symbol2);
    Date timestamp = new Date(cexIOOrder.time);
    BigDecimal limitPrice = new BigDecimal(cexIOOrder.price);
    Order.OrderStatus status = adaptOrderStatus(cexIOOrder);
    BigDecimal cumulativeAmount = null;

    if (cexIOOrder.remains != null) {
      BigDecimal remains = new BigDecimal(cexIOOrder.remains);
      cumulativeAmount = originalAmount.subtract(remains);
    }
    BigDecimal totalAmountMaker =
        cexIOOrder.totalAmountMaker != null
            ? new BigDecimal(cexIOOrder.totalAmountMaker)
            : BigDecimal.ZERO;
    BigDecimal totalAmountTaker =
        cexIOOrder.totalAmountTaker != null
            ? new BigDecimal(cexIOOrder.totalAmountTaker)
            : BigDecimal.ZERO;
    BigDecimal tradedAmount = totalAmountMaker.add(totalAmountTaker);

    BigDecimal averagePrice = null;
    if (cumulativeAmount != null && tradedAmount.compareTo(BigDecimal.ZERO) > 0) {
      averagePrice = tradedAmount.divide(cumulativeAmount, 2, RoundingMode.HALF_UP);
    }
    BigDecimal feeMaker =
        cexIOOrder.feeMaker != null ? new BigDecimal(cexIOOrder.feeMaker) : BigDecimal.ZERO;
    BigDecimal feeTaker =
        cexIOOrder.feeTaker != null ? new BigDecimal(cexIOOrder.feeTaker) : BigDecimal.ZERO;
    BigDecimal fee = feeMaker.add(feeTaker);
    return new LimitOrder(
        orderType,
        originalAmount,
        currencyPair,
        cexIOOrder.orderId,
        timestamp,
        limitPrice,
        averagePrice,
        cumulativeAmount,
        fee.compareTo(BigDecimal.ZERO) > 0 ? fee : null,
        status);
  }

  /**
   * From CEX API <a href="https://cex.io/rest-api#/definitions/OrderStatus">documentation </a> <br>
   * Order status can assume follow values ('d' = done, fully executed OR 'c' = canceled, not
   * executed OR 'cd' = cancel-done, partially executed OR 'a' = active, created)
   *
   * @param cexIOOrder cex raw order
   * @return OrderStatus
   */
  private static Order.OrderStatus adaptOrderStatus(CexIOOpenOrder cexIOOrder) {
    if ("c".equalsIgnoreCase(cexIOOrder.status)) return Order.OrderStatus.CANCELED;
    if ("d".equalsIgnoreCase(cexIOOrder.status)) return Order.OrderStatus.FILLED;
    if ("a".equalsIgnoreCase(cexIOOrder.status)) {
      try {
        BigDecimal remains = new BigDecimal(cexIOOrder.remains);
        BigDecimal amount = new BigDecimal(cexIOOrder.amount);
        if (remains.compareTo(BigDecimal.ZERO) > 0 && remains.compareTo(amount) < 0)
          return Order.OrderStatus.PARTIALLY_FILLED;
        else return Order.OrderStatus.PENDING_NEW;
      } catch (NumberFormatException ex) {
        return Order.OrderStatus.PENDING_NEW;
      }
    }
    if ("cd".equalsIgnoreCase(cexIOOrder.status)) {
      try {
        BigDecimal remains = new BigDecimal(cexIOOrder.remains);
        BigDecimal amount = new BigDecimal(cexIOOrder.amount);
        if (remains.compareTo(BigDecimal.ZERO) > 0 && remains.compareTo(amount) < 0)
          return Order.OrderStatus.PARTIALLY_CANCELED;
        else return Order.OrderStatus.CANCELED;
      } catch (NumberFormatException ex) {
        return Order.OrderStatus.CANCELED;
      }
    }
    return Order.OrderStatus.UNKNOWN;
  }

  /**
   * CexIO position status is not documented, testing API we can infer that they are similar to
   * order status {@link #adaptOrderStatus(CexIOOpenOrder)}
   *
   * @param cexioPosition cex raw order
   * @return OrderStatus
   */
  public static Order.OrderStatus adaptPositionStatus(CexioPosition cexioPosition) {
    if ("c".equalsIgnoreCase(cexioPosition.getStatus())) return Order.OrderStatus.CANCELED;
    if ("d".equalsIgnoreCase(cexioPosition.getStatus())) return Order.OrderStatus.FILLED;
    if ("a".equalsIgnoreCase(cexioPosition.getStatus())) return Order.OrderStatus.NEW;
    return Order.OrderStatus.UNKNOWN;
  }

  private static CurrencyPair adaptCurrencyPair(String pair) {
    // Currency pair is in the format: "BCH:USD"
    return new CurrencyPair(pair.replace(":", "/"));
  }

  private static Fee adaptFeeDetails(FeeDetails feeDetails) {
    // It might be worth expanding the Fee structure in xchange-core to contain both buy and sell
    // fees
    return new Fee(
        feeDetails.getBuyMaker().max(feeDetails.getSellMaker()),
        feeDetails.getBuy().max(feeDetails.getSell()));
  }

  public static Map<CurrencyPair, Fee> adaptDynamicTradingFees(Map<CurrencyPair, FeeDetails> fees) {
    Map<CurrencyPair, Fee> result = new HashMap<CurrencyPair, Fee>();
    for (Map.Entry<CurrencyPair, FeeDetails> entry : fees.entrySet()) {
      result.put(entry.getKey(), adaptFeeDetails(entry.getValue()));
    }
    return result;
  }
}
