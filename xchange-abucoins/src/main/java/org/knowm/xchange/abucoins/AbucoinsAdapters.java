package org.knowm.xchange.abucoins;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import org.knowm.xchange.abucoins.dto.AbucoinsCreateLimitOrderRequest;
import org.knowm.xchange.abucoins.dto.AbucoinsCreateMarketOrderRequest;
import org.knowm.xchange.abucoins.dto.account.AbucoinsAccount;
import org.knowm.xchange.abucoins.dto.account.AbucoinsDepositHistory;
import org.knowm.xchange.abucoins.dto.account.AbucoinsDepositsHistory;
import org.knowm.xchange.abucoins.dto.account.AbucoinsFill;
import org.knowm.xchange.abucoins.dto.account.AbucoinsFills;
import org.knowm.xchange.abucoins.dto.account.AbucoinsHistory;
import org.knowm.xchange.abucoins.dto.account.AbucoinsWithdrawalHistory;
import org.knowm.xchange.abucoins.dto.account.AbucoinsWithdrawalsHistory;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsFullTicker;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsOrderBook;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsTicker;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsTrade;
import org.knowm.xchange.abucoins.dto.trade.AbucoinsOrder;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Author: bryant_harris */
public class AbucoinsAdapters {
  private static Logger logger = LoggerFactory.getLogger(AbucoinsAdapters.class);

  protected static Date parseDate(final String rawDate) {

    String modified;
    if (rawDate.length() > 23) {
      modified = rawDate.substring(0, 23);
    } else if (rawDate.endsWith("Z")) {
      switch (rawDate.length()) {
        case 20:
          modified = rawDate.substring(0, 19) + ".000";
          break;
        case 22:
          modified = rawDate.substring(0, 21) + "00";
          break;
        case 23:
          modified = rawDate.substring(0, 22) + "0";
          break;
        default:
          modified = rawDate;
          break;
      }
    } else {
      switch (rawDate.length()) {
        case 19:
          modified = rawDate + ".000";
          break;
        case 21:
          modified = rawDate + "00";
          break;
        case 22:
          modified = rawDate + "0";
          break;
        default:
          modified = rawDate;
          break;
      }
    }
    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
      dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
      return dateFormat.parse(modified);
    } catch (ParseException e) {
      logger.warn("unable to parse rawDate={} modified={}", rawDate, modified, e);
      return null;
    }
  }

  /**
   * Adapts a AbucoinsTrade to a Trade Object
   *
   * @param trade Abucoins trade object
   * @param currencyPair trade currencies
   * @return The XChange Trade
   */
  public static Trade adaptTrade(AbucoinsTrade trade, CurrencyPair currencyPair) {

    BigDecimal amount = trade.getSize();
    BigDecimal price = trade.getPrice();
    Date date = parseDate(trade.getTime());
    OrderType type = trade.getSide().equals("buy") ? OrderType.BID : OrderType.ASK;
    return new Trade(type, amount, currencyPair, price, date, trade.getTradeID());
  }

  /**
   * Adapts a AbucoinsTrade[] to a Trades Object
   *
   * @param abucoinsTrades The Abucoins trade data returned by API
   * @param currencyPair trade currencies
   * @return The trades
   */
  public static Trades adaptTrades(AbucoinsTrade[] abucoinsTrades, CurrencyPair currencyPair) {

    List<Trade> tradesList = new ArrayList<>();
    long lastTradeId = 0;
    for (AbucoinsTrade trade : abucoinsTrades) tradesList.add(adaptTrade(trade, currencyPair));

    return new Trades(tradesList, lastTradeId, TradeSortType.SortByTimestamp);
  }

  /**
   * Adapts a AbucoinsTicker to a Ticker Object
   *
   * @param ticker The exchange specific ticker
   * @param currencyPair The currency pair (e.g. BTC/USD)
   * @return The ticker
   */
  public static Ticker adaptTicker(AbucoinsTicker ticker, CurrencyPair currencyPair) {

    BigDecimal last = ticker.getPrice();
    BigDecimal bid = ticker.getBid();
    BigDecimal ask = ticker.getAsk();
    BigDecimal volume = ticker.getVolume();
    if (ticker.getTime() == null) throw new RuntimeException("Null date for: " + ticker);
    Date timestamp = parseDate(ticker.getTime());

    return new Ticker.Builder()
        .currencyPair(currencyPair)
        .last(last)
        .bid(bid)
        .ask(ask)
        .volume(volume)
        .timestamp(timestamp)
        .build();
  }

  /**
   * Adapts a AbucoinsFullTicker to a Ticker Object
   *
   * @param ticker The exchange specific ticker
   * @return The ticker
   */
  public static Ticker adaptTicker(AbucoinsFullTicker ticker) {
    return adaptTicker(ticker, adaptCurrencyPair(ticker.getProductID()));
  }

  /**
   * Adapts Cex.IO Depth to OrderBook Object
   *
   * @param abucoinsOrderBook AbucoinsOrderBook order book
   * @param currencyPair The currency pair (e.g. BTC/USD)
   * @return The XChange OrderBook
   */
  public static OrderBook adaptOrderBook(
      AbucoinsOrderBook abucoinsOrderBook, CurrencyPair currencyPair) {

    List<LimitOrder> asks = createOrders(currencyPair, OrderType.ASK, abucoinsOrderBook.getAsks());
    List<LimitOrder> bids = createOrders(currencyPair, OrderType.BID, abucoinsOrderBook.getBids());

    return new OrderBook(new Date(), asks, bids);
  }

  public static AccountInfo adaptAccountInfo(AbucoinsAccount[] accounts) {
    // group account balances by profileID
    Map<Long, List<Balance>> mapByProfileID = new HashMap<>();
    for (AbucoinsAccount account : accounts) {
      List<Balance> balances = mapByProfileID.get(account.getProfileID());
      if (balances == null) {
        balances = new ArrayList<>();
        mapByProfileID.put(account.getProfileID(), balances);
      }
      balances.add(adaptBalance(account));
    }

    // create a wallet for each profileID
    List<Wallet> wallets = new ArrayList<>();
    for (Long profileID : mapByProfileID.keySet()) {
      List<Balance> balances = mapByProfileID.get(profileID);
      wallets.add(new Wallet(String.valueOf(profileID), balances));
    }

    return new AccountInfo("", wallets);
  }

  /**
   * Adapts AbucoinsAccount to a Balance
   *
   * @param account AbucoinsAccount balance
   * @return The account info
   */
  public static Balance adaptBalance(AbucoinsAccount account) {
    Currency currency = Currency.getInstance(account.getCurrency());
    return new Balance(currency, account.getBalance(), account.getAvailable(), account.getHold());
  }

  public static List<LimitOrder> createOrders(
      CurrencyPair currencyPair, OrderType orderType, AbucoinsOrderBook.LimitOrder[] orders) {

    List<LimitOrder> limitOrders = new ArrayList<>();
    if (orders == null) return limitOrders;

    for (AbucoinsOrderBook.LimitOrder o : orders) {
      limitOrders.add(createOrder(currencyPair, o, orderType));
    }
    return limitOrders;
  }

  public static LimitOrder createOrder(
      CurrencyPair currencyPair, AbucoinsOrderBook.LimitOrder priceAndAmount, OrderType orderType) {
    return new LimitOrder.Builder(orderType, currencyPair)
        .averagePrice(priceAndAmount.getPrice())
        .limitPrice(priceAndAmount.getPrice())
        .orderStatus(OrderStatus.NEW)
        .originalAmount(priceAndAmount.getSize())
        .build();
  }

  public static OpenOrders adaptOpenOrders(AbucoinsOrder[] orders) {
    List<LimitOrder> l = new ArrayList<>();
    for (AbucoinsOrder order : orders) l.add(adaptLimitOrder(order));
    OpenOrders retVal = new OpenOrders(l);

    return retVal;
  }

  public static Order adaptOrder(AbucoinsOrder order) {
    switch (order.getType()) {
      case limit:
        return adaptLimitOrder(order);

      case market:
        return adaptMarketOrder(order);

      default:
        logger.warn("Unrecognized order type " + order.getType() + " returning null for Order");
        return null;
    }
  }

  public static LimitOrder adaptLimitOrder(AbucoinsOrder order) {
    return new LimitOrder.Builder(
            adaptOrderType(order.getSide()), adaptCurrencyPair(order.getProductID()))
        .averagePrice(order.getPrice())
        .cumulativeAmount(order.getFilledSize())
        .id(order.getId())
        .limitPrice(order.getPrice())
        .orderStatus(adaptOrderStatus(order.getStatus()))
        .originalAmount(order.getSize())
        .remainingAmount(order.getSize().subtract(order.getFilledSize()))
        .timestamp(parseDate(order.getCreatedAt()))
        .build();
  }

  public static MarketOrder adaptMarketOrder(AbucoinsOrder order) {
    return ((MarketOrder.Builder)
            new MarketOrder.Builder(
                    adaptOrderType(order.getSide()), adaptCurrencyPair(order.getProductID()))
                .averagePrice(order.getPrice())
                .cumulativeAmount(order.getFilledSize())
                .id(order.getId())
                .orderStatus(adaptOrderStatus(order.getStatus()))
                .originalAmount(order.getSize())
                .remainingAmount(order.getSize().subtract(order.getFilledSize()))
                .timestamp(parseDate(order.getCreatedAt())))
        .build();
  }

  public static OrderType adaptOrderType(AbucoinsOrder.Side side) {
    switch (side) {
      case buy:
        return OrderType.BID;

      case sell:
        return OrderType.ASK;

      default:
        logger.warn("Unrecognized Side " + side + " returning null for OrderType");
        return null;
    }
  }

  public static AbucoinsOrder.Side adaptAbucoinsSide(OrderType orderType) {
    switch (orderType) {
      case BID:
        return AbucoinsOrder.Side.buy;

      case ASK:
        return AbucoinsOrder.Side.sell;

      default:
        logger.warn("Unrecognized OrderType " + orderType + " returning null for Side");
        return null;
    }
  }

  public static OrderStatus adaptOrderStatus(AbucoinsOrder.Status status) {
    switch (status) {
      case pending:
        return OrderStatus.PENDING_NEW;

      case open:
        return OrderStatus.NEW;

      case done:
        return OrderStatus.FILLED;

      case closed:
        // from chatting with Abucoins when describing closed
        // "it’s mean that your order can be partially fulfilled but it’s closed"
        return OrderStatus.PARTIALLY_FILLED;

      case rejected:
        return OrderStatus.REJECTED;

      default:
        logger.warn("Unrecognized Status " + status + " returning null for OrderStatus");
        return null;
    }
  }

  public static String adaptCurrencyPairToProductID(CurrencyPair currencyPair) {
    return currencyPair == null ? null : currencyPair.toString().replace('/', '-');
  }

  public static CurrencyPair adaptCurrencyPair(String abucoinsProductID) {
    int indexOf = abucoinsProductID.indexOf('-');
    String base = abucoinsProductID.substring(0, indexOf);
    String counter = abucoinsProductID.substring(indexOf + 1);
    return new CurrencyPair(
        Currency.getInstanceNoCreate(base), Currency.getInstanceNoCreate(counter));
  }

  public static AbucoinsCreateMarketOrderRequest adaptAbucoinsCreateMarketOrderRequest(
      MarketOrder marketOrder) {
    return new AbucoinsCreateMarketOrderRequest(
        adaptAbucoinsSide(marketOrder.getType()),
        adaptCurrencyPairToProductID(marketOrder.getCurrencyPair()),
        marketOrder.getOriginalAmount(),
        null);
  }

  public static AbucoinsCreateLimitOrderRequest adaptAbucoinsCreateLimitOrderRequest(
      LimitOrder limitOrder) {
    return new AbucoinsCreateLimitOrderRequest(
        adaptAbucoinsSide(limitOrder.getType()),
        adaptCurrencyPairToProductID(limitOrder.getCurrencyPair()),
        null,
        null,
        limitOrder.getLimitPrice(),
        limitOrder.getOriginalAmount(),
        AbucoinsOrder.TimeInForce.GTC,
        null,
        null);
  }

  public static UserTrades adaptUserTrades(AbucoinsFills fills) {
    List<UserTrade> userTrades = new ArrayList<>();
    for (AbucoinsFill fill : fills) userTrades.add(adaptUserTrade(fill));
    List<String> afterCursorValues = fills.getResponseHeaders().get("ac-after");
    String nextPageCursor = null;
    if (afterCursorValues != null && !afterCursorValues.isEmpty()) {
      nextPageCursor = afterCursorValues.get(0);
    }
    return new UserTrades(userTrades, 0L, Trades.TradeSortType.SortByTimestamp, nextPageCursor);
  }

  public static UserTrade adaptUserTrade(AbucoinsFill fill) {
    return new UserTrade.Builder()
        .currencyPair(adaptCurrencyPair(fill.getProductID()))
        .id(fill.getTradeID())
        .orderId(fill.getOrderID())
        .price(fill.getPrice())
        .originalAmount(fill.getSize())
        .timestamp(parseDate(fill.getCreatedAt()))
        .type(adaptOrderType(fill.getSide()))
        .build();
  }

  public static List<FundingRecord> adaptFundingRecordsFromDepositsHistory(
      AbucoinsDepositsHistory history) {
    List<FundingRecord> retVal = new ArrayList<>();
    for (AbucoinsDepositHistory h : history.getHistory()) retVal.add(adaptFundingRecord(h));
    return retVal;
  }

  public static List<FundingRecord> adaptFundingRecords(AbucoinsWithdrawalsHistory history) {
    List<FundingRecord> retVal = new ArrayList<>();
    for (AbucoinsWithdrawalHistory h : history.getHistory()) retVal.add(adaptFundingRecord(h));
    return retVal;
  }

  public static FundingRecord adaptFundingRecord(AbucoinsDepositHistory history) {
    return fundingRecordBuilder(history)
        .setInternalId(history.getDepositID())
        .setType(FundingRecord.Type.DEPOSIT)
        .build();
  }

  public static FundingRecord adaptFundingRecord(AbucoinsWithdrawalHistory history) {
    return fundingRecordBuilder(history)
        .setInternalId(history.getWithdrawID())
        .setType(FundingRecord.Type.WITHDRAWAL)
        .build();
  }

  static FundingRecord.Builder fundingRecordBuilder(AbucoinsHistory history) {
    return new FundingRecord.Builder()
        .setDescription(history.getUrl())
        .setAmount(history.getAmount())
        .setCurrency(Currency.getInstance(history.getCurrency()))
        .setDate(parseDate(history.getDate()))
        .setFee(history.getFee())
        .setStatus(adaptFundingStatus(history.getStatus()));
  }

  public static FundingRecord.Status adaptFundingStatus(AbucoinsHistory.Status abucoinsStatus) {
    switch (abucoinsStatus) {
      case unknown: // reminder unknown is our own placeholder for cases where we cannot parse the
        // status

      default:
      case awaitingEmailConfirmation:
      case pending:
        return FundingRecord.Status.PROCESSING;

      case sent:
      case complete:
      case completed:
        return FundingRecord.Status.COMPLETE;
    }
  }

  public static String[] adaptToSetOfIDs(String resp) {
    return resp.replaceAll("[\\[\\\"\\] ]", "").split(",");
  }
}
