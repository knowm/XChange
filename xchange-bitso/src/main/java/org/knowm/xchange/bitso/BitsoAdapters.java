package org.knowm.xchange.bitso;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.bitso.dto.account.BitsoBalance;
import org.knowm.xchange.bitso.dto.marketdata.BitsoOrderBook;
import org.knowm.xchange.bitso.dto.marketdata.BitsoTicker;
import org.knowm.xchange.bitso.dto.marketdata.BitsoTrades;
import org.knowm.xchange.bitso.dto.trade.BitsoUserTransaction;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.instrument.Instrument;

public final class BitsoAdapters {

  private BitsoAdapters() {}

  public static Ticker adaptTicker(BitsoTicker t, Instrument instrument) {
    if (instrument instanceof CurrencyPair) {
      return adaptTicker(t, (CurrencyPair) instrument);
    }
    throw new IllegalArgumentException(
        "Unsupported instrument type: " + instrument.getClass().getName());
  }

  public static Ticker adaptTicker(BitsoTicker t, CurrencyPair currencyPair) {

    Date timestamp = null;
    if (t.getPayload() != null && t.getPayload().getCreatedAt() != null) {
      timestamp = parseTimestamp(t.getPayload().getCreatedAt());
    }

    return new Ticker.Builder()
        .currencyPair(currencyPair)
        .last(t.getLast())
        .bid(t.getBid())
        .ask(t.getAsk())
        .high(t.getHigh())
        .low(t.getLow())
        .vwap(t.getVwap())
        .volume(t.getVolume())
        .timestamp(timestamp)
        .build();
  }

  /** Adapts BitsoBalance v3 structure to XChange Wallet */
  public static Wallet adaptWallet(BitsoBalance bitsoBalance) {
    List<Balance> balances = new ArrayList<>();

    // Convert each currency balance from the v3 API structure
    for (BitsoBalance.CurrencyBalance currencyBalance : bitsoBalance.getBalances()) {
      Currency currency = Currency.getInstance(currencyBalance.getCurrency());

      Balance balance =
          new Balance(
              currency,
              currencyBalance.getTotal(),
              currencyBalance.getAvailable(),
              currencyBalance.getLocked());

      balances.add(balance);
    }

    return Wallet.Builder.from(balances).build();
  }

  public static OrderBook adaptOrderBook(
      BitsoOrderBook bitsoOrderBook, Instrument instrument, int timeScale) {
    if (instrument instanceof CurrencyPair) {
      return adaptOrderBook(bitsoOrderBook, (CurrencyPair) instrument, timeScale);
    }
    throw new IllegalArgumentException(
        "Unsupported instrument type: " + instrument.getClass().getName());
  }

  public static OrderBook adaptOrderBook(
      BitsoOrderBook bitsoOrderBook, CurrencyPair currencyPair, int timeScale) {

    List<LimitOrder> asks =
        createOrders(currencyPair, Order.OrderType.ASK, bitsoOrderBook.getAsks());
    List<LimitOrder> bids =
        createOrders(currencyPair, Order.OrderType.BID, bitsoOrderBook.getBids());
    Date date =
        new Date(
            bitsoOrderBook.getTimestamp()
                * timeScale); // polled order books provide a timestamp in seconds, stream in ms
    return new OrderBook(date, asks, bids);
  }

  public static List<LimitOrder> createOrders(
      CurrencyPair currencyPair, Order.OrderType orderType, List<List<BigDecimal>> orders) {

    List<LimitOrder> limitOrders = new ArrayList<>();
    for (List<BigDecimal> ask : orders) {
      checkArgument(
          ask.size() == 2, "Expected a pair (price, amount) but got {0} elements.", ask.size());
      limitOrders.add(createOrder(currencyPair, ask, orderType));
    }
    return limitOrders;
  }

  public static LimitOrder createOrder(
      CurrencyPair currencyPair, List<BigDecimal> priceAndAmount, Order.OrderType orderType) {

    return new LimitOrder(
        orderType, priceAndAmount.get(1), currencyPair, "", null, priceAndAmount.get(0));
  }

  public static Trades adaptTrades(BitsoTrades bitsoTrades, Instrument instrument) {
    if (instrument instanceof CurrencyPair) {
      return adaptTrades(bitsoTrades, (CurrencyPair) instrument);
    }
    throw new IllegalArgumentException(
        "Unsupported instrument type: " + instrument.getClass().getName());
  }

  /**
   * Adapts BitsoTrades v3 DTO to XChange Trades Object
   *
   * @param bitsoTrades The Bitso v3 trades response
   * @param currencyPair (e.g. BTC/MXN)
   * @return The XChange Trades
   */
  public static Trades adaptTrades(BitsoTrades bitsoTrades, CurrencyPair currencyPair) {

    List<Trade> trades = new ArrayList<>();
    long lastTradeId = 0;

    if (bitsoTrades.getPayload() != null) {
      for (BitsoTrades.BitsoTrade trade : bitsoTrades.getPayload()) {
        Order.OrderType type;
        switch (trade.getMakerSide()) {
          case "buy":
            // If maker is buying, then this trade is a sell (taker is selling)
            type = Order.OrderType.ASK;
            break;
          case "sell":
            // If maker is selling, then this trade is a buy (taker is buying)
            type = Order.OrderType.BID;
            break;
          default:
            type = null;
        }

        final long tradeId = trade.getTid();
        if (tradeId > lastTradeId) {
          lastTradeId = tradeId;
        }

        // Parse timestamp from ISO 8601 format
        Date timestamp = parseTimestamp(trade.getCreatedAt());

        trades.add(
            Trade.builder()
                .type(type)
                .originalAmount(trade.getAmount())
                .instrument(currencyPair)
                .price(trade.getPrice())
                .timestamp(timestamp)
                .id(String.valueOf(tradeId))
                .build());
      }
    }

    return new Trades(trades, lastTradeId, TradeSortType.SortByID);
  }

  public static void checkArgument(boolean argument, String msgPattern, Object... msgArgs) {

    if (!argument) {
      throw new IllegalArgumentException(MessageFormat.format(msgPattern, msgArgs));
    }
  }

  public static String toBitsoCurrency(String currency) {
    if (currency == null) {
      return null;
    }
    if (currency.equalsIgnoreCase("usdc")) {
      return Currency.USD.getCurrencyCode().toLowerCase();
    }
    return currency.toLowerCase();
  }

  public static String fromBitsoCurrency(String bitsoCurrency) {
    if (bitsoCurrency == null) {
      return null;
    }
    if (bitsoCurrency.equalsIgnoreCase(Currency.USD.getCurrencyCode())) {
      return Currency.USDC.getCurrencyCode();
    }
    return bitsoCurrency.toUpperCase();
  }

  /** Adapts BitsoUserTransaction v3 structure to XChange UserTrades */
  public static UserTrades adaptTradeHistory(BitsoUserTransaction[] bitsoUserTransactions) {

    List<UserTrade> trades = new ArrayList<>();
    long lastTradeId = 0;

    for (BitsoUserTransaction bitsoUserTransaction : bitsoUserTransactions) {
      // In v3 API, all returned transactions are trades (no need to filter by type)

      // Determine order type from the side field
      Order.OrderType orderType;
      if ("buy".equals(bitsoUserTransaction.getSide())) {
        orderType = Order.OrderType.BID;
      } else if ("sell".equals(bitsoUserTransaction.getSide())) {
        orderType = Order.OrderType.ASK;
      } else {
        continue; // Skip unknown order types
      }

      // Parse currency pair from book field (e.g., "btc_mxn")
      CurrencyPair currencyPair = parseCurrencyPair(bitsoUserTransaction.getBook());
      if (currencyPair == null) {
        continue; // Skip if we can't parse the currency pair
      }

      // Use major amount (the traded amount in base currency)
      BigDecimal originalAmount = bitsoUserTransaction.getMajor().abs();
      BigDecimal price = bitsoUserTransaction.getPrice();

      // Parse timestamp from ISO 8601 format
      Date timestamp = parseTimestamp(bitsoUserTransaction.getCreatedAt());

      // Use trade ID for tracking
      String tradeId = bitsoUserTransaction.getTid();
      long numericTradeId = Long.parseLong(tradeId);
      if (numericTradeId > lastTradeId) {
        lastTradeId = numericTradeId;
      }

      String orderId = bitsoUserTransaction.getOid();
      BigDecimal feeAmount =
          bitsoUserTransaction.getFeesAmount() != null
              ? bitsoUserTransaction.getFeesAmount().abs()
              : BigDecimal.ZERO;

      // Fee currency is specified in the fees_currency field
      Currency feeCurrency = Currency.getInstance(bitsoUserTransaction.getFeesCurrency());

      UserTrade trade =
          UserTrade.builder()
              .type(orderType)
              .originalAmount(originalAmount)
              .instrument(currencyPair)
              .price(price)
              .timestamp(timestamp)
              .id(tradeId)
              .orderId(orderId)
              .feeAmount(feeAmount)
              .feeCurrency(feeCurrency)
              .build();

      trades.add(trade);
    }

    return new UserTrades(trades, lastTradeId, Trades.TradeSortType.SortByID);
  }

  /** Parse currency pair from book string (e.g., "btc_mxn" -> BTC/MXN) */
  private static CurrencyPair parseCurrencyPair(String book) {
    if (book == null || !book.contains("_")) {
      return null;
    }

    String[] parts = book.split("_");
    if (parts.length != 2) {
      return null;
    }

    try {
      Currency base = Currency.getInstance(parts[0]);
      Currency counter = Currency.getInstance(parts[1]);
      return new CurrencyPair(base, counter);
    } catch (IllegalArgumentException e) {
      return null;
    }
  }

  /** Parse ISO 8601 timestamp string to Date */
  private static Date parseTimestamp(String timestamp) {
    if (timestamp == null) {
      return new Date();
    }

    try {
      // Parse ISO 8601 format: 2024-01-15T10:30:00.000+00:00
      return Date.from(Instant.parse(timestamp));
    } catch (Exception e) {
      // Fallback to current time if parsing fails
      return new Date();
    }
  }

  /** Parse ISO 8601 timestamp string to Date */
  private static Date parseTimestamp(Instant timestamp) {
    if (timestamp == null) {
      return new Date();
    }

    try {
      // Parse ISO 8601 format: 2024-01-15T10:30:00.000+00:00
      return Date.from(timestamp);
    } catch (Exception e) {
      // Fallback to current time if parsing fails
      return new Date();
    }
  }
}
