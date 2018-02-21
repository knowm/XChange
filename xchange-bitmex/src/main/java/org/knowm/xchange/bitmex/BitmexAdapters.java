package org.knowm.xchange.bitmex;

import com.google.common.collect.BiMap;
import org.knowm.xchange.bitmex.dto.account.BitmexTicker;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexDepth;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPublicOrder;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPublicTrade;
import org.knowm.xchange.bitmex.dto.trade.*;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
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

import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;

public class BitmexAdapters {

  public static OrderBook adaptOrderBook(BitmexDepth bitmexDepth, CurrencyPair currencyPair) {

    OrdersContainer asksOrdersContainer = adaptOrders(bitmexDepth.getAsks(), currencyPair, OrderType.ASK);
    OrdersContainer bidsOrdersContainer = adaptOrders(bitmexDepth.getBids(), currencyPair, OrderType.BID);

    return new OrderBook(new Date(Math.max(asksOrdersContainer.getTimestamp(), bidsOrdersContainer.getTimestamp())), asksOrdersContainer.getLimitOrders(), bidsOrdersContainer.getLimitOrders());
  }

  public static BitmexDepth adaptDepth(BitmexPublicOrder[] orders, CurrencyPair currencyPair) {

    BitmexDepth bitmexDepth = new BitmexDepth(new ArrayList<BitmexPublicOrder>(), new ArrayList<BitmexPublicOrder>());

    for (BitmexPublicOrder bitmexOrder : orders) {
      if (bitmexOrder.getSide().equals(BitmexSide.BUY))
        bitmexDepth.getBids().add(bitmexOrder);
      else if (bitmexOrder.getSide().equals(BitmexSide.SELL))
        bitmexDepth.getAsks().add(bitmexOrder);
    }

    return bitmexDepth;
  }

  public static OrdersContainer adaptOrders(List<BitmexPublicOrder> orders, CurrencyPair currencyPair, OrderType orderType) {

    // bitmex does not provide timestamps on order book
    long maxTimestamp = System.currentTimeMillis();
    List<LimitOrder> limitOrders = new ArrayList<>(orders.size());

    for (BitmexPublicOrder order : orders) {

      limitOrders.add(adaptOrder(order, orderType, currencyPair));
    }
    return new OrdersContainer(maxTimestamp, limitOrders);

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

  public static LimitOrder adaptOrder(BitmexPublicOrder order, OrderType orderType, CurrencyPair currencyPair) {

    BigDecimal volume = order.getVolume();

    return new LimitOrder(orderType, volume, currencyPair, "", null, order.getPrice());
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

    return new Trade(type, originalAmount, currencyPair, bitmexPublicTrade.getPrice(), timestamp, String.valueOf(timestamp.getTime()));
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
      CurrencyPair currencyPair = adaptCurrencyPair(bitmexCurrencyPair);
      if (currencyPair != null) {
        currencyPairs.add(currencyPair);
      }
    }
    return currencyPairs;
  }

  public static Currency adaptCurrency(String bitmexCurrencyCode) {

    return BitmexUtils.translateBitmexCurrencyCode(bitmexCurrencyCode);
  }

  public static CurrencyPair adaptCurrencyPair(String bitmexCurrencyPair) {

    return BitmexUtils.translateBitmexCurrencyPair(bitmexCurrencyPair);
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
    BigDecimal remainingAmount = originalAmount.min(filledAmount);
    CurrencyPair pair = adaptCurrencyPair(orderDescription.getAssetPair());
    Date timestamp = new Date((long) (bitmexOrder.getOpenTimestamp() * 1000L));

    OrderStatus status = adaptOrderStatus(bitmexOrder.getStatus());

    if (status == OrderStatus.NEW && filledAmount.compareTo(BigDecimal.ZERO) > 0 && filledAmount.compareTo(originalAmount) < 0) {
      status = OrderStatus.PARTIALLY_FILLED;
    }

    return new LimitOrder(type, originalAmount, pair, id, timestamp, orderDescription.getPrice(), orderDescription.getPrice(), filledAmount, bitmexOrder.getFee(), status);
  }

  public static UserTrades adaptTradesHistory(Map<String, BitmexTrade> bitmexTrades) {

    List<UserTrade> trades = new ArrayList<>();
    for (Entry<String, BitmexTrade> bitmexTradeEntry : bitmexTrades.entrySet()) {
      trades.add(adaptTrade(bitmexTradeEntry.getValue(), bitmexTradeEntry.getKey()));
    }

    return new UserTrades(trades, TradeSortType.SortByID);
  }

  public static BitmexUserTrade adaptTrade(BitmexTrade bitmexTrade, String tradeId) {

    OrderType orderType = adaptOrderType(bitmexTrade.getSide());
    BigDecimal originalAmount = bitmexTrade.getSize();
    String bitmexAssetPair = bitmexTrade.getSymbol();
    CurrencyPair pair = adaptCurrencyPair(bitmexAssetPair);
    BigDecimal price = bitmexTrade.getPrice();

    return new BitmexUserTrade(orderType, originalAmount, pair, price, null, tradeId, bitmexTrade.getTrdMatchID(), BigDecimal.ONE, pair.counter, BigDecimal.ONE);
  }

  public static OrderType adaptOrderType(BitmexSide bitmexType) {

    return bitmexType.equals(BitmexSide.BUY) ? OrderType.BID : OrderType.ASK;
  }

  public static String adaptOrderId(BitmexOrderResponse orderResponse) {

    List<String> orderIds = orderResponse.getTransactionIds();
    return (orderIds == null || orderIds.isEmpty()) ? "" : orderIds.get(0);
  }

  public static ExchangeMetaData adaptToExchangeMetaData(ExchangeMetaData originalMetaData, List<BitmexTicker> tickers, BiMap<BitmexPrompt, String> contracts) {

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
      pairs.put(pair, adaptPair(ticker, pairs.get(adaptCurrencyPair(pair.toString()))));
      if (!BitmexUtils.bitmexCurrencies.containsKey(baseCurrencyCode) && !BitmexUtils.bitmexCurrencies.containsValue(base))
        BitmexUtils.bitmexCurrencies.put(baseCurrencyCode, base);
      if (!BitmexUtils.bitmexCurrencies.containsKey(quoteCurrencyCode) && !BitmexUtils.bitmexCurrencies.containsValue(quote))
        BitmexUtils.bitmexCurrencies.put(quoteCurrencyCode, quote);

      int scale = Math.max(0, ticker.getTickSize().stripTrailingZeros().scale());
      BigDecimal baseWithdrawalFee = originalMetaData.getCurrencies().get(baseCurrencyCode) == null ? null : originalMetaData.getCurrencies().get(baseCurrencyCode).getWithdrawalFee();
      BigDecimal quoteWithdrawalFee = originalMetaData.getCurrencies().get(quoteCurrencyCode) == null ? null : originalMetaData.getCurrencies().get(quoteCurrencyCode).getWithdrawalFee();

      currencies.put(baseCurrencyCode, new CurrencyMetaData(scale, baseWithdrawalFee));
      currencies.put(quoteCurrencyCode, new CurrencyMetaData(scale, quoteWithdrawalFee));
      BitmexPrompt prompt = contracts.inverse().get(ticker.getSymbol().replaceFirst(ticker.getRootSymbol(), "")) != null ? contracts.inverse().get(ticker.getSymbol().replaceFirst(ticker
          .getRootSymbol(), "")) : BitmexPrompt.PERPETUAL;

      BitmexContract contract = new BitmexContract(pair, prompt);
      if (!BitmexUtils.bitmexContracts.containsKey(ticker.getSymbol()) && !BitmexUtils.bitmexContracts.containsValue(contract))
        BitmexUtils.bitmexContracts.put(ticker.getSymbol(), contract);

    }

    return new ExchangeMetaData(pairs, currencies, originalMetaData == null ? null : originalMetaData.getPublicRateLimits(), originalMetaData == null ? null : originalMetaData.getPrivateRateLimits(),
        originalMetaData == null ? null : originalMetaData.isShareRateLimits());
  }

  private static CurrencyPairMetaData adaptPair(BitmexTicker ticker, CurrencyPairMetaData OriginalMeta) {

    if (OriginalMeta != null) {
      return new CurrencyPairMetaData(ticker.getTakerFee(), OriginalMeta.getMinimumAmount(), OriginalMeta.getMaximumAmount(), Math.max(0, ticker.getTickSize().stripTrailingZeros().scale()));
    }
    else {
      return new CurrencyPairMetaData(ticker.getTakerFee(), null, null, Math.max(0, ticker.getTickSize().stripTrailingZeros().scale()));
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
    default:
      return null;
    }
  }

  public static CurrencyPair adaptCurrencyPair(CurrencyPair currencyPair) {

    return currencyPair;
  }
}
