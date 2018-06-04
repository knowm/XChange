package org.knowm.xchange.gdax;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.gdax.dto.GdaxTransfer;
import org.knowm.xchange.gdax.dto.account.GDAXAccount;
import org.knowm.xchange.gdax.dto.marketdata.*;
import org.knowm.xchange.gdax.dto.trade.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GDAXAdapters {

  private static Logger logger = LoggerFactory.getLogger(GDAXAdapters.class);

  private GDAXAdapters() {}

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

  public static Ticker adaptTicker(
      GDAXProductTicker ticker, GDAXProductStats stats, CurrencyPair currencyPair) {

    BigDecimal last = ticker.getPrice();
    BigDecimal open = stats.getOpen();
    BigDecimal high = stats.getHigh();
    BigDecimal low = stats.getLow();
    BigDecimal buy = ticker.getBid();
    BigDecimal sell = ticker.getAsk();
    BigDecimal volume = ticker.getVolume();
    Date date = parseDate(ticker.getTime());

    return new Ticker.Builder()
        .currencyPair(currencyPair)
        .last(last)
        .open(open)
        .high(high)
        .low(low)
        .bid(buy)
        .ask(sell)
        .volume(volume)
        .timestamp(date)
        .build();
  }

  public static OrderBook adaptOrderBook(
      GDAXProductBook book, CurrencyPair currencyPair, Date date) {

    List<LimitOrder> asks = toLimitOrderList(book.getAsks(), OrderType.ASK, currencyPair);
    List<LimitOrder> bids = toLimitOrderList(book.getBids(), OrderType.BID, currencyPair);

    return new OrderBook(date, asks, bids);
  }

  public static OrderBook adaptOrderBook(GDAXProductBook book, CurrencyPair currencyPair) {
    return adaptOrderBook(book, currencyPair, null);
  }

  private static List<LimitOrder> toLimitOrderList(
      GDAXProductBookEntry[] levels, OrderType orderType, CurrencyPair currencyPair) {

    List<LimitOrder> allLevels = new ArrayList<>();

    if (levels != null) {
      for (int i = 0; i < levels.length; i++) {
        GDAXProductBookEntry ask = levels[i];

        allLevels.add(
            new LimitOrder(orderType, ask.getVolume(), currencyPair, "0", null, ask.getPrice()));
      }
    }

    return allLevels;
  }

  public static Wallet adaptAccountInfo(GDAXAccount[] gdaxAccounts) {

    List<Balance> balances = new ArrayList<>(gdaxAccounts.length);

    for (int i = 0; i < gdaxAccounts.length; i++) {

      GDAXAccount gdaxAccount = gdaxAccounts[i];
      balances.add(
          new Balance(
              Currency.getInstance(gdaxAccount.getCurrency()),
              gdaxAccount.getBalance(),
              gdaxAccount.getAvailable(),
              gdaxAccount.getHold()));
    }

    return new Wallet(gdaxAccounts[0].getProfile_id(), balances);
  }

  @SuppressWarnings("unchecked")
  public static OpenOrders adaptOpenOrders(GDAXOrder[] coinbaseExOpenOrders) {
    Stream<Order> orders =
        Arrays.asList(coinbaseExOpenOrders).stream().map(GDAXAdapters::adaptOrder);
    Map<Boolean, List<Order>> twoTypes =
        orders.collect(Collectors.partitioningBy(t -> t instanceof LimitOrder));
    @SuppressWarnings("rawtypes")
    List limitOrders = twoTypes.get(true);
    return new OpenOrders(limitOrders, twoTypes.get(false));
  }

  public static Order adaptOrder(GDAXOrder order) {
    OrderType type = order.getSide().equals("buy") ? OrderType.BID : OrderType.ASK;
    CurrencyPair currencyPair = new CurrencyPair(order.getProductId().replace('-', '/'));

    Date createdAt = parseDate(order.getCreatedAt());

    OrderStatus orderStatus = adaptOrderStatus(order);

    final BigDecimal averagePrice;
    if (order.getFilledSize().signum() == 0) {
      averagePrice = BigDecimal.ZERO;
    } else {
      averagePrice = order.getExecutedvalue().divide(order.getFilledSize(), new MathContext(8));
    }

    if (order.getType().equals("market")) {
      return new MarketOrder(
          type,
          order.getSize(),
          currencyPair,
          order.getId(),
          createdAt,
          averagePrice,
          order.getFilledSize(),
          order.getFillFees(),
          orderStatus);
    } else if (order.getType().equals("limit")) {
      if (order.getStop() == null) {
        return new LimitOrder(
            type,
            order.getSize(),
            currencyPair,
            order.getId(),
            createdAt,
            order.getPrice(),
            averagePrice,
            order.getFilledSize(),
            order.getFillFees(),
            orderStatus);
      } else {
        return new StopOrder(
            type,
            order.getSize(),
            currencyPair,
            order.getId(),
            createdAt,
            order.getStopPrice(),
            averagePrice,
            order.getFilledSize(),
            orderStatus);
      }
    }

    return null;
  }

  public static OrderStatus[] adaptOrderStatuses(GDAXOrder[] orders) {

    OrderStatus[] orderStatuses = new OrderStatus[orders.length];

    Integer i = 0;
    for (GDAXOrder gdaxOrder : orders) {
      orderStatuses[i++] = adaptOrderStatus(gdaxOrder);
    }

    return orderStatuses;
  }

  /** The status from the GDAXOrder object converted to xchange status */
  public static OrderStatus adaptOrderStatus(GDAXOrder order) {

    if (order.getStatus().equals("pending")) {
      return OrderStatus.PENDING_NEW;
    }

    if (order.getStatus().equals("done") || order.getStatus().equals("settled")) {

      if (order.getDoneReason().equals("filled")) {
        return OrderStatus.FILLED;
      }

      if (order.getDoneReason().equals("canceled")) {
        return OrderStatus.CANCELED;
      }

      return OrderStatus.UNKNOWN;
    }

    if (order.getFilledSize().signum() == 0) {

      if (order.getStatus().equals("open") && order.getStop() != null) {
        // This is a massive edge case of a stop triggering but not immediately
        // fulfilling.  STOPPED status is only currently used by the HitBTC and
        // YoBit implementations and in both cases it looks like a
        // misunderstanding and those should return CANCELLED.  Should we just
        // remove this status?
        return OrderStatus.STOPPED;
      }

      return OrderStatus.NEW;
    }

    if (order.getFilledSize().compareTo(BigDecimal.ZERO) > 0
        // if size >= filledSize order should be partially filled
        && order.getSize().compareTo(order.getFilledSize()) >= 0)
      return OrderStatus.PARTIALLY_FILLED;

    return OrderStatus.UNKNOWN;
  }

  public static UserTrades adaptTradeHistory(GDAXFill[] coinbaseExFills) {

    List<UserTrade> trades = new ArrayList<>(coinbaseExFills.length);

    for (int i = 0; i < coinbaseExFills.length; i++) {
      GDAXFill fill = coinbaseExFills[i];

      OrderType type = fill.getSide().equals("buy") ? OrderType.BID : OrderType.ASK;

      CurrencyPair currencyPair = new CurrencyPair(fill.getProductId().replace('-', '/'));

      UserTrade t =
          new UserTrade(
              type,
              fill.getSize(),
              currencyPair,
              fill.getPrice(),
              parseDate(fill.getCreatedAt()),
              String.valueOf(fill.getTradeId()),
              fill.getOrderId(),
              fill.getFee(),
              currencyPair.counter);
      trades.add(t);
    }

    return new UserTrades(trades, TradeSortType.SortByID);
  }

  public static Trades adaptTrades(GDAXTrade[] coinbaseExTrades, CurrencyPair currencyPair) {

    List<Trade> trades = new ArrayList<>(coinbaseExTrades.length);

    for (int i = 0; i < coinbaseExTrades.length; i++) {
      GDAXTrade trade = coinbaseExTrades[i];

      // yes, sell means buy for gdax reported trades..
      OrderType type = trade.getSide().equals("sell") ? OrderType.BID : OrderType.ASK;

      Trade t =
          new Trade(
              type,
              trade.getSize(),
              currencyPair,
              trade.getPrice(),
              parseDate(trade.getTimestamp()),
              String.valueOf(trade.getTradeId()));
      trades.add(t);
    }

    return new Trades(trades, coinbaseExTrades[0].getTradeId(), TradeSortType.SortByID);
  }

  public static CurrencyPair adaptCurrencyPair(GDAXProduct product) {
    return new CurrencyPair(product.getBaseCurrency(), product.getTargetCurrency());
  }

  public static ExchangeMetaData adaptToExchangeMetaData(
      ExchangeMetaData exchangeMetaData, GDAXProduct[] products) {

    Map<CurrencyPair, CurrencyPairMetaData> currencyPairs = exchangeMetaData.getCurrencyPairs();
    Map<Currency, CurrencyMetaData> currencies = exchangeMetaData.getCurrencies();
    for (GDAXProduct product : products) {

      BigDecimal minSize = product.getBaseMinSize();
      BigDecimal maxSize = product.getBaseMaxSize();

      CurrencyPair pair = adaptCurrencyPair(product);

      CurrencyPairMetaData staticMetaData = exchangeMetaData.getCurrencyPairs().get(pair);
      int priceScale = staticMetaData == null ? 8 : staticMetaData.getPriceScale();
      CurrencyPairMetaData cpmd = new CurrencyPairMetaData(null, minSize, maxSize, priceScale);
      currencyPairs.put(pair, cpmd);

      if (!currencies.containsKey(pair.base)) currencies.put(pair.base, null);
      if (!currencies.containsKey(pair.counter)) currencies.put(pair.counter, null);
    }
    return new ExchangeMetaData(
        currencyPairs,
        currencies,
        exchangeMetaData.getPublicRateLimits(),
        exchangeMetaData.getPrivateRateLimits(),
        true);
  }

  public static String adaptProductID(CurrencyPair currencyPair) {
    return currencyPair.base.getCurrencyCode() + "-" + currencyPair.counter.getCurrencyCode();
  }

  public static GDAXPlaceOrder.Side adaptSide(OrderType orderType) {
    return orderType == OrderType.ASK ? GDAXPlaceOrder.Side.sell : GDAXPlaceOrder.Side.buy;
  }

  public static GDAXPlaceOrder.Stop adaptStop(OrderType orderType) {
    return orderType == OrderType.ASK ? GDAXPlaceOrder.Stop.loss : GDAXPlaceOrder.Stop.entry;
  }

  public static GDAXPlaceLimitOrder adaptGDAXPlaceLimitOrder(LimitOrder limitOrder) {
    GDAXPlaceLimitOrder.Builder builder =
        new GDAXPlaceLimitOrder.Builder()
            .price(limitOrder.getLimitPrice())
            .type(GDAXPlaceOrder.Type.limit)
            .productId(adaptProductID(limitOrder.getCurrencyPair()))
            .side(adaptSide(limitOrder.getType()))
            .size(limitOrder.getOriginalAmount());

    if (limitOrder.getOrderFlags().contains(GDAXOrderFlags.POST_ONLY)) builder.postOnly(true);
    if (limitOrder.getOrderFlags().contains(GDAXOrderFlags.FILL_OR_KILL))
      builder.timeInForce(GDAXPlaceLimitOrder.TimeInForce.FOK);
    if (limitOrder.getOrderFlags().contains(GDAXOrderFlags.IMMEDIATE_OR_CANCEL))
      builder.timeInForce(GDAXPlaceLimitOrder.TimeInForce.IOC);

    return builder.build();
  }

  public static GDAXPlaceMarketOrder adaptGDAXPlaceMarketOrder(MarketOrder marketOrder) {
    return new GDAXPlaceMarketOrder.Builder()
        .productId(adaptProductID(marketOrder.getCurrencyPair()))
        .type(GDAXPlaceOrder.Type.market)
        .side(adaptSide(marketOrder.getType()))
        .size(marketOrder.getOriginalAmount())
        .build();
  }

  /**
   * Creates a 'stop' order. Stop limit order converts to a limit order when the stop amount is
   * triggered. The limit order can have a different price than the stop price.
   *
   * <p>If the stop order has no limit price it will execute as a market order once the stop price
   * is broken
   *
   * @param stopOrder
   * @return
   */
  public static GDAXPlaceOrder adaptGDAXStopOrder(StopOrder stopOrder) {
    // stop orders can also execute as 'stop limit' orders, that is converting to
    // a limit order, but a traditional 'stop' order converts to a market order
    if (stopOrder.getLimitPrice() == null) {
      return new GDAXPlaceMarketOrder.Builder()
          .productId(adaptProductID(stopOrder.getCurrencyPair()))
          .type(GDAXPlaceOrder.Type.market)
          .side(adaptSide(stopOrder.getType()))
          .size(stopOrder.getOriginalAmount())
          .stop(adaptStop(stopOrder.getType()))
          .stopPrice(stopOrder.getStopPrice())
          .build();
    }
    return new GDAXPlaceLimitOrder.Builder()
        .productId(adaptProductID(stopOrder.getCurrencyPair()))
        .type(GDAXPlaceOrder.Type.limit)
        .side(adaptSide(stopOrder.getType()))
        .size(stopOrder.getOriginalAmount())
        .stop(adaptStop(stopOrder.getType()))
        .stopPrice(stopOrder.getStopPrice())
        .price(stopOrder.getLimitPrice())
        .build();
  }

  public static FundingRecord adaptFundingRecord(Currency currency, GdaxTransfer gdaxTransfer) {
    FundingRecord.Status status = FundingRecord.Status.PROCESSING;

    Date processedAt = gdaxTransfer.processedAt();
    Date canceledAt = gdaxTransfer.canceledAt();

    if (canceledAt != null) status = FundingRecord.Status.CANCELLED;
    else if (processedAt != null) status = FundingRecord.Status.COMPLETE;

    Date timestamp = gdaxTransfer.createdAt();

    String address = gdaxTransfer.getDetails().getCryptoAddress();
    if (address == null) address = gdaxTransfer.getDetails().getSentToAddress();

    return new FundingRecord(
        address,
        timestamp,
        currency,
        gdaxTransfer.amount(),
        gdaxTransfer.getId(),
        gdaxTransfer.getDetails().getCryptoTransactionHash(),
        gdaxTransfer.type(),
        status,
        null,
        null,
        null);
  }
}
