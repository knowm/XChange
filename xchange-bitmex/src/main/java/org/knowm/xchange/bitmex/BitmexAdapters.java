package org.knowm.xchange.bitmex;

import com.google.common.collect.BiMap;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.knowm.xchange.bitmex.dto.account.BitmexTicker;
import org.knowm.xchange.bitmex.dto.account.BitmexWalletTransaction;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexDepth;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPrivateOrder;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPublicOrder;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPublicOrderList;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPublicTrade;
import org.knowm.xchange.bitmex.dto.trade.BitmexOrder;
import org.knowm.xchange.bitmex.dto.trade.BitmexOrderDescription;
import org.knowm.xchange.bitmex.dto.trade.BitmexOrderResponse;
import org.knowm.xchange.bitmex.dto.trade.BitmexOrderStatus;
import org.knowm.xchange.bitmex.dto.trade.BitmexPrivateExecution;
import org.knowm.xchange.bitmex.dto.trade.BitmexSide;
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
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;

public class BitmexAdapters {

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

  public static BitmexDepth adaptDepth(BitmexPublicOrderList orders, CurrencyPair currencyPair) {

    BitmexDepth bitmexDepth = new BitmexDepth(new ArrayList<>(), new ArrayList<>());

    for (BitmexPublicOrder bitmexOrder : orders) {
      if (bitmexOrder.getSide().equals(BitmexSide.BUY)) bitmexDepth.getBids().add(bitmexOrder);
      else if (bitmexOrder.getSide().equals(BitmexSide.SELL))
        bitmexDepth.getAsks().add(bitmexOrder);
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

    CurrencyPair pair = BitmexUtils.translateBitmexCurrencyPair(rawOrder.getSymbol());

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

    return new Trade(
        type,
        originalAmount,
        currencyPair,
        bitmexPublicTrade.getPrice(),
        timestamp,
        String.valueOf(timestamp.getTime()));
  }

  public static Wallet adaptWallet(Map<String, BigDecimal> bitmexWallet) {

    List<Balance> balances = new ArrayList<>(bitmexWallet.size());
    for (Entry<String, BigDecimal> balancePair : bitmexWallet.entrySet()) {
      Currency currency = adaptCurrency(balancePair.getKey());
      Balance balance = new Balance(currency, balancePair.getValue());
      balances.add(balance);
    }
    return new Wallet(balances);
  }

  public static Set<CurrencyPair> adaptCurrencyPairs(Collection<String> bitmexCurrencyPairs) {

    Set<CurrencyPair> currencyPairs = new HashSet<>();
    for (String bitmexCurrencyPair : bitmexCurrencyPairs) {
      CurrencyPair currencyPair = BitmexUtils.translateBitmexCurrencyPair(bitmexCurrencyPair);
      if (currencyPair != null) {
        currencyPairs.add(currencyPair);
      }
    }
    return currencyPairs;
  }

  public static Currency adaptCurrency(String bitmexCurrencyCode) {

    return BitmexUtils.translateBitmexCurrencyCode(bitmexCurrencyCode);
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
    CurrencyPair pair = BitmexUtils.translateBitmexCurrencyPair(orderDescription.getAssetPair());
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

    return bitmexType.equals(BitmexSide.BUY) ? OrderType.BID : OrderType.ASK;
  }

  public static String adaptOrderId(BitmexOrderResponse orderResponse) {

    List<String> orderIds = orderResponse.getTransactionIds();
    return (orderIds == null || orderIds.isEmpty()) ? "" : orderIds.get(0);
  }

  public static ExchangeMetaData adaptToExchangeMetaData(
      ExchangeMetaData originalMetaData,
      List<BitmexTicker> tickers,
      BiMap<BitmexPrompt, String> contracts) {

    // So we will create 3 maps.
    // A pairs map ( "ETC/BTC" -> price_scale:, min_amount:)
    // A currencies map : "BTC"->"scale": 5,"withdrawal_fee": 0.001
    // A bitmexContracts Map XMRZ17->XMR.BTC.MONTHLY
    Map<CurrencyPair, CurrencyPairMetaData> pairs = new HashMap<>();
    Map<Currency, CurrencyMetaData> currencies = new HashMap<>();
    BitmexUtils.setBitmexAssetPairs(tickers);

    pairs.putAll(originalMetaData.getCurrencyPairs());
    currencies.putAll(originalMetaData.getCurrencies());

    for (BitmexTicker ticker : tickers) {
      String quote = ticker.getQuoteCurrency();
      String base = ticker.getRootSymbol();
      Currency baseCurrencyCode = BitmexAdapters.adaptCurrency(base);
      Currency quoteCurrencyCode = BitmexAdapters.adaptCurrency(quote);

      CurrencyPair pair = new CurrencyPair(baseCurrencyCode, quoteCurrencyCode);
      pairs.put(pair, adaptPair(ticker, pairs.get(pair)));
      if (!BitmexUtils.bitmexCurrencies.containsKey(baseCurrencyCode)
          && !BitmexUtils.bitmexCurrencies.containsValue(base))
        BitmexUtils.bitmexCurrencies.put(baseCurrencyCode, base);
      if (!BitmexUtils.bitmexCurrencies.containsKey(quoteCurrencyCode)
          && !BitmexUtils.bitmexCurrencies.containsValue(quote))
        BitmexUtils.bitmexCurrencies.put(quoteCurrencyCode, quote);

      int scale = Math.max(0, ticker.getTickSize().stripTrailingZeros().scale());
      BigDecimal baseWithdrawalFee =
          originalMetaData.getCurrencies().get(baseCurrencyCode) == null
              ? null
              : originalMetaData.getCurrencies().get(baseCurrencyCode).getWithdrawalFee();
      BigDecimal quoteWithdrawalFee =
          originalMetaData.getCurrencies().get(quoteCurrencyCode) == null
              ? null
              : originalMetaData.getCurrencies().get(quoteCurrencyCode).getWithdrawalFee();

      currencies.put(baseCurrencyCode, new CurrencyMetaData(scale, baseWithdrawalFee));
      currencies.put(quoteCurrencyCode, new CurrencyMetaData(scale, quoteWithdrawalFee));
      BitmexPrompt prompt =
          contracts.inverse().get(ticker.getSymbol().replaceFirst(ticker.getRootSymbol(), ""))
                  != null
              ? contracts.inverse().get(ticker.getSymbol().replaceFirst(ticker.getRootSymbol(), ""))
              : BitmexPrompt.PERPETUAL;

      BitmexContract contract = new BitmexContract(pair, prompt);
      if (!BitmexUtils.bitmexContracts.containsKey(ticker.getSymbol())
          && !BitmexUtils.bitmexContracts.containsValue(contract))
        BitmexUtils.bitmexContracts.put(ticker.getSymbol(), contract);
    }

    return new ExchangeMetaData(
        pairs,
        currencies,
        originalMetaData == null ? null : originalMetaData.getPublicRateLimits(),
        originalMetaData == null ? null : originalMetaData.getPrivateRateLimits(),
        originalMetaData == null ? null : originalMetaData.isShareRateLimits());
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

  public static String adaptCurrencyPairToSymbol(CurrencyPair currencyPair) {
    return currencyPair == null
        ? null
        : currencyPair.base.getCurrencyCode() + currencyPair.counter.getCurrencyCode();
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
    CurrencyPair pair = BitmexUtils.translateBitmexCurrencyPair(exec.symbol);
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
            .feeAmount(exec.commission.multiply(exec.lastQty))
            .feeCurrency(pair.counter.equals(Currency.USD) ? pair.counter : pair.base)
            .timestamp(exec.timestamp)
            .type(orderType)
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

    String datePattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
    Date dateFunding = null;

    try {
      dateFunding = dateFormat.parse(walletTransaction.getTransactTime());
    } catch (ParseException e) {
      throw new IllegalArgumentException(e);
    }

    String currency = walletTransaction.getCurrency();

    if (currency.equals("XBt")) {
      currency = Currency.BTC.getCurrencyCode();
    }

    return new FundingRecord(
        walletTransaction.getAddress(),
        dateFunding,
        Currency.getInstance(currency),
        walletTransaction.getAmount().divide(BigDecimal.valueOf(100_000_000L)),
        walletTransaction.getTransactID(),
        walletTransaction.getTx(),
        walletTransaction.getTransactType().equals("Deposit")
            ? FundingRecord.Type.DEPOSIT
            : FundingRecord.Type.WITHDRAWAL,
        FundingRecord.Status.COMPLETE,
        null,
        walletTransaction.getFee(),
        walletTransaction.getText());
  }
}
