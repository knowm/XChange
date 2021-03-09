package org.knowm.xchange.bitmex;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;
import java.util.Map.Entry;
import org.knowm.xchange.bitmex.dto.account.BitmexTicker;
import org.knowm.xchange.bitmex.dto.account.BitmexWalletTransaction;
import org.knowm.xchange.bitmex.dto.marketdata.*;
import org.knowm.xchange.bitmex.dto.trade.*;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
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
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.exceptions.ExchangeException;

public class BitmexAdapters {

  private static final BigDecimal SATOSHIS_BY_BTC = BigDecimal.valueOf(100_000_000L);

  public static OrderBook adaptOrderBook(BitmexDepth bitmexDepth, CurrencyPair currencyPair) {

    OrdersContainer asksOrdersContainer =
        adaptOrders(bitmexDepth.getAsks(), currencyPair, OrderType.ASK, true);
    OrdersContainer bidsOrdersContainer =
        adaptOrders(bitmexDepth.getBids(), currencyPair, OrderType.BID, false);

    return new OrderBook(
        new Date(Math.max(asksOrdersContainer.getTimestamp(), bidsOrdersContainer.getTimestamp())),
        asksOrdersContainer.getLimitOrders(),
        bidsOrdersContainer.getLimitOrders());
  }

  public static BitmexDepth adaptDepth(BitmexPublicOrderList orders) {

    BitmexDepth bitmexDepth = new BitmexDepth(new ArrayList<>(), new ArrayList<>());

    for (BitmexPublicOrder bitmexOrder : orders) {
      if (bitmexOrder.getSide().equals(BitmexSide.BUY)) {
        bitmexDepth.getBids().add(bitmexOrder);
      } else if (bitmexOrder.getSide().equals(BitmexSide.SELL)) {
        bitmexDepth.getAsks().add(bitmexOrder);
      }
    }

    return bitmexDepth;
  }

  public static OrdersContainer adaptOrders(
      List<BitmexPublicOrder> orders,
      CurrencyPair currencyPair,
      OrderType orderType,
      boolean reverse) {

    // bitmex does not provide timestamps on order book
    long maxTimestamp = System.currentTimeMillis();
    LimitOrder[] limitOrders = new LimitOrder[orders.size()];

    int i = reverse ? orders.size() - 1 : 0;
    for (BitmexPublicOrder order : orders) {
      limitOrders[i] = adaptOrder(order, orderType, currencyPair);
      i += (reverse ? -1 : 1);
    }
    return new OrdersContainer(maxTimestamp, Arrays.asList(limitOrders));
  }

  public static Trades adaptTrades(List<BitmexPublicTrade> trades, CurrencyPair currencyPair) {

    List<Trade> tradeList = new ArrayList<>(trades.size());
    for (int i = 0; i < trades.size(); i++) {
      BitmexPublicTrade trade = trades.get(i);
      tradeList.add(adaptTrade(trade, currencyPair));
    }
    long lastTid = trades.size() > 0 ? (trades.get(0).getTime().getTime()) : 0;
    // long lastTid = 0L;
    return new Trades(tradeList, lastTid, TradeSortType.SortByTimestamp);
  }

  public static LimitOrder adaptOrder(
      BitmexPublicOrder order, OrderType orderType, CurrencyPair currencyPair) {

    BigDecimal volume = order.getVolume();

    return new LimitOrder(orderType, volume, currencyPair, "", null, order.getPrice());
  }

  public static LimitOrder adaptOrder(BitmexPrivateOrder rawOrder) {
    OrderType type = rawOrder.getSide() == BitmexSide.BUY ? OrderType.BID : OrderType.ASK;

    CurrencyPair pair = adaptSymbolToCurrencyPair(rawOrder.getSymbol());

    return new LimitOrder(
        type,
        rawOrder.getVolume(),
        pair,
        rawOrder.getId(),
        rawOrder.getTimestamp(),
        rawOrder.getPrice(),
        rawOrder.getAvgPx(),
        rawOrder.getCumQty(),
        null,
        BitmexAdapters.adaptOrderStatus(rawOrder.getOrderStatus()));
  }

  public static Ticker adaptTicker(BitmexTicker bitmexTicker, CurrencyPair currencyPair) {

    Ticker.Builder builder = new Ticker.Builder();
    builder.open(bitmexTicker.getPrevClosePrice());
    builder.ask(bitmexTicker.getAskPrice());
    builder.bid(bitmexTicker.getBidPrice());
    builder.last(bitmexTicker.getLastPrice());
    builder.high(bitmexTicker.getHighPrice());
    builder.low(bitmexTicker.getLowPrice());
    builder.vwap(new BigDecimal(bitmexTicker.getVwap().longValue()));
    builder.volume(bitmexTicker.getVolume24h());
    builder.currencyPair(currencyPair);
    return builder.build();
  }

  public static Trade adaptTrade(BitmexPublicTrade bitmexPublicTrade, CurrencyPair currencyPair) {

    OrderType type = adaptOrderType(bitmexPublicTrade.getSide());
    BigDecimal originalAmount = bitmexPublicTrade.getSize();
    Date timestamp = bitmexPublicTrade.getTime();
    // Date timestamp = adaptTimestamp(bitmexPublicTrade.getTime());
    // new Date((long) (bitmexPublicTrade.getTime()));

    return new Trade.Builder()
        .type(type)
        .originalAmount(originalAmount)
        .currencyPair(currencyPair)
        .price(bitmexPublicTrade.getPrice())
        .timestamp(timestamp)
        .id(String.valueOf(timestamp.getTime()))
        .build();
  }

  public static Wallet adaptWallet(Map<String, BigDecimal> bitmexWallet) {

    List<Balance> balances = new ArrayList<>(bitmexWallet.size());
    for (Entry<String, BigDecimal> balancePair : bitmexWallet.entrySet()) {
      Currency currency = new Currency(balancePair.getKey());
      Balance balance = new Balance(currency, balancePair.getValue());
      balances.add(balance);
    }
    return Wallet.Builder.from(balances).build();
  }

  public static OpenOrders adaptOpenOrders(Map<String, BitmexOrder> bitmexOrders) {

    List<LimitOrder> limitOrders = new ArrayList<>();
    for (Entry<String, BitmexOrder> bitmexOrderEntry : bitmexOrders.entrySet()) {
      BitmexOrder bitmexOrder = bitmexOrderEntry.getValue();
      BitmexOrderDescription orderDescription = bitmexOrder.getOrderDescription();

      if (!"limit".equals(orderDescription.getOrderType().toString())) {
        // how to handle stop-loss, take-profit, stop-loss-limit, and so on orders?
        // ignore anything but a plain limit order for now
        continue;
      }

      limitOrders.add(adaptLimitOrder(bitmexOrder, bitmexOrderEntry.getKey()));
    }
    return new OpenOrders(limitOrders);
  }

  public static LimitOrder adaptLimitOrder(BitmexOrder bitmexOrder, String id) {

    BitmexOrderDescription orderDescription = bitmexOrder.getOrderDescription();
    OrderType type = adaptOrderType(orderDescription.getType());

    BigDecimal originalAmount = bitmexOrder.getVolume();
    BigDecimal filledAmount = bitmexOrder.getVolumeExecuted();
    CurrencyPair pair = BitmexAdapters.adaptSymbolToCurrencyPair(orderDescription.getAssetPair());
    Date timestamp = new Date((long) (bitmexOrder.getOpenTimestamp() * 1000L));

    OrderStatus status = adaptOrderStatus(bitmexOrder.getStatus());

    if (status == OrderStatus.NEW
        && filledAmount.compareTo(BigDecimal.ZERO) > 0
        && filledAmount.compareTo(originalAmount) < 0) {
      status = OrderStatus.PARTIALLY_FILLED;
    }

    return new LimitOrder(
        type,
        originalAmount,
        pair,
        id,
        timestamp,
        orderDescription.getPrice(),
        orderDescription.getPrice(),
        filledAmount,
        bitmexOrder.getFee(),
        status);
  }

  public static OrderType adaptOrderType(BitmexSide bitmexType) {
    return bitmexType == null
        ? null
        : bitmexType.equals(BitmexSide.BUY) ? OrderType.BID : OrderType.ASK;
  }

  public static String adaptOrderId(BitmexOrderResponse orderResponse) {

    List<String> orderIds = orderResponse.getTransactionIds();
    return (orderIds == null || orderIds.isEmpty()) ? "" : orderIds.get(0);
  }

  private static CurrencyPairMetaData adaptPair(
      BitmexTicker ticker, CurrencyPairMetaData originalMeta) {

    if (originalMeta != null) {
      return new CurrencyPairMetaData(
          ticker.getTakerFee(),
          originalMeta.getMinimumAmount(),
          originalMeta.getMaximumAmount(),
          Math.max(0, ticker.getTickSize().stripTrailingZeros().scale()),
          originalMeta.getFeeTiers());
    } else {
      return new CurrencyPairMetaData(
          ticker.getTakerFee(),
          null,
          null,
          Math.max(0, ticker.getTickSize().stripTrailingZeros().scale()),
          null);
    }
  }

  public static OrderStatus adaptOrderStatus(BitmexOrderStatus status) {

    switch (status) {
      case PENDING:
        return OrderStatus.PENDING_NEW;
      case OPEN:
        return OrderStatus.NEW;
      case CLOSED:
        return OrderStatus.FILLED;
      case CANCELED:
        return OrderStatus.CANCELED;
      case EXPIRED:
        return OrderStatus.EXPIRED;
      case REJECTED:
        return OrderStatus.REJECTED;
      default:
        return null;
    }
  }

  public static OrderStatus adaptOrderStatus(BitmexPrivateOrder.OrderStatus status) {
    switch (status) {
      case New:
        return OrderStatus.NEW;
      case PartiallyFilled:
        return OrderStatus.PARTIALLY_FILLED;
      case Filled:
        return OrderStatus.FILLED;
      case Canceled:
        return OrderStatus.CANCELED;
      case Rejected:
        return OrderStatus.REJECTED;
      default:
        return null;
    }
  }

  public static String adaptCurrency(Currency currency) {
    // bitmex seems to use a lowercase 't' in XBT
    // can test this here - https://testnet.bitmex.com/api/explorer/#!/User/User_getDepositAddress
    // uppercase 'T' will return 'Unknown currency code'
    if (currency.getCurrencyCode().equals("BTC") || currency.getCurrencyCode().equals("XBT")) {
      return "XBt";
    }

    return currency.getCurrencyCode();
  }

  public static Currency adaptCurrency(String currencyCode) {
    if (currencyCode.equalsIgnoreCase("XBt")) {
      return Currency.BTC;
    }

    return Currency.getInstance(currencyCode);
  }

  public static String adaptCurrencyPairToSymbol(CurrencyPair currencyPair) {
    return currencyPair == null
        ? null
        : currencyPair.base.getCurrencyCode() + currencyPair.counter.getCurrencyCode();
  }

  public static CurrencyPair adaptSymbolToCurrencyPair(String bitmexSymbol) {

    // Assuming that base symbol has 3 characters
    String baseSymbol = bitmexSymbol.substring(0, 3);
    String counterSymbol = bitmexSymbol.substring(3);

    return new CurrencyPair(baseSymbol, counterSymbol);
  }

  public static class OrdersContainer {

    private final long timestamp;
    private final List<LimitOrder> limitOrders;

    /**
     * Constructor
     *
     * @param timestamp
     * @param limitOrders
     */
    public OrdersContainer(long timestamp, List<LimitOrder> limitOrders) {

      this.timestamp = timestamp;
      this.limitOrders = limitOrders;
    }

    public long getTimestamp() {
      return timestamp;
    }

    public List<LimitOrder> getLimitOrders() {
      return limitOrders;
    }
  }

  public static UserTrade adoptUserTrade(BitmexPrivateExecution exec) {
    CurrencyPair pair = BitmexAdapters.adaptSymbolToCurrencyPair(exec.symbol);
    // the "lastQty" parameter is in the USD currency for ???/USD pairs
    OrderType orderType = convertType(exec.side);
    return orderType == null
        ? null
        : new UserTrade.Builder()
            .id(exec.execID)
            .orderId(exec.orderID)
            .currencyPair(pair)
            .originalAmount(exec.lastQty)
            .price(exec.lastPx)
            .feeAmount(exec.execComm.divide(SATOSHIS_BY_BTC, MathContext.DECIMAL32))
            .feeCurrency(Currency.XBT)
            .timestamp(exec.timestamp)
            .type(orderType)
            .orderUserReference(exec.clOrdID)
            .build();
  }

  private static OrderType convertType(String side) {
    switch (side) {
      case "Buy":
        return OrderType.BID;
      case "Sell":
        return OrderType.ASK;
      default:
        return null;
    }
  }

  public static FundingRecord adaptFundingRecord(BitmexWalletTransaction walletTransaction) {
    return new FundingRecord(
        walletTransaction.getAddress(),
        walletTransaction.getTransactTime(),
        adaptCurrency(walletTransaction.getCurrency()),
        walletTransaction.getAmount().abs(),
        walletTransaction.getTransactID(),
        walletTransaction.getTx(),
        adaptFundingRecordtype(walletTransaction),
        adaptFundingRecordStatus(walletTransaction.getTransactStatus()),
        walletTransaction.getWalletBalance(),
        walletTransaction.getFee(),
        walletTransaction.getText());
  }

  private static FundingRecord.Type adaptFundingRecordtype(
      final BitmexWalletTransaction walletTransaction) {

    String type = walletTransaction.getTransactType();
    if (type.equalsIgnoreCase("Deposit")) {
      return FundingRecord.Type.DEPOSIT;
    } else if (type.equalsIgnoreCase("Withdrawal")) {
      return FundingRecord.Type.WITHDRAWAL;
    } else if (type.equalsIgnoreCase("RealisedPNL") || type.equalsIgnoreCase("UnrealisedPNL")) {
      // 'RealisedPNL' will always have transactStatus = Completed whereas 'UnrealisedPNL' will
      // always be transactStatus = Pending
      if (walletTransaction.getAmount().compareTo(BigDecimal.ZERO) > 0) {
        return FundingRecord.Type.REALISED_PROFIT;
      } else if (walletTransaction.getAmount().compareTo(BigDecimal.ZERO) < 0) {
        return FundingRecord.Type.REALISED_LOSS;
      }
    }

    throw new ExchangeException("Unknown FundingRecord.Type");
  }

  private static FundingRecord.Status adaptFundingRecordStatus(final String transactStatus) {
    return FundingRecord.Status.resolveStatus(transactStatus);
  }
}
