package org.knowm.xchange.gemini.v1;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
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
import org.knowm.xchange.dto.trade.FixedRateLoanOrder;
import org.knowm.xchange.dto.trade.FloatingRateLoanOrder;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.gemini.v1.dto.account.GeminiBalancesResponse;
import org.knowm.xchange.gemini.v1.dto.marketdata.GeminiDepth;
import org.knowm.xchange.gemini.v1.dto.marketdata.GeminiLendLevel;
import org.knowm.xchange.gemini.v1.dto.marketdata.GeminiLevel;
import org.knowm.xchange.gemini.v1.dto.marketdata.GeminiTicker;
import org.knowm.xchange.gemini.v1.dto.marketdata.GeminiTrade;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiOrderStatusResponse;
import org.knowm.xchange.gemini.v1.dto.trade.GeminiTradeResponse;
import org.knowm.xchange.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class GeminiAdapters {

  public static final Logger log = LoggerFactory.getLogger(GeminiAdapters.class);

  private GeminiAdapters() {

  }

  public static List<CurrencyPair> adaptCurrencyPairs(Collection<String> GeminiSymbol) {

    List<CurrencyPair> currencyPairs = new ArrayList<>();
    for (String symbol : GeminiSymbol) {
      currencyPairs.add(adaptCurrencyPair(symbol));
    }
    return currencyPairs;
  }

  public static CurrencyPair adaptCurrencyPair(String GeminiSymbol) {

    String tradableIdentifier = GeminiSymbol.substring(0, 3).toUpperCase();
    String transactionCurrency = GeminiSymbol.substring(3).toUpperCase();
    return new CurrencyPair(tradableIdentifier, transactionCurrency);
  }

  public static String adaptCurrencyPair(CurrencyPair pair) {

    return (pair.base.getCurrencyCode() + pair.counter.getCurrencyCode()).toLowerCase();
  }

  public static OrderBook adaptOrderBook(GeminiDepth btceDepth, CurrencyPair currencyPair) {

    OrdersContainer asksOrdersContainer = adaptOrders(btceDepth.getAsks(), currencyPair, OrderType.ASK);
    OrdersContainer bidsOrdersContainer = adaptOrders(btceDepth.getBids(), currencyPair, OrderType.BID);

    return new OrderBook(new Date(Math.max(asksOrdersContainer.getTimestamp(), bidsOrdersContainer.getTimestamp())),
        asksOrdersContainer.getLimitOrders(), bidsOrdersContainer.getLimitOrders());
  }

  public static OrdersContainer adaptOrders(GeminiLevel[] GeminiLevels, CurrencyPair currencyPair, OrderType orderType) {

    BigDecimal maxTimestamp = new BigDecimal(Long.MIN_VALUE);
    List<LimitOrder> limitOrders = new ArrayList<>(GeminiLevels.length);

    for (GeminiLevel GeminiLevel : GeminiLevels) {
      if (GeminiLevel.getTimestamp().compareTo(maxTimestamp) > 0) {
        maxTimestamp = GeminiLevel.getTimestamp();
      }

      Date timestamp = convertBigDecimalTimestampToDate(GeminiLevel.getTimestamp());
      limitOrders.add(adaptOrder(GeminiLevel.getAmount(), GeminiLevel.getPrice(), currencyPair, orderType, timestamp));
    }

    long maxTimestampInMillis = maxTimestamp.multiply(new BigDecimal(1000L)).longValue();
    return new OrdersContainer(maxTimestampInMillis, limitOrders);
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

  public static LimitOrder adaptOrder(BigDecimal amount, BigDecimal price, CurrencyPair currencyPair, OrderType orderType, Date timestamp) {

    return new LimitOrder(orderType, amount, currencyPair, "", timestamp, price);
  }

  public static List<FixedRateLoanOrder> adaptFixedRateLoanOrders(GeminiLendLevel[] orders, String currency, String orderType, String id) {

    List<FixedRateLoanOrder> loanOrders = new ArrayList<>(orders.length);

    for (GeminiLendLevel order : orders) {
      if ("yes".equalsIgnoreCase(order.getFrr())) {
        continue;
      }

      // Bid orderbook is reversed order. Insert at reversed indices
      if (orderType.equalsIgnoreCase("loan")) {
        loanOrders.add(0, adaptFixedRateLoanOrder(currency, order.getAmount(), order.getPeriod(), orderType, id, order.getRate()));
      } else {
        loanOrders.add(adaptFixedRateLoanOrder(currency, order.getAmount(), order.getPeriod(), orderType, id, order.getRate()));
      }
    }

    return loanOrders;
  }

  public static FixedRateLoanOrder adaptFixedRateLoanOrder(String currency, BigDecimal amount, int dayPeriod, String direction, String id,
      BigDecimal rate) {

    OrderType orderType = direction.equalsIgnoreCase("loan") ? OrderType.BID : OrderType.ASK;

    return new FixedRateLoanOrder(orderType, currency, amount, dayPeriod, id, null, rate);
  }

  public static List<FloatingRateLoanOrder> adaptFloatingRateLoanOrders(GeminiLendLevel[] orders, String currency, String orderType, String id) {

    List<FloatingRateLoanOrder> loanOrders = new ArrayList<>(orders.length);

    for (GeminiLendLevel order : orders) {
      if ("no".equals(order.getFrr())) {
        continue;
      }

      // Bid orderbook is reversed order. Insert at reversed indices
      if (orderType.equalsIgnoreCase("loan")) {
        loanOrders.add(0, adaptFloatingRateLoanOrder(currency, order.getAmount(), order.getPeriod(), orderType, id, order.getRate()));
      } else {
        loanOrders.add(adaptFloatingRateLoanOrder(currency, order.getAmount(), order.getPeriod(), orderType, id, order.getRate()));
      }
    }

    return loanOrders;
  }

  public static FloatingRateLoanOrder adaptFloatingRateLoanOrder(String currency, BigDecimal amount, int dayPeriod, String direction, String id,
      BigDecimal rate) {

    OrderType orderType = direction.equalsIgnoreCase("loan") ? OrderType.BID : OrderType.ASK;

    return new FloatingRateLoanOrder(orderType, currency, amount, dayPeriod, id, null, rate);
  }

  public static Trade adaptTrade(GeminiTrade trade, CurrencyPair currencyPair) {

    OrderType orderType = trade.getType().equals("buy") ? OrderType.BID : OrderType.ASK;
    BigDecimal amount = trade.getAmount();
    BigDecimal price = trade.getPrice();
    Date date = DateUtils.fromMillisUtc(trade.getTimestamp() * 1000L); // Gemini uses Unix timestamps
    final String tradeId = String.valueOf(trade.getTradeId());
    return new Trade(orderType, amount, currencyPair, price, date, tradeId);
  }

  public static Trades adaptTrades(GeminiTrade[] trades, CurrencyPair currencyPair) {

    List<Trade> tradesList = new ArrayList<>(trades.length);
    long lastTradeId = 0;
    for (GeminiTrade trade : trades) {
      long tradeId = trade.getTradeId();
      if (tradeId > lastTradeId) {
        lastTradeId = tradeId;
      }
      tradesList.add(adaptTrade(trade, currencyPair));
    }
    return new Trades(tradesList, lastTradeId, TradeSortType.SortByID);
  }

  public static Ticker adaptTicker(GeminiTicker GeminiTicker, CurrencyPair currencyPair) {

    BigDecimal last = GeminiTicker.getLast();
    BigDecimal bid = GeminiTicker.getBid();
    BigDecimal ask = GeminiTicker.getAsk();
    BigDecimal volume = GeminiTicker.getVolume().getBaseVolume(currencyPair);

    Date timestamp = DateUtils.fromMillisUtc(GeminiTicker.getVolume().getTimestampMS());

    return new Ticker.Builder().currencyPair(currencyPair).last(last).bid(bid).ask(ask).volume(volume).timestamp(timestamp)
        .build();
  }

  public static Wallet adaptWallet(GeminiBalancesResponse[] response) {

    Map<String, BigDecimal[]> balancesByCurrency = new HashMap<>(); // {total, available}

    // for each currency we have multiple balances types: exchange, trading, deposit.
    // each of those may be partially frozen/available
    for (GeminiBalancesResponse balance : response) {
      String currencyName = balance.getCurrency().toUpperCase();
      BigDecimal[] balanceDetail = balancesByCurrency.get(currencyName);
      if (balanceDetail == null) {
        balanceDetail = new BigDecimal[]{balance.getAmount(), balance.getAvailable()};
      } else {
        balanceDetail[0] = balanceDetail[0].add(balance.getAmount());
        balanceDetail[1] = balanceDetail[1].add(balance.getAvailable());
      }
      balancesByCurrency.put(currencyName, balanceDetail);
    }

    List<Balance> balances = new ArrayList<>(balancesByCurrency.size());
    for (Entry<String, BigDecimal[]> entry : balancesByCurrency.entrySet()) {
      String currencyName = entry.getKey();
      BigDecimal[] balanceDetail = entry.getValue();
      BigDecimal balanceTotal = balanceDetail[0];
      BigDecimal balanceAvailable = balanceDetail[1];
      balances.add(new Balance(Currency.getInstance(currencyName), balanceTotal, balanceAvailable));
    }

    return new Wallet(balances);
  }

  public static OpenOrders adaptOrders(GeminiOrderStatusResponse[] activeOrders) {

    List<LimitOrder> limitOrders = new ArrayList<>(activeOrders.length);

    for (GeminiOrderStatusResponse order : activeOrders) {
      OrderType orderType = order.getSide().equalsIgnoreCase("buy") ? OrderType.BID : OrderType.ASK;
      CurrencyPair currencyPair = adaptCurrencyPair(order.getSymbol());
      Date timestamp = convertBigDecimalTimestampToDate(order.getTimestamp());
      limitOrders
          .add(new LimitOrder(orderType, order.getRemainingAmount(), currencyPair, String.valueOf(order.getId()), timestamp, order.getPrice()));
    }

    return new OpenOrders(limitOrders);
  }

  public static UserTrades adaptTradeHistory(GeminiTradeResponse[] trades, String symbol) {

    List<UserTrade> pastTrades = new ArrayList<>(trades.length);
    CurrencyPair currencyPair = adaptCurrencyPair(symbol);

    for (GeminiTradeResponse trade : trades) {
      OrderType orderType = trade.getType().equalsIgnoreCase("buy") ? OrderType.BID : OrderType.ASK;
      Date timestamp = convertBigDecimalTimestampToDate(trade.getTimestamp());
      final BigDecimal fee = trade.getFeeAmount() == null ? null : trade.getFeeAmount().negate();
      pastTrades.add(new UserTrade(orderType, trade.getAmount(), currencyPair, trade.getPrice(), timestamp, trade.getTradeId(), trade.getOrderId(),
          fee, Currency.getInstance(trade.getFeeCurrency())));
    }

    return new UserTrades(pastTrades, TradeSortType.SortByTimestamp);
  }

  private static Date convertBigDecimalTimestampToDate(BigDecimal timestampInSeconds) {

    return new Date((long)Math.floor(timestampInSeconds.doubleValue() * 1000));
  }

  public static ExchangeMetaData adaptMetaData(List<CurrencyPair> currencyPairs, ExchangeMetaData metaData) {

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

