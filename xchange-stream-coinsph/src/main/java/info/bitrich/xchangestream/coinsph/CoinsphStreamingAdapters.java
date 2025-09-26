package info.bitrich.xchangestream.coinsph;

import info.bitrich.xchangestream.coinsph.dto.CoinsphWebSocketAggTrade;
import info.bitrich.xchangestream.coinsph.dto.CoinsphWebSocketDepth;
import info.bitrich.xchangestream.coinsph.dto.CoinsphWebSocketExecutionReport;
import info.bitrich.xchangestream.coinsph.dto.CoinsphWebSocketOutboundAccountPosition;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.coinsph.CoinsphAdapters;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderStatus;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.UserTrade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CoinsphStreamingAdapters {

  private static final Logger LOG = LoggerFactory.getLogger(CoinsphStreamingAdapters.class);

  public static CurrencyPair getCurrencyPairFromSymbol(String symbol) {
    if (symbol == null || symbol.length() < 2) {
      throw new IllegalArgumentException("Invalid symbol: " + symbol);
    }

    // Common quote currencies and their lengths to try
    String[] quoteCurrencies = {"USDT", "BTC", "ETH", "BUSD", "BNB", "USD", "EUR"};

    for (String quote : quoteCurrencies) {
      if (symbol.endsWith(quote)) {
        String base = symbol.substring(0, symbol.length() - quote.length());
        return new CurrencyPair(base, quote);
      }
    }

    // If no common pattern matched, try a simple split at position 3 (e.g., "BTCUSD" -> BTC/USD)
    if (symbol.length() >= 6) {
      String base = symbol.substring(0, 3);
      String quote = symbol.substring(3);
      return new CurrencyPair(base, quote);
    }

    // Fallback with a warning
    LOG.warn("Could not parse symbol: {}. Using default parsing method.", symbol);
    // Just assume half and half if all else fails
    int midpoint = symbol.length() / 2;
    String base = symbol.substring(0, midpoint);
    String quote = symbol.substring(midpoint);
    return new CurrencyPair(base, quote);
  }

  private CoinsphStreamingAdapters() {}

  public static String getChannelName(CurrencyPair currencyPair, String streamType) {
    String symbol = getSymbol(currencyPair).toLowerCase();
    return symbol + "@" + streamType;
  }

  /**
   * Converts a CurrencyPair to a symbol string.
   *
   * @param currencyPair The currency pair
   * @return The symbol in Coins.ph format (e.g., "BTCUSDT")
   */
  public static String getSymbol(CurrencyPair currencyPair) {
    return currencyPair.getBase().getCurrencyCode() + currencyPair.getCounter().getCurrencyCode();
  }

  public static OrderBook adaptOrderBook(CoinsphWebSocketDepth wsDepth, CurrencyPair currencyPair) {
    List<LimitOrder> asks =
        wsDepth.getAsks().stream()
            .map(
                entry ->
                    new LimitOrder(
                        Order.OrderType.ASK, entry.get(1), currencyPair, null, null, entry.get(0)))
            .collect(Collectors.toList());
    List<LimitOrder> bids =
        wsDepth.getBids().stream()
            .map(
                entry ->
                    new LimitOrder(
                        Order.OrderType.BID, entry.get(1), currencyPair, null, null, entry.get(0)))
            .collect(Collectors.toList());
    // The WebSocket depth stream usually provides a lastUpdateId that can be used as a timestamp
    // or to manage order book consistency. Coins.ph uses 'u' (finalUpdateId).
    return new OrderBook(
        new Date(wsDepth.getEventTime()), asks, bids); // Using eventTime as book timestamp
  }

  public static Trade adaptTrade(CoinsphWebSocketAggTrade wsTrade) {
    return Trade.builder()
        .id(String.valueOf(wsTrade.getAggregateTradeId()))
        .instrument(CoinsphAdapters.toCurrencyPair(wsTrade.getSymbol().toUpperCase()))
        .price(wsTrade.getPrice())
        .originalAmount(wsTrade.getQuantity())
        .timestamp(new Date(wsTrade.getTradeTime()))
        .type(
            wsTrade.isBuyerMaker()
                ? Order.OrderType.ASK
                : Order.OrderType
                    .BID) // if buyer is maker, it was their sell order that got filled by a taker
        // buy
        .build();
  }

  public static Order adaptOrder(CoinsphWebSocketExecutionReport executionReport) {
    CurrencyPair currencyPair = getCurrencyPairFromSymbol(executionReport.getSymbol());
    OrderType orderType;
    if ("BUY".equalsIgnoreCase(executionReport.getSide())) {
      orderType = OrderType.BID;
    } else {
      orderType = OrderType.ASK;
    }

    OrderStatus status = adaptOrderStatus(executionReport.getOrderStatus());
    Date timestamp = new Date(executionReport.getEventTime());
    String orderId = String.valueOf(executionReport.getOrderId());
    String clientOrderId = executionReport.getClientOrderId();

    if ("LIMIT".equalsIgnoreCase(executionReport.getOrderType())) {
      return new LimitOrder.Builder(orderType, currencyPair)
          .originalAmount(executionReport.getQuantity())
          .id(orderId)
          .orderStatus(status)
          .timestamp(timestamp)
          .limitPrice(executionReport.getPrice())
          .cumulativeAmount(executionReport.getCumulativeFilledQuantity())
          .userReference(clientOrderId)
          .build();
    } else if ("MARKET".equalsIgnoreCase(executionReport.getOrderType())) {
      return new org.knowm.xchange.dto.trade.MarketOrder.Builder(orderType, currencyPair)
          .originalAmount(executionReport.getQuantity())
          .id(orderId)
          .orderStatus(status)
          .timestamp(timestamp)
          .cumulativeAmount(executionReport.getCumulativeFilledQuantity())
          .userReference(clientOrderId)
          .build();
    } else {
      LOG.warn(
          "Unsupported order type: {}. Treating as limit order.", executionReport.getOrderType());
      return new LimitOrder.Builder(orderType, currencyPair)
          .originalAmount(executionReport.getQuantity())
          .id(orderId)
          .orderStatus(status)
          .timestamp(timestamp)
          .limitPrice(executionReport.getPrice())
          .cumulativeAmount(executionReport.getCumulativeFilledQuantity())
          .userReference(clientOrderId)
          .build();
    }
  }

  public static List<Balance> adaptBalances(
      CoinsphWebSocketOutboundAccountPosition accountPosition) {
    List<Balance> balances = new ArrayList<>();
    for (CoinsphWebSocketOutboundAccountPosition.Balance balance : accountPosition.getBalances()) {
      Currency currency = new Currency(balance.getAsset());
      BigDecimal total = balance.getFree().add(balance.getLocked());
      balances.add(
          new Balance.Builder()
              .currency(currency)
              .available(balance.getFree())
              .frozen(balance.getLocked())
              .total(total)
              .timestamp(new Date(accountPosition.getEventTime()))
              // accountLastUpdateTime (u) could also be relevant if more precise timing is needed
              // for the balance itself
              .build());
    }
    return balances;
  }

  public static UserTrade adaptUserTrade(CoinsphWebSocketExecutionReport executionReport) {
    CurrencyPair currencyPair = getCurrencyPairFromSymbol(executionReport.getSymbol());
    OrderType orderType;
    if ("BUY".equalsIgnoreCase(executionReport.getSide())) {
      orderType = OrderType.BID;
    } else {
      orderType = OrderType.ASK;
    }

    Date timestamp = new Date(executionReport.getTradeTime());
    String orderId = String.valueOf(executionReport.getOrderId());
    String tradeId = String.valueOf(executionReport.getTradeId());

    // For trade fee
    Currency feeCurrency =
        executionReport.getCommissionAsset() != null
            ? new Currency(executionReport.getCommissionAsset())
            : null;
    BigDecimal feeAmount = executionReport.getCommission();

    return UserTrade.builder()
        .type(orderType)
        .originalAmount(executionReport.getLastExecutedQuantity())
        .instrument(currencyPair)
        .price(executionReport.getLastExecutedPrice())
        .timestamp(timestamp)
        .id(tradeId)
        .orderId(orderId)
        .feeAmount(feeAmount)
        .feeCurrency(feeCurrency)
        .build();
  }

  public static org.knowm.xchange.dto.marketdata.Ticker adaptTicker(
      info.bitrich.xchangestream.coinsph.dto.CoinsphWebSocketTicker wsTicker) {
    if (wsTicker == null) {
      return null;
    }
    // Create an instance of the REST DTO from the WebSocket DTO
    org.knowm.xchange.coinsph.dto.marketdata.CoinsphTicker restTicker =
        new org.knowm.xchange.coinsph.dto.marketdata.CoinsphTicker(
            wsTicker.getSymbol(),
            wsTicker.getPriceChange(),
            wsTicker.getPriceChangePercent(),
            wsTicker.getWeightedAvgPrice(),
            wsTicker.getPrevClosePrice(),
            wsTicker.getLastPrice(),
            wsTicker.getLastQty(),
            wsTicker.getBidPrice(),
            wsTicker.getBidQty(),
            wsTicker.getAskPrice(),
            wsTicker.getAskQty(),
            wsTicker.getOpenPrice(),
            wsTicker.getHighPrice(),
            wsTicker.getLowPrice(),
            wsTicker.getVolume(),
            wsTicker.getQuoteVolume(),
            wsTicker.getOpenTime(),
            wsTicker.getCloseTime(),
            wsTicker.getFirstId(),
            wsTicker.getLastId(),
            wsTicker.getCount());

    // Delegate to the existing adapter in xchange-coinsph
    return CoinsphAdapters.adaptTicker(restTicker);
  }

  public static org.knowm.xchange.dto.marketdata.Ticker adaptBookTicker(
      info.bitrich.xchangestream.coinsph.dto.CoinsphWebSocketBookTicker wsBookTicker) {
    if (wsBookTicker == null) {
      return null;
    }
    CurrencyPair currencyPair = getCurrencyPairFromSymbol(wsBookTicker.getSymbol());
    return new org.knowm.xchange.dto.marketdata.Ticker.Builder()
        .instrument(currencyPair)
        .bid(wsBookTicker.getBidPrice())
        .ask(wsBookTicker.getAskPrice())
        .bidSize(wsBookTicker.getBidQty())
        .askSize(wsBookTicker.getAskQty())
        .timestamp(new Date(wsBookTicker.getEventTime())) // Use event time from the message
        .build();
  }

  public static OrderStatus adaptOrderStatus(String status) {
    if (status == null) {
      return OrderStatus.UNKNOWN;
    }

    switch (status.toUpperCase()) {
      case "NEW":
        return OrderStatus.NEW;
      case "PARTIALLY_FILLED":
        return OrderStatus.PARTIALLY_FILLED;
      case "FILLED":
        return OrderStatus.FILLED;
      case "CANCELED":
      case "CANCELLED":
        return OrderStatus.CANCELED;
      case "REJECTED":
        return OrderStatus.REJECTED;
      case "EXPIRED":
        return OrderStatus.EXPIRED;
      case "PENDING_CANCEL":
        return OrderStatus.PENDING_CANCEL;
      default:
        LOG.warn("Unhandled order status: {}. Returning UNKNOWN.", status);
        return OrderStatus.UNKNOWN;
    }
  }

  // Ticker and BookTicker adapters are now implemented above.
}
